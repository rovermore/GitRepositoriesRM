package com.example.rovermore.gitrepositoriesrm.datamodel

data class Repository(
    val id: Int, val name: String,
    val owner: Owner,
    val description: String?
    )

