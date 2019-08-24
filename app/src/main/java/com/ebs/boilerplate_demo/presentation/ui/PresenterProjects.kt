package com.ebs.boilerplate_demo.presentation.ui

import com.ebs.baseutility.mvp.BasePresenterImpl
import com.ebs.baseutility.mvp.BaseView
import com.ebs.boilerplate_demo.data.repositories.api.projects.ProjectsRepository
import com.ebs.boilerplate_demo.domain.interactors.InteractorProjects
import com.ebs.boilerplate_demo.domain.items.Item
import com.ebs.boilerplate_demo.domain.mappers.MapperProjects
import com.ebs.boilerplate_demo.domain.mappers.ModelMap

class PresenterProjects<V : PresenterProjects.View> : BasePresenterImpl<V>(),
    InteractorProjects.Callback {
    val items: List<Item> = ArrayList()
    private val interactor: InteractorProjects = InteractorProjects(ProjectsRepository(), MapperProjects())

    override fun onProjectsRetrived(modelMap: ModelMap) {
        view.onItemsRetrived(modelMap.items, modelMap.realSize)
    }

    fun getProjects() {
        interactor.getProjects(this)
    }

    override fun onDestroyed() {
        super.onDestroyed()
        interactor.unsubscribe()
    }

    interface View : BaseView {
        fun onItemsRetrived(items: List<Item>, realSize: Int)
    }
}