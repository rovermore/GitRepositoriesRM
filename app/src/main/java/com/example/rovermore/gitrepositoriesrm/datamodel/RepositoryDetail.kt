package com.example.rovermore.gitrepositoriesrm.datamodel

import com.google.gson.annotations.SerializedName

data class RepositoryDetail(val id: Int, val name: String,
                            val owner: Owner,
                            val description: String?,
                            @SerializedName("stargazers_count") val  stargazersCount: Int,
                            @SerializedName("watchers_count") val watchersCount: Int,
                            val language: String,
                            @SerializedName("forks_count") val forksCount: Int,
                            @SerializedName("open_issues_count") val openIssuesCount: Int
                            )