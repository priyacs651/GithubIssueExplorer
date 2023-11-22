package com.example.githubissueexplorer.data.repo

import android.util.Log
import com.example.githubissueexplorer.data.model.ApiResponse
import com.example.githubissueexplorer.data.model.IssueResponseItemModel
import com.example.githubissueexplorer.data.remote.IssueService
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import io.realm.kotlin.UpdatePolicy.ALL
import io.realm.kotlin.ext.isManaged
import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import org.mongodb.kbson.ObjectId

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IssueRepoImpl @Inject constructor(
  private val isuueService: IssueService, private val realm: Realm
) : IssueRepo {

  override suspend fun getIssuesFromRemote(): ApiResponse<List<IssueResponseItemModel>> {
    val response = isuueService.getAllIssue().body()
    return response?.let {
      insertIssueIntoLocal(it)
      ApiResponse.Success(it)
    } ?: kotlin.run {
      ApiResponse.Failure("Please try again later")
    }
  }

  override suspend fun getIssuesFromLocal(): List<IssueResponseItemModel> {
    return withContext(Dispatchers.IO) {
      realm.write {
        val list = realm.query<IssueResponseItemModel>().find()
        list
      }

    }
  }

  override suspend fun deleteIssueFromLocal(id: Int): Unit = withContext(Dispatchers.IO) {

    realm.write {

          val frozenObject = realm.query<IssueResponseItemModel>("id == $0", id).find().first()
          val liveObject = findLatest(frozenObject)
          liveObject?.let { delete(it) }
    }
  }

  override suspend fun updateIssueFromLocal(id: Int, login: String, title: String) {
    realm.write {
      val frozenObject = realm.query<IssueResponseItemModel>("id== $0", id).find().first()
      findLatest(frozenObject)?.let { issueResponseItemModel ->
        issueResponseItemModel.user?.login = login
        issueResponseItemModel.title = title
      }

    }
  }

  private suspend fun insertIssueIntoLocal(issueEntities: List<IssueResponseItemModel>) =
    withContext(Dispatchers.IO) {
      realm.write {
        issueEntities.forEach {
          copyToRealm(it, ALL)
        }
      }

    }
}





