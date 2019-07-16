package com.example.rovermore.gitrepositoriesrm.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rovermore.gitrepositoriesrm.MainAdapter
import com.example.rovermore.gitrepositoriesrm.datamodel.Owner
import com.example.rovermore.gitrepositoriesrm.datamodel.Repository
import com.example.rovermore.gitrepositoriesrm.detail.DetailView
import kotlinx.android.synthetic.main.activity_main.*


class MainView : AppCompatActivity(),MainViewInterface, MainAdapter.OnItemClicked {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : MainAdapter
    private lateinit var repositoriesList: MutableList<Repository>
    private var pageMoreEntries = true

    private val LOGIN = "login"
    private val REPOSITORY = "repository"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.rovermore.gitrepositoriesrm.R.layout.activity_main)

        recyclerView = recycler_view
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(this,null, this)
        recyclerView.adapter = adapter

        val mainPresenterInterface: MainPresenterInterface = MainPresenter(0, this)

        mainPresenterInterface.getAllRepositories()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if(pageMoreEntries){
                        Toast.makeText(baseContext, "Loading...", Toast.LENGTH_SHORT).show()
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

    override fun onErrorReceivingResults(error: String) {
        Toast.makeText(this, error,Toast.LENGTH_SHORT).show()
    }

    override fun itemClicked(repository: Repository) {
        val owner: Owner? = repository.owner
        val login = owner!!.login

        intent = Intent(applicationContext, DetailView::class.java)
        intent.putExtra(LOGIN, login)
        intent.putExtra(REPOSITORY, repository.name)
        startActivity(intent)
    }

}
