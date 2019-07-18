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
    private lateinit var adapter: MainAdapter
    private lateinit var repositoriesList: MutableList<Repository>
    private var isSearchedButtonClicked = false
    private var pageNumber = 0
    private lateinit var search: String
    private val LOGIN = "login"
    private val REPOSITORY = "repository"
    private lateinit var mainPresenterInterface: MainPresenterInterface
    private var isScrolling = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.rovermore.gitrepositoriesrm.R.layout.activity_main)
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)

        progressbar_main.visibility = View.VISIBLE

        recyclerView = recycler_view

        recyclerView.visibility = View.GONE

        val layoutManager = LinearLayoutManager(this)

        recyclerView.layoutManager = layoutManager
        adapter = MainAdapter(this, null, this)
        recyclerView.adapter = adapter

        mainPresenterInterface = MainPresenter(0, this)

        mainPresenterInterface.getAllRepositories(true)

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                if (dy > 0) {

                    val visibleItemCount = layoutManager.childCount
                    val scrolledItems = layoutManager.findFirstCompletelyVisibleItemPosition()
                    val totalCount = layoutManager.itemCount

                    if (isScrolling && (visibleItemCount + scrolledItems) == totalCount
                        || !recyclerView.canScrollVertically(1)) {

                        progressbar_scroll.visibility = View.VISIBLE

                        if (isSearchedButtonClicked) {

                            pageNumber++
                            mainPresenterInterface.getSearchRepositories(search, pageNumber)

                        } else {

                            mainPresenterInterface.getAllRepositories(false)

                        }
                    }

                }

                super.onScrolled(recyclerView, dx, dy)
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                isScrolling = true

            }
        })

        button_search.setOnClickListener {
            progressbar_main.visibility = View.VISIBLE
            progressbar_scroll.visibility = View.GONE
            recyclerView.visibility = View.GONE
            pageNumber = 1
            search = et_search.text.toString()
            mainPresenterInterface.getSearchRepositories(search, pageNumber)
            isSearchedButtonClicked = true
        }
    }

    private fun createUi(repositoriesList: MutableList<Repository>) {
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
        progressbar_scroll.visibility = View.GONE
        this.repositoriesList.addAll(insertIndex, newRepositoriesList)
        recyclerView.adapter!!.notifyItemRangeInserted(insertIndex, newRepositoriesList.size)
    }

    override fun onErrorReceivingResults(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
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

        when (item?.itemId) {

            R.id.clear_results -> {
                progressbar_main.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
                progressbar_scroll.visibility = View.GONE
                isSearchedButtonClicked = false
                mainPresenterInterface.getAllRepositories(true)
                et_search.text.clear()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
