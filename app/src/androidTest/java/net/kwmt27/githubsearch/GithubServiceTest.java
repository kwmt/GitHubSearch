package net.kwmt27.githubsearch;

import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.kwmt27.githubsearch.entity.GithubResponse;
import net.kwmt27.githubsearch.model.SearchCodeModel;
import net.kwmt27.githubsearch.testUtil.FileUtil;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

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

    @Before
    public void setUp() throws IOException {
        mMockServer = new MockWebServer();
        mMockServer.start();

        HttpUrl baseUrl = new HttpUrl.Builder()
                .scheme("http")
                .host(mMockServer.getHostName())
                .port(mMockServer.getPort())
                .build();
        ModelLocator.setApiClient(new MockApiClient(baseUrl));
    }

    @After
    public void tearDown() throws IOException {
        mMockServer.shutdown();
    }


    @Test
    public void testFetchGithubIsSuccess() {

        String json = FileUtil.getJsonStringFromFile("api_github_com.json");
        assertThat(json, notNullValue());

        mMockServer.enqueue(new MockResponse().setBody(json));

        TestSubscriber<GithubResponse> testSubscriber = new TestSubscriber<>();

        SearchCodeModel githubService = new SearchCodeModel();
        githubService.fetchGithub(testSubscriber);

        testSubscriber.awaitTerminalEvent();

        testSubscriber.assertNoErrors();
        testSubscriber.assertValueCount(1);

        GithubResponse actual = testSubscriber.getOnNextEvents().get(0);
        GithubResponse expected = gson.fromJson(json, GithubResponse.class);
        String e = gson.toJson(expected, GithubResponse.class);
        String a = gson.toJson(actual, GithubResponse.class);
        assertThat(a, is(equalTo(e)));
    }


    @Test
    public void testFetchGithubIsJSONException() {

        String json = FileUtil.getJsonStringFromFile("api_github_com_jsonexception.json");
        assertThat(json, notNullValue());
        mMockServer.enqueue(new MockResponse().setBody(json));

        TestSubscriber<GithubResponse> testSubscriber = new TestSubscriber<>();
        SearchCodeModel githubService = new SearchCodeModel();
        githubService.fetchGithub(testSubscriber);

        testSubscriber.awaitTerminalEvent();
        testSubscriber.assertError(JSONException.class);

    }


}
