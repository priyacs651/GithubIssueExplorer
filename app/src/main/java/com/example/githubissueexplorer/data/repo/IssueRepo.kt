package com.example.githubissueexplorer.data.repo

import com.example.githubissueexplorer.data.model.ApiResponse
import com.example.githubissueexplorer.data.model.IssueResponseItemModel
interface IssueRepo {
  suspend fun getIssuesFromRemote(): ApiResponse<List<IssueResponseItemModel>>
  suspend fun getIssuesFromLocal(): List<IssueResponseItemModel>
  suspend fun deleteIssueFromLocal(id: Int)
  suspend fun updateIssueFromLocal(id : Int,login:String,title:String)
}