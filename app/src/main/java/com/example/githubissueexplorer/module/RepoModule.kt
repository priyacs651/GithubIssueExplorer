package com.example.githubissueexplorer.module

import com.example.githubissueexplorer.data.repo.IssueRepo
import com.example.githubissueexplorer.data.repo.IssueRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
object RepoModule {
    @Provides
    fun provideIssueRepo(repoImpl: IssueRepoImpl): IssueRepo = repoImpl
}