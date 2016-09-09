package net.kwmt27.codesearch.model.rx;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.kwmt27.codesearch.entity.EventEntity;
import net.kwmt27.codesearch.entity.events.CommentEvent;
import net.kwmt27.codesearch.entity.events.CommitCommentEvent;
import net.kwmt27.codesearch.entity.events.CreateEvent;
import net.kwmt27.codesearch.entity.events.DeleteEvent;
import net.kwmt27.codesearch.entity.events.DeploymentEvent;
import net.kwmt27.codesearch.entity.events.DeploymentStatusEvent;
import net.kwmt27.codesearch.entity.events.DownloadEvent;
import net.kwmt27.codesearch.entity.events.FollowEvent;
import net.kwmt27.codesearch.entity.events.ForkApplyEvent;
import net.kwmt27.codesearch.entity.events.ForkEvent;
import net.kwmt27.codesearch.entity.events.GistEvent;
import net.kwmt27.codesearch.entity.events.GollumEvent;
import net.kwmt27.codesearch.entity.events.IssueCommentEvent;
import net.kwmt27.codesearch.entity.events.IssuesEvent;
import net.kwmt27.codesearch.entity.events.MemberEvent;
import net.kwmt27.codesearch.entity.events.MembershipEvent;
import net.kwmt27.codesearch.entity.events.PageBuildEvent;
import net.kwmt27.codesearch.entity.events.PublicEvent;
import net.kwmt27.codesearch.entity.events.PullRequestEvent;
import net.kwmt27.codesearch.entity.events.PullRequestReviewCommentEvent;
import net.kwmt27.codesearch.entity.events.PushEvent;
import net.kwmt27.codesearch.entity.events.ReleaseEvent;
import net.kwmt27.codesearch.entity.events.RepositoryEvent;
import net.kwmt27.codesearch.entity.events.StatusEvent;
import net.kwmt27.codesearch.entity.events.TeamAddEvent;
import net.kwmt27.codesearch.entity.events.WatchEvent;
import net.kwmt27.codesearch.libs.gson.RuntimeTypeAdapterFactory;

import java.util.ArrayList;
import java.util.List;

public class GsonFactory {
    public static Gson create() {

        RuntimeTypeAdapterFactory<EventEntity> eventEntityRuntimeTypeAdapterFactory
                = RuntimeTypeAdapterFactory.of(EventEntity.class);

        for (Class<? extends EventEntity> cls : getEventClassList()) {
            eventEntityRuntimeTypeAdapterFactory.registerSubtype(cls);
        }

        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .registerTypeAdapterFactory(eventEntityRuntimeTypeAdapterFactory)
                .create();
    }

    private static List<Class<? extends EventEntity>> getEventClassList() {
        // net.kwmt27.codesearch.entity.events's classes
        List<Class<? extends EventEntity>> list = new ArrayList<>();
        list.add(CommentEvent.class);
        list.add(CommitCommentEvent.class);
        list.add(CreateEvent.class);
        list.add(DeleteEvent.class);
        list.add(DeploymentEvent.class);
        list.add(DeploymentStatusEvent.class);
        list.add(DownloadEvent.class);
        list.add(FollowEvent.class);
        list.add(ForkApplyEvent.class);
        list.add(ForkEvent.class);
        list.add(GistEvent.class);
        list.add(GollumEvent.class);
        list.add(IssueCommentEvent.class);
        list.add(IssuesEvent.class);
        list.add(MemberEvent.class);
        list.add(MembershipEvent.class);
        list.add(PageBuildEvent.class);
        list.add(PublicEvent.class);
        list.add(PullRequestEvent.class);
        list.add(PullRequestReviewCommentEvent.class);
        list.add(PushEvent.class);
        list.add(ReleaseEvent.class);
        list.add(RepositoryEvent.class);
        list.add(StatusEvent.class);
        list.add(TeamAddEvent.class);
        list.add(WatchEvent.class);
        return list;
    }

}
