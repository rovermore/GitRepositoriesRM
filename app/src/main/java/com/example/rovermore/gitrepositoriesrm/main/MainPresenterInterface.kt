package com.example.rovermore.gitrepositoriesrm.main

interface MainPresenterInterface {

    fun getAllRepositories()

    fun getSearchRepositories(search: String, pageNumber : Int)

}