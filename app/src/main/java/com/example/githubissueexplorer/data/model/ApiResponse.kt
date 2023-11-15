package com.example.githubissueexplorer.data.model



import io.realm.RealmAny
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

import io.realm.annotations.RealmClass

@RealmClass
open class IssueResponseItem  (
    @PrimaryKey()
    var id: Int = 0,
    var title: String = "",
    var body: String? = "",
    var created_at: String = "",
    var user: UserRealm? = null,

    var labels: RealmList<LabelRealm> = RealmList(),
    var active_lock_reason: String? = null,
    var assignee: RealmAny? = null,
    var assignees: RealmList<RealmAny>?= null,

    var author_association: String? = null,
    var closed_at: RealmAny? = null,
    var comments: String = "",
    var comments_url: Boolean = false,
    var draft: String = "",
    var events_url: String = "",
    var html_url: String = "",
    var labels_url: String = "",
    var locked: Boolean = false,
    var milestone: RealmAny? = null,
    var node_id: String = "",
    var number: Int = 0,
    var performed_via_github_app: RealmAny? = null,
    var pull_request: PullRequestRealm? = null,
    var reactions: ReactionsRealm? = null,
    var repository_url: String = "",
    var state: String = "",
    var state_reason: String? = "",
    var timeline_url: String = "",
    var updated_at: String = "",
    var url: String = "",
): RealmObject()

@RealmClass
open class LabelRealm (
    var id: Long = 0,
    var color: String = "",
    var default: Boolean = false,
    var description: String = "",
    var name: String = "",
    var node_id: String = "",
    var url: String = "") : RealmObject()


@RealmClass
open class PullRequestRealm(
    var diff_url: String = "",
    var html_url: String = "",
    var merged_at: RealmAny? = null,
    var patch_url: String = "",
    var url: String = "",
):RealmObject()

@RealmClass
open class ReactionsRealm (
    var n1: Int = 0,
    var n2: Int = 0,
    var confused: Int = 0,
    var eyes: Int = 0,
    var heart: Int = 0,
    var hooray: Int = 0,
    var laugh: Int = 0,
    var rocket: Int = 0,
    var total_count: Int = 0,
    var url: String = ""
): RealmObject()

@RealmClass
open class UserRealm  (
    var avatar_url: String = "",
    var events_url: String = "",
    var followers_url: String = "",
    var following_url: String = "",
    var gists_url: String = "",
    var gravatar_id: String = "",
    var html_url: String = "",
    var id: Int = 0,
    var login: String = "",
    var node_id: String = "",
    var organizations_url: String = "",
    var received_events_url: String = "",
    var repos_url: String = "",
    var site_admin: Boolean = false,
    var starred_url: String = "",
    var subscriptions_url: String = "",
    var type: String = "",
    var url: String = "",
): RealmObject()

sealed class ApiResponse<out T> {
    data class Success<out T>(val data: T) : ApiResponse<T>()
    data class Failure(val errorMessage: String) : ApiResponse<Nothing>()
}
