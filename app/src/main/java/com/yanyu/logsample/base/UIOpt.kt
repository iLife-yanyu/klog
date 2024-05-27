package com.yanyu.logsample.base

import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding

interface UIOpt<VB : ViewBinding> {
    fun getViewBinding(layoutInflater: LayoutInflater): VB

    fun initData()

    fun initListener()

    fun initViews()

    fun initUserInterface() {
        initData()
        initListener()
        initViews()
    }
}