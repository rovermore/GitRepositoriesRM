package com.example.rovermore.gitrepositoriesrm.main

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.rovermore.gitrepositoriesrm.GitHubAPI
import com.example.rovermore.gitrepositoriesrm.datamodel.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(var context: Context, var lastRepositoryID: Int?, var mainViewInterface: MainViewInterface) : MainPresenterInterface {

    val LOG_TAG = "MainPresenter"

    private var positionIndex = 0

    override fun getAllRepositories() {

        val gitHubApi by lazy {
            GitHubAPI.create()
        }

        val call: Call<MutableList<Repository>> = gitHubApi.getAllRepositories(lastRepositoryID.toString())
        call.enqueue(object : Callback<MutableList<Repository>> {

            override fun onResponse(call: Call<MutableList<Repository>>, response: Response<MutableList<Repository>>) {
                if (!response.isSuccessful) run {
                    Log.e(LOG_TAG, "Response code is: " + response.code())
                } else {
                    val repositoriesList = response.body()
                    if (repositoriesList != null) {
                        if(lastRepositoryID!!.equals(0)) {
                            mainViewInterface.onReceiveAllResults(repositoriesList)
                        } else {
                            positionIndex = positionIndex + repositoriesList.size
                            mainViewInterface.onReceivingMoreResults(positionIndex,repositoriesList)
                        }
                        lastRepositoryID = repositoriesList[repositoriesList.size -1].id
                        Toast.makeText(context, "OJOOOOOOO: $lastRepositoryID", Toast.LENGTH_LONG).show()
                    }
                    /*for (num in 0..repositoriesList?.size!!)
                        Log.i(LOG_TAG, Gson().toJson(repositoriesList))*/
                }
            }

            override fun onFailure(call: Call<MutableList<Repository>>, t: Throwable) {
                t?.printStackTrace()
            }
        })
    }
}

//StringBuilder(repositoryList[position].description.substring(0,20)).append("...").toString()