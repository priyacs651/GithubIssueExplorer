package com.example.githubissueexplorer.data.repo

import android.content.Context
import com.example.githubissueexplorer.data.db.IssueDao
import com.example.githubissueexplorer.data.model.ApiResponse
import com.example.githubissueexplorer.data.remote.IssueService
import com.example.githubissueexplorer.data.model.IssueResponseItem
import javax.inject.Inject

class IssueRepoImpl @Inject constructor(
    private val dao: IssueDao,
    private val isuueService: IssueService
) : IssueRepo {

    override suspend fun getIssuesFromRemote(): ApiResponse<List<IssueResponseItem>> {
        val response = isuueService.getAllIssue().body()

        return response?.let {
            insert(it)
            ApiResponse.Success(it)
        } ?: kotlin.run {
            ApiResponse.Failure("Please try again later")
        }
    }

    override suspend fun getIssuesFromDb(): List<IssueResponseItem> {
        return dao.getAllIssue()
    }

    suspend fun insert(issueEntity: List<IssueResponseItem>) {
        dao.insertAllIssue(issueEntity)
    }




}