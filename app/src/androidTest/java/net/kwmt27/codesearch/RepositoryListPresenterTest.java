//package net.kwmt27.codesearch;
//
//import android.support.test.runner.AndroidJUnit4;
//import android.test.suitebuilder.annotation.LargeTest;
//
//import net.kwmt27.codesearch.presenter.repolist.RepositoryListPresenter;
//import net.kwmt27.codesearch.testUtil.FileUtil;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import java.io.IOException;
//
//import okhttp3.HttpUrl;
//import okhttp3.mockwebserver.MockResponse;
//import okhttp3.mockwebserver.MockWebServer;
//
//import static org.hamcrest.CoreMatchers.is;
//import static org.hamcrest.core.IsNull.notNullValue;
//import static org.junit.Assert.assertThat;
//
//@RunWith(AndroidJUnit4.class)
//@LargeTest
//public class RepositoryListPresenterTest {
//
//
//    private MockWebServer mMockServer;
//
//    @Before
//    public void setUp() throws IOException {
//        mMockServer = new MockWebServer();
//        mMockServer.start();
//
//        HttpUrl baseUrl = new HttpUrl.Builder()
//                .scheme("http")
//                .host(mMockServer.getHostName())
//                .port(mMockServer.getPort())
//                .build();
//        ModelLocator.setApiClient(new MockApiClient(baseUrl));
//    }
//
//    @After
//    public void tearDown() throws IOException {
//        mMockServer.shutdown();
//    }
//
//    @Test
//    public void testOnClearClick() {
//
//        class MockRepositoryListView implements RepositoryListPresenter.IRepositoryListView {
//
//            @Override
//            public void updateTextView(String str) {
//                assertThat(str, is("クリアされました"));
//            }
//        }
//        RepositoryListPresenter presenter = new RepositoryListPresenter(new MockRepositoryListView());
//        presenter.onClearClick();
//    }
//
//    @Test
//    public void testOnGetClick() {
//        String json = FileUtil.getJsonStringFromFile("api_search_code.json");
//        assertThat(json, notNullValue());
//        mMockServer.enqueue(new MockResponse().setBody(json));
//
//        class MockRepositoryListView implements RepositoryListPresenter.IRepositoryListView {
//
//            @Override
//            public void updateTextView(String str) {
//                assertThat(str, is("https://api.github.com/user"));
//            }
//        }
//        RepositoryListPresenter presenter = new RepositoryListPresenter(new MockRepositoryListView());
//        presenter.onGetClick();
//    }
////    @Test
////    public void testOnGetClickJsonParseException() throws IOException {
////        String json = FileUtil.getJsonStringFromFile("api_github_com_jsonexception.json");
////        assertThat(json, notNullValue());
////        mMockServer.enqueue(new MockResponse().setBody(json));
////
////        class MockRepositoryListView implements RepositoryListPresenter.IRepositoryListView {
////
////            @Override
////            public void updateTextView(String str) {
////                assertThat(str, is("JSONパースに失敗しました。"));
////            }
////        }
////        RepositoryListPresenter presenter = new RepositoryListPresenter(new MockRepositoryListView());
////        presenter.onGetClick();
////    }
//}
