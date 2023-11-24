package com.example.githubissueexplorer.data.model

import android.animation.TypeConverter
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.google.gson.reflect.TypeToken
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.types.RealmAny
import io.realm.kotlin.types.RealmList
import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import java.lang.reflect.Type

class IssueResponseItemModel() : RealmObject {
  @PrimaryKey
  var id: Int = 0
  var title: String = ""
  var body: String? = ""
  @SerializedName("created_at")
  var createdAt: String = ""
  var user: UserModel? = null

  // added new field v2
  @SerializedName("node_id")
  var nodeId : String = ""

  // added new fields v3

  @SerializedName("closed_at")
  var closedAt: RealmAny? = null
  var comments: String = ""
  var labels :RealmList<Label>? = null

}

class UserModel() : RealmObject {
  @SerializedName("avatar_url")
  var avatarUrl: String = ""
  var login: String = ""
}


class Label() :RealmObject{
  var id: Long = 0
  var color: String = ""
  var default: Boolean = false
  var description: String = ""

  var name: String = ""
  var node_id: String = ""
  var url: String =""

}





sealed class ApiResponse<out T> {
  data class Success<out T>(val data: T) : ApiResponse<T>()
  data class Failure(val errorMessage: String) : ApiResponse<Nothing>()
}
