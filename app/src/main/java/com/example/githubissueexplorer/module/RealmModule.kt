package com.example.githubissueexplorer.module
import com.example.githubissueexplorer.data.model.IssueResponseItemModel
import com.example.githubissueexplorer.data.model.UserModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RealmModule {
  @Provides
  @Singleton
  fun providesRealmDatabase(): Realm {
    val config = RealmConfiguration
      .Builder(schema = setOf(IssueResponseItemModel::class, UserModel::class))
      .name("Issue_Explorer").deleteRealmIfMigrationNeeded().build()
    return Realm.open(config)
  }
}