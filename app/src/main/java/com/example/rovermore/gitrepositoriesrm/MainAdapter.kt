package com.example.rovermore.gitrepositoriesrm

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.rovermore.gitrepositoriesrm.datamodel.Owner
import com.example.rovermore.gitrepositoriesrm.datamodel.Repository
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item_repository.view.*


class MainAdapter(var context: Context, var repositoriesList: MutableList<Repository>?, var itemClicked: OnItemClicked) : RecyclerView.Adapter<MainAdapter.MyViewHolder>() {

    interface OnItemClicked {
        fun itemClicked(repository: Repository)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.MyViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.list_item_repository, parent, false)

        return MyViewHolder(view, itemClicked, repositoriesList)
    }

    override fun getItemCount(): Int {
        if(repositoriesList.isNullOrEmpty()){
            return 0
        }
        return repositoriesList!!.size
    }

    override fun onBindViewHolder(holder: MainAdapter.MyViewHolder, position: Int) {

        if(!repositoriesList.isNullOrEmpty()) {

            holder.view.tv_repository_name.text = repositoriesList!![position].name

            if (!repositoriesList!![position].description.isNullOrBlank()) {
                if (repositoriesList!![position].description!!.length < 60) {
                    holder.view.tv_repository_description.text = repositoriesList!![position].description
                } else {
                    holder.view.tv_repository_description.text =
                        StringBuilder(repositoriesList!![position].description!!.substring(0, 60)).append("...")
                            .toString()
                }
            }
            val currentOwner: Owner? = repositoriesList!![position].owner
            holder.view.tv_owner.text = currentOwner!!.login
            val photoUrl = currentOwner.avatarUrl
            if (photoUrl!!.isNotBlank()) {
                Picasso.with(context).load(photoUrl).into(holder.view.iv_cicle_profile_image)
            } else {
                holder.view.iv_cicle_profile_image.setImageResource(R.drawable.ic_account_circle_black_24dp)
            }
        }
    }

    fun updateRepositoriesList(repositoriesList: MutableList<Repository>?){
        this.repositoriesList = repositoriesList
        notifyDataSetChanged()
    }

    class MyViewHolder(v: View, itemClicked: OnItemClicked, repositoryList: MutableList<Repository>?) : RecyclerView.ViewHolder(v), View.OnClickListener {

        var view: View = v
        var mItemClicked = itemClicked
        var mRepositoryList = repositoryList

        init{
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition
            val clickedRepository = mRepositoryList?.get(position)
            mItemClicked.itemClicked(clickedRepository!!)

        }
    }

}