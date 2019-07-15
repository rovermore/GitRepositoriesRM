package com.example.rovermore.gitrepositoriesrm.main

import android.util.Log
import com.example.rovermore.gitrepositoriesrm.GitHubAPI
import com.example.rovermore.gitrepositoriesrm.datamodel.Repository
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(var lastRepositoryID: Int) : MainPresenterInterface {

    val LOG_TAG = "MainPresenter"

    override fun getAllRepositories() {

        val gitHubApi by lazy {
            GitHubAPI.create()
        }

        val call: Call<List<Repository>> = gitHubApi.getAllRepositories(lastRepositoryID.toString())
        call.enqueue(object : Callback<List<Repository>> {

            override fun onResponse(call: Call<List<Repository>>?, response: Response<List<Repository>>?) {
                if (!response!!.isSuccessful) run {
                    Log.e(LOG_TAG, "Response code is: " + response.code())
                } else {
                    val repositoriesList = response?.body()
                    for (num in 0..repositoriesList?.size!!)
                        Log.i(LOG_TAG, Gson().toJson(repositoriesList))
                }
            }

            override fun onFailure(call: Call<List<Repository>>?, t: Throwable?) {
                t?.printStackTrace()
            }
        })
    }
}