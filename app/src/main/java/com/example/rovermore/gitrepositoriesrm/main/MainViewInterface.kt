package com.example.rovermore.gitrepositoriesrm.main

import com.example.rovermore.gitrepositoriesrm.datamodel.Repository

interface MainViewInterface {

    fun onReceiveFirstResults(repositoriesList: MutableList<Repository>)

    fun onReceivingMoreResults(insertIndex: Int, newRepositoriesList: MutableList<Repository>)

    fun onErrorReceivingResults(error: String)

    fun onResultsNotFound(error: String)

}