package com.example.rovermore.gitrepositoriesrm.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.rovermore.gitrepositoriesrm.R

class MainView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var mainPresenterInterface: MainPresenterInterface = MainPresenter(0)

        mainPresenterInterface.getAllRepositories()
    }


}
