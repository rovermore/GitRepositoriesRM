package com.example.rovermore.gitrepositoriesrm.networkutils

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class NetworkUtils (context: Context) {

    val BASE_URL = "https://api.github.com/"

    fun getClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        return httpClient.addInterceptor(logging).build()
    }
}