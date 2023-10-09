package com.example.githubissueexplorer.module

import com.example.githubissueexplorer.data.remote.IssueService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun provideRetrofitInstance(): Retrofit {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
        return Retrofit.Builder().baseUrl("https://api.github.com/").client(httpClient)
            .addConverterFactory(
                GsonConverterFactory.create()
            ).build()
    }

    @Provides
    fun provideIssueService(retrofit: Retrofit): IssueService = retrofit.create(IssueService::class.java)

}