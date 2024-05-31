package com.yanyu.logsample

import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import com.yanyu.libs.klog.KLog
import com.yanyu.logsample.base.BaseActivity
import com.yanyu.logsample.databinding.ActivityMainBinding
import com.yanyu.logsample.ui.AdvanceActivity
import com.yanyu.logsample.ui.NormalActivity

class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun initData() {

    }

    override fun initListener() {
        binding.btnAdvance.setOnClickListener { startActivity(AdvanceActivity::class.java) }
        binding.btnNormal.setOnClickListener {
            KLog.e("LogImpl", "start to NormalActivity")
            startActivity(NormalActivity::class.java)
        }
    }

    override fun initViews() {
        Handler(Looper.getMainLooper()).postDelayed({
            binding.btnNormal.performClick()
        }, 1000)
    }
}