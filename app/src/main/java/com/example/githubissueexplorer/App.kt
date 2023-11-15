package com.example.githubissueexplorer

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration

@HiltAndroidApp
class App: Application() {
    override fun onCreate() {
        super.onCreate()
    }

}