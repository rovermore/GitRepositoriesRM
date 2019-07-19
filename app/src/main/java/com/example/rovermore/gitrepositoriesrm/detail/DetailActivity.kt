package com.example.rovermore.gitrepositoriesrm.detail

import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rovermore.gitrepositoriesrm.datamodel.Owner
import com.example.rovermore.gitrepositoriesrm.datamodel.RepositoryDetail
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_view.*


class DetailActivity : AppCompatActivity(), DetailViewInterface {

    private val LOGIN = "login"
    private val REPOSITORY = "repository"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.rovermore.gitrepositoriesrm.R.layout.activity_detail_view)
        //TODO MIRAR PARA QUE VUELVA A LA ACTIVITY ANTERIOS SIN RECARGAR
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        title = "Repository"

        val login = intent?.extras!!.get(LOGIN) as String
        val repositoryName = intent?.extras!!.get(REPOSITORY) as String

        val detailPresenterInterface: DetailPresenterInterface = DetailPresenter(login, repositoryName, this)

        detailPresenterInterface.getRepositoryDetail()
    }

    override fun onReceiveRepositoryDetail(repositoryDetail: RepositoryDetail) {
        val owner: Owner = repositoryDetail.owner
        Picasso.with(this).load(owner.avatarUrl).into(iv_profile_detail)
        tv_repository_name_detail.text = repositoryDetail.name
        tv_login_detail.text = owner.login
        tv_url_user_detail.text = owner.htmlUrl
        tv_description.text = repositoryDetail.description
        tv_language.text = repositoryDetail.language
        tv_star.text = repositoryDetail.stargazersCount.toString()
        tv_watch.text = repositoryDetail.watchersCount.toString()
        tv_forks.text = repositoryDetail.forksCount.toString()
        tv_issue.text = repositoryDetail.openIssuesCount.toString()

        tv_url_user_detail.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse(owner.htmlUrl)
            startActivity(intent)
        }
    }

    override fun onErrorReceivingRepositoryDetail(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}
