package com.example.rovermore.gitrepositoriesrm.main

import com.example.rovermore.gitrepositoriesrm.datamodel.Repository

interface MainViewInterface {

    fun onReceiveAllResults(repositoriesList: List<Repository>)
}