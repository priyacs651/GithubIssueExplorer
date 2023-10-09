package com.example.githubissueexplorer.data.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class IssueResponseItem(
    @PrimaryKey
    val id: Int,
    val title: String,
    val body: String,
    val created_at: String,
    @Embedded
    val user: User,

    @Ignore
    val labels: List<Label>,
    @Ignore
    val active_lock_reason: Any?,
    @Ignore
    val assignee: Any?,
    @Ignore
    val assignees: List<Any>,
    @Ignore
    val author_association: String?,
    @Ignore
    val closed_at: Any?,
    @Ignore
    val comments: String,
    @Ignore
    val comments_url: Boolean,
    @Ignore
    val draft: String,
    @Ignore
    val events_url: String,
    @Ignore
    val html_url: String,
    @Ignore
    val labels_url: String,
    @Ignore
    val locked: Boolean,
    @Ignore
    val milestone: Any?,
    @Ignore
    val node_id: String,
    @Ignore
    val number: Int,
    @Ignore
    val performed_via_github_app: Any?,
    @Ignore
    val pull_request: PullRequest,
    @Ignore
    val reactions: Reactions,
    @Ignore
    val repository_url: String,
    @Ignore
    val state: String,
    @Ignore
    val state_reason: String,
    @Ignore
    val timeline_url: String,
    @Ignore
    val updated_at: String,
    @Ignore
    val url: String,
) {
    constructor(id: Int, title: String, body: String, created_at: String, user: User):this(id, title, body, created_at, user,
        emptyList(),null,null,
        emptyList(),"",null,"",false,"","",
        "","",false,null,"",0,null,
        PullRequest("","",null,"",""),
        Reactions(0,0,0,0,0,0,0,0,0,""),"","","","","",""
    )
}

data class Label(
    val id: Long,
    val color: String,
    val default: Boolean,
    val description: String,

    val name: String,
    val node_id: String,
    val url: String
)

data class PullRequest(
    val diff_url: String,
    val html_url: String,
    val merged_at: Any?,
    val patch_url: String,
    val url: String
)

data class Reactions(
    @SerializedName("+1")
    val n1: Int,
    @SerializedName("-1")
    val n2: Int,
    val confused: Int,
    val eyes: Int,
    val heart: Int,
    val hooray: Int,
    val laugh: Int,
    val rocket: Int,
    val total_count: Int,
    val url: String
)

data class User(
    val avatar_url: String,
    val events_url: String,
    val followers_url: String,
    val following_url: String,
    val gists_url: String,
    val gravatar_id: String,
    val html_url: String,
    @ColumnInfo(name = "userId")
    val id: Int,
    val login: String,
    val node_id: String,
    val organizations_url: String,
    val received_events_url: String,
    val repos_url: String,
    val site_admin: Boolean,
    val starred_url: String,
    val subscriptions_url: String,
    val type: String,
    val url: String
)


sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Failure(val errorMessage: String) : ApiResponse<Nothing>()
}
