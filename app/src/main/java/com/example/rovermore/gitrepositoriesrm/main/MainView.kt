package com.example.rovermore.gitrepositoriesrm.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rovermore.gitrepositoriesrm.MainAdapter
import com.example.rovermore.gitrepositoriesrm.datamodel.Repository
import kotlinx.android.synthetic.main.activity_main.*


class MainView : AppCompatActivity(),MainViewInterface {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : MainAdapter
    private lateinit var repositoriesList: MutableList<Repository>
    private var pageMoreEntries = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.rovermore.gitrepositoriesrm.R.layout.activity_main)

        recyclerView = recycler_view
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(this,null)
        recyclerView.adapter = adapter

        val mainPresenterInterface: MainPresenterInterface = MainPresenter(this, 0, this)

        mainPresenterInterface.getAllRepositories()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if(pageMoreEntries){
                        mainPresenterInterface.getAllRepositories()
                        pageMoreEntries = false
                    }
                }
            }
        })
    }

    private fun createUi(repositoriesList: MutableList<Repository>){
        this.repositoriesList = repositoriesList
        adapter.updateRepositoriesList(this.repositoriesList)
    }

    override fun onReceiveAllResults(repositoriesList: MutableList<Repository>) {
        createUi(repositoriesList)
    }

    override fun onReceivingMoreResults(insertIndex: Int, newRepositoriesList: MutableList<Repository>) {
        this.repositoriesList.addAll(insertIndex,newRepositoriesList)
        recyclerView.adapter!!.notifyItemRangeInserted(insertIndex,newRepositoriesList.size)
        pageMoreEntries = true

    }

    override fun onErrorReceivingSearchResults() {
        /*Snackbar.make(searchButton, "Error when connecting the network", Snackbar.LENGTH_SHORT)
            .setAction("Retry", View.OnClickListener { mainPresenterInterface.getAllRepositories()() }).show()*/
    }

    override fun onErrorReceivingResults() {
        Toast.makeText(this,"Error al recibir datos del servidor",Toast.LENGTH_SHORT).show()
    }

}
