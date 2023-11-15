package com.example.githubissueexplorer.module

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.realm.Realm
import io.realm.RealmConfiguration
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RealmModule {
    @Provides
    @Singleton
    fun providesRealmDatabase(@ApplicationContext context: Context): RealmConfiguration {
        Realm.init(context)
        return RealmConfiguration
            .Builder()
            .name("Issue_Explorer")
            .build()

    }
}