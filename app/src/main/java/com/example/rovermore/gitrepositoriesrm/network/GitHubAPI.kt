package com.example.rovermore.gitrepositoriesrm.network

import com.example.rovermore.gitrepositoriesrm.datamodel.Repository
import com.example.rovermore.gitrepositoriesrm.datamodel.RepositoryDetail
import com.example.rovermore.gitrepositoriesrm.datamodel.Search
import retrofit2.Call
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

}