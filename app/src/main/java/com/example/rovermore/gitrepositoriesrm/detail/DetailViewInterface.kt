package com.example.rovermore.gitrepositoriesrm.detail

import com.example.rovermore.gitrepositoriesrm.datamodel.RepositoryDetail

interface DetailViewInterface {

    fun onReceiveRepositoryDetail(repositoryDetail: RepositoryDetail)

    fun onErrorReceivingRepositoryDetail(error: String)
}