package com.yanyu.logsample.ui

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.yanyu.klog.KLog
import com.yanyu.logsample.R
import com.yanyu.logsample.base.BaseActivity
import com.yanyu.logsample.databinding.ActivityNormalBinding
import com.yanyu.logsample.util.MenuUtil

class NormalActivity : BaseActivity<ActivityNormalBinding>() {

    private lateinit var json: String
    private lateinit var jsonLong: String
    private lateinit var stringLong: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handler.sendEmptyMessageDelayed(0, 3000)
    }

    override fun initData() {
        jsonLong = getString(R.string.json_long)
        json = getString(R.string.json)
        stringLong = getString(R.string.string_long)
    }

    override fun initListener() {
        binding.log.setOnClickListener { log() }
        binding.logParam.setOnClickListener { logParam() }
        binding.logNull.setOnClickListener { logWithNull() }
        binding.logWithLong.setOnClickListener { logWithLong() }
        binding.logTagParam.setOnClickListener { logTagParam() }
        binding.logTagParams.setOnClickListener { logTagParams() }
        binding.logTraceStack.setOnClickListener { logTraceStack() }
    }

    override fun initViews() {
        binding.toolBar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(binding.toolBar)
    }

    private fun logTraceStack() {
        KLog.trace()
    }

    private fun logParam() {
//        KLog.debug()
//        KLog.debug("This is a debug message")
//        KLog.debug("DEBUG", "params1", "params2", this)
        KLog.v(LOG_MSG)
        KLog.d(LOG_MSG)
        KLog.i(LOG_MSG)
        KLog.w(LOG_MSG)
        KLog.e(LOG_MSG)
        KLog.a(LOG_MSG)
        KLog.debug(LOG_MSG)
    }

    private fun log() {
        KLog.v()
        KLog.d()
        KLog.i()
        KLog.w()
        KLog.e()
        KLog.a()
        KLog.debug()
    }

    private fun logWithNull() {
        KLog.v(null)
        KLog.d(null)
        KLog.i(null)
        KLog.w(null)
        KLog.e(null)
        KLog.a(null)
        KLog.debug(null)
    }

    private fun logTagParam() {
        KLog.v(TAG, LOG_MSG)
        KLog.d(TAG, LOG_MSG)
        KLog.i(TAG, LOG_MSG)
        KLog.w(TAG, LOG_MSG)
        KLog.e(TAG, LOG_MSG)
        KLog.a(TAG, LOG_MSG)
        KLog.debug(TAG, LOG_MSG)
    }

    private fun logWithLong() {
        KLog.v(TAG, stringLong)
        KLog.d(TAG, stringLong)
        KLog.i(TAG, stringLong)
        KLog.w(TAG, stringLong)
        KLog.e(TAG, stringLong)
        KLog.a(TAG, stringLong)
        KLog.debug(TAG, stringLong)
    }

    private fun logTagParams() {
        KLog.v(TAG, LOG_MSG, "params1", "params2", this)
        KLog.d(TAG, LOG_MSG, "params1", "params2", this)
        KLog.i(TAG, LOG_MSG, "params1", "params2", this)
        KLog.w(TAG, LOG_MSG, "params1", "params2", this)
        KLog.e(TAG, LOG_MSG, "params1", "params2", this)
        KLog.a(TAG, LOG_MSG, "params1", "params2", this)
        KLog.debug(TAG, LOG_MSG, "params1", "params2", this)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        MenuUtil.onOptionsItemSelected(this, item)
        return super.onOptionsItemSelected(item)
    }

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityNormalBinding {
        return ActivityNormalBinding.inflate(layoutInflater)
    }

    companion object {

        private const val LOG_MSG = "KLog is a so cool Log Tool!"
        private const val TAG = "KLog"
        private val handler: Handler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message) {
                KLog.d("Inner Class Test")
            }
        }
    }
}