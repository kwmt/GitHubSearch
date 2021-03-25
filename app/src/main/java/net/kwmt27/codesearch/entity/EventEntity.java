package net.kwmt27.codesearch.entity;

import android.content.Context;
import androidx.annotation.NonNull;
import android.text.format.DateFormat;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.flexbox.FlexboxLayout;
import com.google.gson.annotations.SerializedName;

import net.kwmt27.codesearch.util.TextViewUtil;
import net.kwmt27.codesearch.view.events.EventListFragment;

import java.util.Date;

public class EventEntity extends BaseEntity{
    @SerializedName("type")
    private String mType;
    @SerializedName("public")
    private boolean mIsPublic;
    @SerializedName("actor")
    private ActorEntity mActor;

    @SerializedName("repo")
    private GithubRepoEntity mRepo;
    @SerializedName("created_at")
    private Date mCreatedAt;
    @SerializedName("id")
    private String mId;

    public EventEntity() {
    }

    public EventEntity(ItemType itemType) {
        super(itemType);
    }


    public boolean isPublic() {
        return mIsPublic;
    }

    public ActorEntity getActor() {
        return mActor;
    }

    public GithubRepoEntity getRepo() {
        return mRepo;
    }

    public Date getCreatedAt() {
        return mCreatedAt;
    }

    public String getFormattedCreatedAt() {
        if (mCreatedAt == null) {
            return "not yet created";
        }
        return DateFormat.format("yyyy/MM/dd HH:mm:ss", mCreatedAt).toString();
    }

    public String getId() {
        return mId;
    }

    public void action(EventEntity eventEntity) {

        String repoName = getRepo().getName();
        String action = this.getClass().getSimpleName().replace("Event", "").toLowerCase() + "d " + repoName;
    }

    public View createView(Context context, EventListFragment.OnLinkClickListener listener) {
        // TODO
        FlexboxLayout flexboxLayout = newFlexboxLayout(context);


        TextView repoNameTextView = newTextView(context, getRepo().getName(), true, new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onLinkClick(getRepo().getName(), getRepo().getHtmlUrl(), getRepo());
                }
            }
        });
        flexboxLayout.addView(repoNameTextView);



        TextView textView3 = newTextView(context, getActor().getLogin(), false, new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onLinkClick(getActor().getLogin(), getActor().getHtmlUrl(), getRepo());
                }
            }
        });
        flexboxLayout.addView(textView3);

        return flexboxLayout;

    }

    @NonNull
    protected FlexboxLayout newFlexboxLayout(Context context) {
        FlexboxLayout flexboxLayout = new FlexboxLayout(context);
        flexboxLayout.setFlexWrap(FlexboxLayout.FLEX_WRAP_WRAP);
        flexboxLayout.setAlignContent(FlexboxLayout.ALIGN_CONTENT_STRETCH);
        flexboxLayout.setAlignItems(FlexboxLayout.ALIGN_ITEMS_STRETCH);
        return flexboxLayout;
    }

    @NonNull
    protected TextView newTextView(Context context, String name, boolean setRightPadding, ClickableSpan clickableSpan) {
        TextView textView = new TextView(context);
        textView.setText(name);
        if (setRightPadding) {
            final float density = context.getResources().getDisplayMetrics().density;
            textView.setPadding(0, 0, (int) (density * 8), 0);
        }

        if(clickableSpan!=null) {
            TextViewUtil.addLink(textView, name, clickableSpan);
        }


        textView.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        return textView;
    }

    /**
     * リンクをクリックしたときの処理を返す
     * @param listener {@link EventListFragment} に返すためのリスナー
     * @param title {@link net.kwmt27.codesearch.view.detail.DetailActivity}のタイトル
     * @param url {@link net.kwmt27.codesearch.view.detail.DetailActivity}に表示するためのURL
     * @param githubRepoEntity {@link net.kwmt27.codesearch.view.search.SearchActivity}でレポジトリ検索するために必要なレポジトリ情報
     * @return
     */
    
    protected ClickableSpan newOnLinkClickClickableSpan(EventListFragment.OnLinkClickListener listener, String title, String url, GithubRepoEntity githubRepoEntity){
        return new ClickableSpan() {
            @Override
            public void onClick(View view) {
                if (listener != null) {
                    listener.onLinkClick(title, url, githubRepoEntity);
                }
            }
        };
    }
}
