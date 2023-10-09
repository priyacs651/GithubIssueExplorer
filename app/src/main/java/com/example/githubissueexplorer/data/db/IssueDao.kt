package com.example.githubissueexplorer.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubissueexplorer.data.model.IssueResponseItem

@Dao
interface IssueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllIssue(issueEntity: List<IssueResponseItem>)

    @Query("SELECT * FROM IssueResponseItem ORDER BY created_at DESC")
    suspend fun getAllIssue(): List<IssueResponseItem>
}