package com.ebs.boilerplate_demo.data.repositories.api.projects

import com.ebs.boilerplate_demo.data.models.Project
import io.reactivex.Single
import retrofit2.http.GET

interface ApiProjects {
    @GET("/search/repositories")
    fun getProjects(): Single<List<Project>>
}