package com.example.rovermore.gitrepositoriesrm.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rovermore.gitrepositoriesrm.MainAdapter
import com.example.rovermore.gitrepositoriesrm.R
import com.example.rovermore.gitrepositoriesrm.datamodel.Repository
import kotlinx.android.synthetic.main.activity_main.*

class MainView : AppCompatActivity(),MainViewInterface {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainPresenterInterface: MainPresenterInterface = MainPresenter(0, this)

        mainPresenterInterface.getAllRepositories()
    }

    fun createUi(repositoriesList: List<Repository>){
        recyclerView = recycler_view
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(this,repositoriesList)
        recyclerView.adapter = adapter
    }

    override fun onReceiveAllResults(repositoriesList: List<Repository>) {
        createUi(repositoriesList)
    }

}
