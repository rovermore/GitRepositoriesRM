package com.example.rovermore.gitrepositoriesrm.main

import com.example.rovermore.gitrepositoriesrm.datamodel.Repository

interface MainViewInterface {

    fun onReceiveAllResults(repositoriesList: MutableList<Repository>)

    fun onReceivingMoreResults(insertIndex: Int, newRepositoriesList: MutableList<Repository>)

    fun onErrorReceivingResults()

    fun onErrorReceivingSearchResults()
}