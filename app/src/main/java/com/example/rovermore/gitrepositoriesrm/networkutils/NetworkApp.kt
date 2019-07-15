package com.example.rovermore.gitrepositoriesrm.networkutils

import android.app.Application

class NetworkApp: Application() {

    companion object {
        lateinit var networkUtils: NetworkUtils
    }

    override fun onCreate() {
        super.onCreate()
        networkUtils =
            NetworkUtils(applicationContext)
    }
}