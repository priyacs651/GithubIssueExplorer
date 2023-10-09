package com.example.githubissueexplorer.module

import android.content.Context
import androidx.room.Room
import com.example.githubissueexplorer.data.db.IssueDB
import com.example.githubissueexplorer.data.db.IssueDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    fun provideIssueDbInstance(context: Context): IssueDB {
        return Room.databaseBuilder(
            context.applicationContext,
            IssueDB::class.java,
            IssueDB.dbName
        ).build()
    }

    @Provides
    fun provideIssueDao(issueDB: IssueDB): IssueDao = issueDB.issueDao()

    @Provides
    fun provideContext(@ApplicationContext context: Context) = context

}