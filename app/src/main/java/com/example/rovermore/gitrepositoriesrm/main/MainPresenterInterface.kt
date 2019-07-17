package com.example.rovermore.gitrepositoriesrm.main

interface MainPresenterInterface {

    fun getAllRepositories(isFreshFetch: Boolean)

    fun getSearchRepositories(search: String, pageNumber : Int)

}