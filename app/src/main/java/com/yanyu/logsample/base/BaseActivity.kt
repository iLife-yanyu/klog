package com.yanyu.logsample.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.yanyu.libs.klog.KLog

abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity(), UIOpt<VB> {

    lateinit var binding: VB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = getViewBinding(layoutInflater)
        setContentView(binding.root)
        initUserInterface()
    }

    override fun onResume() {
        super.onResume()
        KLog.extendLog(javaClass)
    }

    final override fun initUserInterface() {
        super.initUserInterface()
    }

    fun <T : Activity> startActivity(clazz: Class<T>) {
        startActivity(Intent(this, clazz))
    }
}
