package com.example.rovermore.gitrepositoriesrm.main



import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rovermore.gitrepositoriesrm.MainAdapter
import com.example.rovermore.gitrepositoriesrm.R
import com.example.rovermore.gitrepositoriesrm.datamodel.Owner
import com.example.rovermore.gitrepositoriesrm.datamodel.Repository
import com.example.rovermore.gitrepositoriesrm.detail.DetailView
import kotlinx.android.synthetic.main.activity_main.*


class MainView : AppCompatActivity(), MainViewInterface, MainAdapter.OnItemClicked {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter : MainAdapter
    private lateinit var repositoriesList: MutableList<Repository>
    private var pageMoreEntries = true
    private var isSearchedButtonClicked = false
    private var pageNumber = 0
    private lateinit var search: String
    private val LOGIN = "login"
    private val REPOSITORY = "repository"
    private lateinit var mainPresenterInterface: MainPresenterInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.rovermore.gitrepositoriesrm.R.layout.activity_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        progressbar_main.visibility = View.VISIBLE

        recyclerView = recycler_view

        recyclerView.visibility = View.GONE

        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(this,null, this)
        recyclerView.adapter = adapter

        mainPresenterInterface = MainPresenter(0, this)

        mainPresenterInterface.getAllRepositories(true)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1)) {
                    if(pageMoreEntries){
                        Toast.makeText(baseContext, "Loading...", Toast.LENGTH_SHORT).show()
                        if(isSearchedButtonClicked){
                            pageNumber = pageNumber + 1
                            mainPresenterInterface.getSearchRepositories(search, pageNumber)
                        } else {
                            mainPresenterInterface.getAllRepositories(false)
                        }
                        pageMoreEntries = false
                    }
                }
            }
        })

        button_search.setOnClickListener {
            progressbar_main.visibility = View.VISIBLE
            recyclerView.visibility = View.GONE
            pageNumber = 1
            search = et_search.text.toString()
            mainPresenterInterface.getSearchRepositories(search, pageNumber)
            isSearchedButtonClicked = true
        }
    }

    private fun createUi(repositoriesList: MutableList<Repository>){
        progressbar_main.visibility = View.GONE
        recyclerView.visibility = View.VISIBLE
        this.repositoriesList = repositoriesList
        adapter.clearMainAdapter()
        adapter.updateRepositoriesList(this.repositoriesList)
    }

    override fun onReceiveFirstResults(repositoriesList: MutableList<Repository>) {
        createUi(repositoriesList)
    }

    override fun onReceivingMoreResults(insertIndex: Int, newRepositoriesList: MutableList<Repository>) {
        this.repositoriesList.addAll(insertIndex,newRepositoriesList)
        recyclerView.adapter!!.notifyItemRangeInserted(insertIndex,newRepositoriesList.size)
        pageMoreEntries = true

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main_view, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        when(item?.itemId){

            R.id.clear_results ->{
                progressbar_main.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                isSearchedButtonClicked = false
                mainPresenterInterface.getAllRepositories(true)
                et_search.text.clear()


            }
        }

        return super.onOptionsItemSelected(item)
    }
}
