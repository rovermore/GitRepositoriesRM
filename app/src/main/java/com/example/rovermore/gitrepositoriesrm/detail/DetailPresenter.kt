package com.example.rovermore.gitrepositoriesrm.detail

import android.util.Log
import com.example.rovermore.gitrepositoriesrm.network.GitHubAPI
import com.example.rovermore.gitrepositoriesrm.datamodel.RepositoryDetail
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailPresenter(var login: String, var reporitoryName: String, var detailViewInterface: DetailViewInterface): DetailPresenterInterface {

    private val LOG_TAG = "Detail Presenter"
    private val ERROR_MSG = "Error al recibir datos desde el servidor"

    override fun getRepositoryDetail() {

        val gitHubApi by lazy {
            GitHubAPI.create()
        }

        val call: Call<RepositoryDetail> = gitHubApi.getRepository(login, reporitoryName)
        call.enqueue(object: Callback<RepositoryDetail>{

            override fun onResponse(call: Call<RepositoryDetail>, response: Response<RepositoryDetail>) {
                if (!response.isSuccessful) run {
                    Log.e(LOG_TAG, "Response code is: " + response.code())
                    detailViewInterface.onErrorReceivingRepositoryDetail(ERROR_MSG)
                } else {
                    val repositoryDetail = response.body()
                    if(repositoryDetail != null){
                        detailViewInterface.onReceiveRepositoryDetail(repositoryDetail)
                    } else {
                        detailViewInterface.onErrorReceivingRepositoryDetail(ERROR_MSG)
                    }
                }
            }

            override fun onFailure(call: Call<RepositoryDetail>, t: Throwable) {
                t?.printStackTrace()
                detailViewInterface.onErrorReceivingRepositoryDetail(ERROR_MSG)
            }
        })
    }

}