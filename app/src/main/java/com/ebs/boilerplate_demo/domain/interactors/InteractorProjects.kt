package com.ebs.boilerplate_demo.domain.interactors

import com.ebs.baseutility.mvp.InteractorListener
import com.ebs.baseutility.rest.ResponseSingleListener
import com.ebs.boilerplate_demo.data.repositories.api.projects.ProjectsRepository
import com.ebs.boilerplate_demo.domain.mappers.MapperProjects
import com.ebs.boilerplate_demo.domain.mappers.ModelMap
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class InteractorProjects constructor(var repository: ProjectsRepository, var itemMapper: MapperProjects) {
    private val compositeDisposable = CompositeDisposable()

    fun getProjects(callback: Callback) {
        val disposable = repository.getProjects()
            .map { t -> itemMapper.mapProjectsToItems(t) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : ResponseSingleListener<ModelMap>(callback){
                override fun onSuccess(t: ModelMap) {
                    super.onSuccess(t)
                    callback.onProjectsRetrived(t)
                }
            })
        compositeDisposable.add(disposable)
    }

    fun unsubscribe(){
        compositeDisposable.dispose()
    }

    public interface Callback : InteractorListener {
        fun onProjectsRetrived(modelMap: ModelMap)
    }



}