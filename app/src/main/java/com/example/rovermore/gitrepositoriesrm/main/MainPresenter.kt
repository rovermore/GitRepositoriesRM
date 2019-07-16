package com.example.rovermore.gitrepositoriesrm.main

import android.util.Log
import com.example.rovermore.gitrepositoriesrm.GitHubAPI
import com.example.rovermore.gitrepositoriesrm.datamodel.Repository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(var lastRepositoryID: Int?, var mainViewInterface: MainViewInterface) : MainPresenterInterface {

    private val LOG_TAG = "MainPresenter"
    private val ERROR = "Error al recibir datos del servidor"
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
                    mainViewInterface.onErrorReceivingResults(ERROR)
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
                    } else {
                        mainViewInterface.onErrorReceivingResults(ERROR)
                    }

                }
            }

            override fun onFailure(call: Call<MutableList<Repository>>, t: Throwable) {
                t?.printStackTrace()
                mainViewInterface.onErrorReceivingResults(ERROR)
            }
        })
    }
}
