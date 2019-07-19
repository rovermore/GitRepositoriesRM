package com.example.rovermore.gitrepositoriesrm.datamodel

import com.google.gson.annotations.SerializedName

data class Owner (
    val login: String,
    val id: Int,
    @SerializedName("avatar_url") var avatarUrl: String,
    @SerializedName("html_url") val htmlUrl: String
    )
