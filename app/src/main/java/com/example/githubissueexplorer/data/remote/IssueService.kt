package com.example.githubissueexplorer.data.remote

import com.example.githubissueexplorer.data.model.IssueResponseItem
import retrofit2.Response
import retrofit2.http.GET

interface IssueService {
    @GET("repos/square/okhttp/issues")
    suspend fun getAllIssue() :Response<List<IssueResponseItem>>
}


