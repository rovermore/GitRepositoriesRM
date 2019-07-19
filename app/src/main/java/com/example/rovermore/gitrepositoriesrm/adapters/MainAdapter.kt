package com.example.rovermore.gitrepositoriesrm.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rovermore.gitrepositoriesrm.R
import com.example.rovermore.gitrepositoriesrm.datamodel.Owner
import com.example.rovermore.gitrepositoriesrm.datamodel.Repository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_repository.view.*


class MainAdapter(
                  var context: Context,
                  var repositoriesList: MutableList<Repository>?,
                  var itemClicked: OnItemClicked
                  ) : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    interface OnItemClicked {
        fun itemClicked(repository: Repository)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_repository, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        if(repositoriesList.isNullOrEmpty()){
            return 0
        }
        return repositoriesList!!.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        if(!repositoriesList.isNullOrEmpty()) {

            holder.bindView(repositoriesList!![position])
        }
    }

    fun updateRepositoriesList(repositoriesList: MutableList<Repository>?){
        this.repositoriesList = repositoriesList
        notifyDataSetChanged()
    }

    fun clearMainAdapter() {
        if (repositoriesList != null) {
            repositoriesList!!.clear()
            repositoriesList = null
            notifyDataSetChanged()
        }
    }

    inner class MyViewHolder(var v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {

        lateinit var currentRepository: Repository

        init{

            v.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            itemClicked.itemClicked(currentRepository)

        }

        fun bindView(repository: Repository) {

            currentRepository = repository

            v.tv_repository_name.text = repository.name

            if (!repository.description.isNullOrBlank()) {

                if (repository.description.length < 60) {

                    v.tv_repository_description.text = repository.description

                } else {

                    v.tv_repository_description.text =
                        StringBuilder(repository.description.substring(0, 50)).append("...")
                            .toString()
                }
            }

            val currentOwner: Owner? = repository.owner

            v.tv_owner.text = currentOwner!!.login

            val photoUrl = currentOwner.avatarUrl
            if (photoUrl.isNotBlank()) {

                Picasso.with(context).load(photoUrl).into(v.iv_cicle_profile_image)

            } else {
                v.iv_cicle_profile_image.setImageResource(R.drawable.ic_account_circle_black_24dp)
            }

        }
    }

}