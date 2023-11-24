package com.example.githubissueexplorer.module

import android.util.Log
import com.example.githubissueexplorer.data.model.Label
import com.example.githubissueexplorer.data.remote.IssueService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializationContext
import com.google.gson.JsonDeserializer
import com.google.gson.JsonElement
import com.google.gson.TypeAdapter
import com.google.gson.TypeAdapterFactory
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.realm.kotlin.ext.realmListOf
import io.realm.kotlin.ext.toRealmList
import io.realm.kotlin.types.RealmList
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.Type
import java.util.concurrent.TimeUnit.SECONDS

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  @Provides
  fun provideRetrofitInstance(): Retrofit {
    val loggingInterceptor = HttpLoggingInterceptor()
    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    val httpClient = OkHttpClient.Builder()
      .addInterceptor(loggingInterceptor).callTimeout(5, SECONDS)
      .build()
    val gson = GsonBuilder()
      .registerTypeAdapter(
        object : TypeToken<RealmList<Label>>() {}.type,
        RealmListDeserializer()
      ).create()

    return Retrofit.Builder().baseUrl("https://api.github.com/").client(httpClient)
      .addConverterFactory(GsonConverterFactory.create(gson))
      .build()
  }

  @Provides
  fun provideIssueService(retrofit: Retrofit): IssueService =
    retrofit.create(IssueService::class.java)
}


class RealmListDeserializer : JsonDeserializer<RealmList<Label>> {

  override fun deserialize(
    json: JsonElement?,
    typeOfT: Type?,
    context: JsonDeserializationContext?
  ): RealmList<Label> {
    val labels = mutableListOf<Label>()

    json?.asJsonArray?.forEach { jsonLabel ->
      val label = context?.deserialize<Label>(jsonLabel, Label::class.java)
      label?.let { labels.add(it) }
    }

    return labels.toRealmList()
  }
}