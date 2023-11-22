package com.example.githubissueexplorer.data.model

import com.google.gson.annotations.SerializedName
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey


class IssueResponseItemModel() : RealmObject {
  @PrimaryKey
  var id: Int = 0
  var title: String = ""
  var body: String? = ""

  @SerializedName("created_at")
  var createdAt: String = ""
  var user: UserModel? = null
}

class UserModel() : RealmObject {
  @SerializedName("avatar_url")
  var avatarUrl: String = ""
  var login: String = ""
}

sealed class ApiResponse<out T> {
  data class Success<out T>(val data: T) : ApiResponse<T>()
  data class Failure(val errorMessage: String) : ApiResponse<Nothing>()
}
