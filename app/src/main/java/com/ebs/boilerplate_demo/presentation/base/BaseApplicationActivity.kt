package com.ebs.boilerplate_demo.presentation.base

import com.ebs.baseutility.mvp.BaseFragmentActivity
import okhttp3.ResponseBody

abstract class BaseApplicationActivity : BaseFragmentActivity(){
    override fun onShowLoading() {
    }

    override fun onHideLoading() {
    }

    override fun onShowHttpError(onShow: Boolean, code: Int, responseBody: ResponseBody?) {
    }

    override fun onShowServerError(onShow: Boolean, error: String?) {
    }

    override fun onShowConnectionError(onShow: Boolean) {
    }

    override fun onUnAuthorizedError(onShow: Boolean) {
    }

    override fun onNotFoundError(onShow: Boolean) {
    }

}