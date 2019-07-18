package com.example.rovermore.gitrepositoriesrm

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class NetworkUtils () {

    val BASE_URL = "https://api.github.com/"

    fun getClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val httpClient = OkHttpClient.Builder()
        return httpClient.addInterceptor(logging).build()
    }

    companion object  {
        fun create(): NetworkUtils
        {
            val networkUtils = NetworkUtils()

            return networkUtils
        }
    }
}