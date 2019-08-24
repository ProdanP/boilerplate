package com.ebs.boilerplate_demo.presentation.ui

import android.content.Context
import android.os.Bundle
import com.ebs.boilerplate_demo.R
import com.ebs.boilerplate_demo.domain.items.Item
import com.ebs.boilerplate_demo.presentation.base.BaseApplicationFragment

class FragmentProjects : BaseApplicationFragment(), PresenterProjects.View {
    companion object {
        fun newInstance(): FragmentProjects {

            val args = Bundle()

            val fragment = FragmentProjects()
            fragment.arguments = args
            return fragment
        }
    }

    override fun getLayoutResourceId(): Int = R.layout.fragment_projects

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onEnterAnimationEnd(savedInstanceState: Bundle?) {
        super.onEnterAnimationEnd(savedInstanceState)
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    override fun onItemsRetrived(items: List<Item>, realSize: Int) {

    }
}