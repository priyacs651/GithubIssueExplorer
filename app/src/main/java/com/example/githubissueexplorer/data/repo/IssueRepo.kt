package com.example.githubissueexplorer.data.repo

import com.example.githubissueexplorer.data.model.ApiResponse
import com.example.githubissueexplorer.data.model.IssueResponseItem

interface IssueRepo {
    suspend fun getIssuesFromRemote():ApiResponse<List<IssueResponseItem>>
   suspend fun getIssuesFromLocal():List<IssueResponseItem>
   suspend fun deleteIssueFromLocal(id: Int)
}