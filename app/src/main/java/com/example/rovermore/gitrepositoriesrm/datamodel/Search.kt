package com.example.rovermore.gitrepositoriesrm.datamodel

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class Search {

    @SerializedName("total_count")
    @Expose
    var totalCount: Int? = null
    @SerializedName("incomplete_results")
    @Expose
    var incompleteResults: Boolean? = null
    @SerializedName("items")
    @Expose
    var repository: MutableList<Repository>? = null
    private val serialVersionUID = -5753277895900112187L
}