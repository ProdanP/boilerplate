package com.ebs.boilerplate_demo.domain.mappers

import com.ebs.boilerplate_demo.data.models.Project
import com.ebs.boilerplate_demo.domain.items.Item
import com.ebs.boilerplate_demo.domain.items.ItemProject

class MapperProjects{

    fun mapProjectsToItems(projects : List<Project>) : ModelMap{
        val items = ArrayList<Item>()
        for (project in projects){
            items.add(ItemProject(project))
        }
        return ModelMap(items, projects.size)
    }
}