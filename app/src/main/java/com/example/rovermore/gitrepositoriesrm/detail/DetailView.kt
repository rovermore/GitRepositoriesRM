package com.example.rovermore.gitrepositoriesrm.detail

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.rovermore.gitrepositoriesrm.datamodel.Owner
import com.example.rovermore.gitrepositoriesrm.datamodel.RepositoryDetail
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail_view.*


class DetailView : AppCompatActivity(), DetailViewInterface {

    private val LOGIN = "login"
    private val REPOSITORY = "repository"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.rovermore.gitrepositoriesrm.R.layout.activity_detail_view)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        setTitle("Perfil")

        val login = intent?.extras!!.get(LOGIN) as String
        val repositoryName = intent?.extras!!.get(REPOSITORY) as String

        val detailPresenterInterface: DetailPresenterInterface = DetailPresenter(login, repositoryName, this)

        detailPresenterInterface.getRepositoryDetail()
    }

    override fun onReceiveRepositoryDetail(repositoryDetail: RepositoryDetail) {
        val owner: Owner = repositoryDetail.owner!!
        Picasso.with(this).load(owner.avatarUrl).into(iv_profile_detail)
        tv_repository_name_detail.text = repositoryDetail.name
        tv_login_detail.text = owner.login
        tv_url_user_detail.text = owner.htmlUrl
    }

    override fun onErrorReceivingRepositoryDetail(error: String) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}
