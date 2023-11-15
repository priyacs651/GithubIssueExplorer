package com.example.githubissueexplorer.data.repo

import android.util.Log
import androidx.compose.animation.rememberSplineBasedDecay
import com.example.githubissueexplorer.data.model.ApiResponse
import com.example.githubissueexplorer.data.model.IssueResponseItem
import com.example.githubissueexplorer.data.remote.IssueService
import io.realm.Realm
import io.realm.RealmConfiguration
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IssueRepoImpl @Inject constructor(
    private val isuueService: IssueService, private val realmConfig: RealmConfiguration
) : IssueRepo {

    override suspend fun getIssuesFromRemote(): ApiResponse<List<IssueResponseItem>> {

        val response = isuueService.getAllIssue().body()
        return response?.let {
            insertIssueIntoLocal(it)
            ApiResponse.Success(it)
        } ?: kotlin.run {
            ApiResponse.Failure("Please try again later")
        }
    }


    override suspend fun getIssuesFromLocal(): List<IssueResponseItem> = withContext(Dispatchers.IO) {
           Realm.getInstance(realmConfig).use {realm ->
                realm.copyFromRealm(realm.where(IssueResponseItem::class.java)?.findAll())
            }
       }


    override suspend fun deleteIssueFromLocal(id: Int) = withContext(Dispatchers.IO) {
        Realm.getInstance(realmConfig).use { r ->
            r.executeTransaction { realm ->
                realm.where(IssueResponseItem::class.java).equalTo("id", id).findFirst()
                    ?.deleteFromRealm()
            }
        }
    }


    private suspend fun insertIssueIntoLocal(issueEntities: List<IssueResponseItem>) = withContext(Dispatchers.IO) {
            Realm.getInstance(realmConfig).use { r ->
                r.executeTransaction { realm ->
                    realm.insertOrUpdate(issueEntities)
                }
            }
        }


}





