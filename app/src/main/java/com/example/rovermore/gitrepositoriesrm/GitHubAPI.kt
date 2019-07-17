package com.example.rovermore.gitrepositoriesrm

import com.example.rovermore.gitrepositoriesrm.datamodel.Repository
import com.example.rovermore.gitrepositoriesrm.datamodel.RepositoryDetail
import com.example.rovermore.gitrepositoriesrm.datamodel.Search
import com.example.rovermore.gitrepositoriesrm.networkutils.NetworkApp
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubAPI {


    @GET("repositories?")
     fun getAllRepositories(@Query("since") lastRepositoryID: String): Call<MutableList<Repository>>

    @GET("search/repositories?")
    fun searchRepositories(@Query("q") search: String,
                           @Query("page") page: String): Call<Search>

    @GET("repos/{user}/{repo}")
     fun getRepository(
        @Path("user") user: String,
        @Path("repo") repo: String
    ): Call<RepositoryDetail>

    companion object {
        fun create(): GitHubAPI {
            val retrofit: Retrofit = Retrofit.Builder()
                .client(NetworkApp.networkUtils.getClient())
                        .addConverterFactory(GsonConverterFactory.create())
                        .baseUrl(NetworkApp.networkUtils.BASE_URL)
                        .build()
            return retrofit.create(GitHubAPI::class.java)
        }
    }
}