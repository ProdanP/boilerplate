package com.ebs.boilerplate_demo.domain.items

import com.ebs.boilerplate_demo.data.models.Project

open class Item

data class ItemProject constructor(var project: Project) : Item()