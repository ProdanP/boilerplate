package com.ebs.boilerplate_demo.data.repositories.api.projects

import com.ebs.boilerplate_demo.data.api_client.RetrofitClientSingleton
import com.ebs.boilerplate_demo.data.models.Project
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProjectsRepository {
    private val apiInterface = RetrofitClientSingleton.instance.client.create(ApiProjects::class.java)

    companion object {
        val instance = ProjectsRepository()
    }

    fun getProjects() : Single<List<Project>>{
        return apiInterface.getProjects().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }

}