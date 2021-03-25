package net.kwmt27.codesearch;

import androidx.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.kwmt27.codesearch.entity.ItemEntity;
import net.kwmt27.codesearch.entity.SearchCodeResultEntity;
import net.kwmt27.codesearch.model.ApiClient;
import net.kwmt27.codesearch.model.SearchCodeModel;
import net.kwmt27.codesearch.testUtil.FileUtil;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.HttpUrl;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import rx.observers.TestSubscriber;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.junit.Assert.assertThat;

@RunWith(AndroidJUnit4.class)
@LargeTest
public class GithubServiceTest {

    private Gson gson = new GsonBuilder().create();
    private MockWebServer mMockServer;


    private ApiClient mMockApiClient;

    @Before
    public void setUp() throws IOException {
        mMockServer = new MockWebServer();
        mMockServer.start();

        HttpUrl baseUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(mMockServer.getHostName())
                .port(mMockServer.getPort())
                .build();

        mMockApiClient = new ApiClient(baseUrl.url().toString());
    }

    @After
    public void tearDown() throws IOException {
        mMockServer.shutdown();
    }


    @Test
    public void testSearchCodeIsSuccess() {

        String json = FileUtil.getJsonStringFromFile("api_search_code.json");
        assertThat(json, notNullValue());

        mMockServer.enqueue(new MockResponse().setBody(json));

        TestSubscriber<List<ItemEntity>> testSubscriber = new TestSubscriber<>();

        SearchCodeModel githubService = new SearchCodeModel(mMockApiClient);
        githubService.searchCode("rxjava", "kwmt/GitHubSearch", null, testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        List<ItemEntity> actual = testSubscriber.getOnNextEvents().get(0);
        ParameterizedType type = new GenericOf(List.class, ItemEntity.class);
        List<ItemEntity> expected = gson.fromJson(json, SearchCodeResultEntity.class).getItemEntityList();
        String e = gson.toJson(expected, type);
        String a = gson.toJson(actual, type);
        assertThat(a, is(equalTo(e)));
    }


//    @Test
//    public void testFetchGithubIsJSONException() {
//
//        String json = FileUtil.getJsonStringFromFile("api_github_com_jsonexception.json");
//        assertThat(json, notNullValue());
//        mMockServer.enqueue(new MockResponse().setBody(json));
//
//        TestSubscriber<GithubResponse> testSubscriber = new TestSubscriber<>();
//        SearchCodeModel githubService = new SearchCodeModel();
//        githubService.fetchGithub(testSubscriber);
//
//        testSubscriber.awaitTerminalEvent();
//        testSubscriber.assertError(JSONException.class);
//
//    }

    // http://osa030.hatenablog.com/entry/2015/10/20/182439
    protected class GenericOf<X, Y> implements ParameterizedType {

        private final Class<X> container;
        private final Class<Y> wrapped;

        public GenericOf(Class<X> container, Class<Y> wrapped) {
            this.container = container;
            this.wrapped = wrapped;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{wrapped};
        }

        @Override
        public Type getRawType() {
            return container;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

}
