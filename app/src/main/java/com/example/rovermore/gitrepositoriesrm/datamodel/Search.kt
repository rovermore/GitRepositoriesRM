package com.example.rovermore.gitrepositoriesrm.datamodel

import com.google.gson.annotations.SerializedName



data class Search(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("items") val repository: MutableList<Repository>?
    )