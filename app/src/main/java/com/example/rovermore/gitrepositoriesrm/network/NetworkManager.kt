package com.example.rovermore.gitrepositoriesrm.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class NetworkManager {

    private val BASE_URL = "https://api.github.com/"
    lateinit var gitHubApi: GitHubAPI

    init {
        setUpGitHubApi()
    }

    private fun setUpGitHubApi(){
        val retrofit = getRetrofitInstance()
        gitHubApi = retrofit.create(GitHubAPI::class.java)
    }

    private fun getClient(): OkHttpClient {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        val httpClient = OkHttpClient.Builder()
        return httpClient.addInterceptor(logging).build()
    }

    private fun getRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .client(getClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
    }
}