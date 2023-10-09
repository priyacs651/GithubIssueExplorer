package com.example.githubissueexplorer.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.githubissueexplorer.data.db.IssueDao
import com.example.githubissueexplorer.data.model.IssueResponseItem

@Database(entities = [IssueResponseItem ::class], version = 1)
abstract class IssueDB : RoomDatabase() {
    abstract fun issueDao(): IssueDao

    companion object {
        const val dbName = "IssueResponseItem"
    }
}