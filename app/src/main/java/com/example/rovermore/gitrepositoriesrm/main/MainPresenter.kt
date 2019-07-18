package com.example.rovermore.gitrepositoriesrm.main

import android.util.Log
import com.example.rovermore.gitrepositoriesrm.datamodel.Repository
import com.example.rovermore.gitrepositoriesrm.datamodel.Search
import com.example.rovermore.gitrepositoriesrm.network.GitHubAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainPresenter(var lastRepositoryID: Int?, var mainViewInterface: MainViewInterface) : MainPresenterInterface {

    private val LOG_TAG = "MainPresenter"
    private val ERROR = "Error al recibir datos del servidor"
    private val NO_RESULTS_FOUND = "No se encontraron resultados"
    private var positionIndex = 0

    override fun getAllRepositories(isFreshFetch: Boolean) {

        if(isFreshFetch) {
            positionIndex = 0
            lastRepositoryID = 0
        }

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

                        if(isFreshFetch) {
                            //positionIndex = 0
                            //lastRepositoryID = 0
                            mainViewInterface.onReceiveFirstResults(repositoriesList)

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

    override fun getSearchRepositories(search: String, pageNumber: Int) {

        val gitHubApi by lazy {
            GitHubAPI.create()
        }

        val call: Call<Search> = gitHubApi.searchRepositories(search, pageNumber.toString())

        call.enqueue(object:Callback<Search>{

            override fun onResponse(call: Call<Search>, response: Response<Search>) {

                if (!response.isSuccessful) run {

                    Log.e(LOG_TAG, "Response code is: " + response.code())

                        mainViewInterface.onErrorReceivingResults(ERROR)


                } else {

                    val search = response.body()

                    if(search?.totalCount != 0) {

                        val repositoriesList = search?.repository

                        if (repositoriesList != null) {

                            if(pageNumber==1) {

                                positionIndex = 0
                                mainViewInterface.onReceiveFirstResults(repositoriesList)

                            } else {

                                positionIndex = positionIndex + repositoriesList.size
                                mainViewInterface.onReceivingMoreResults(positionIndex,repositoriesList)
                            }
                        }

                    } else {

                        Log.d(LOG_TAG, "RESPONSE BODY IS NULL")
                        mainViewInterface.onResultsNotFound(NO_RESULTS_FOUND)
                    }
                }
            }

            override fun onFailure(call: Call<Search>, t: Throwable) {
                t?.printStackTrace()
                mainViewInterface.onErrorReceivingResults(ERROR)
            }
        })


    }

}
