package com.yanyu.logsample.ui

import android.graphics.Color
import android.os.Build
import android.os.Environment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import com.yanyu.klog.KLog
import com.yanyu.logsample.R
import com.yanyu.logsample.base.BaseActivity
import com.yanyu.logsample.databinding.ActivityAdvanceBinding
import com.yanyu.logsample.util.MenuUtil

class AdvanceActivity : BaseActivity<ActivityAdvanceBinding>() {

    private lateinit var json: String
    private lateinit var jsonLong: String
    private lateinit var stringLong: String

    override fun initData() {
        jsonLong = getString(R.string.json_long)
        json = getString(R.string.json)
        stringLong = getString(R.string.string_long)
    }

    override fun initListener() {
        binding.logJson.setOnClickListener { logJson() }
        binding.logLongJson.setOnClickListener { logLongJson() }
        binding.logTagJson.setOnClickListener { logTagJson() }
        binding.logWithFile.setOnClickListener { logWithFile() }
        binding.logTraceStack.setOnClickListener { logTraceStack() }
    }

    override fun initViews() {
        binding.toolBar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(binding.toolBar)
    }

    private fun logTraceStack() {
        KLog.trace()
    }

    private fun logJson() {
        KLog.json("12345")
        KLog.json(json)
    }

    private fun logLongJson() {
        KLog.json(jsonLong)
    }

    private fun logTagJson() {
        KLog.json(TAG, json)
    }

    private fun logWithFile() {
        KLog.file(jsonLong)
        KLog.file(TAG, jsonLong)
        KLog.file(TAG, "test.txt", jsonLong)
    }

    private fun logPath() {
//        KLog.file(jsonLong)
//        KLog.file(TAG, jsonLong)
//        KLog.file(TAG, "test.txt", jsonLong)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            KLog.d("getStorageDirectory = ${Environment.getStorageDirectory().path}")
        }
        KLog.d("getDataDirectory = ${Environment.getDataDirectory().path}")
        KLog.d("getExternalStorageDirectory = ${Environment.getExternalStorageDirectory().path}")
        KLog.d("getRootDirectory = ${Environment.getRootDirectory().path}")
        KLog.d("DIRECTORY_MUSIC = ${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).path}")
        KLog.d("DIRECTORY_PODCASTS = ${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PODCASTS).path}")
        KLog.d("DIRECTORY_RINGTONES = ${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RINGTONES).path}")
        KLog.d("DIRECTORY_ALARMS = ${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_ALARMS).path}")
        KLog.d("DIRECTORY_NOTIFICATIONS = ${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS).path}")
        KLog.d("DIRECTORY_PICTURES = ${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).path}")
        KLog.d("DIRECTORY_MOVIES = ${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES).path}")
        KLog.d("DIRECTORY_DOWNLOADS = ${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).path}")
        KLog.d("DIRECTORY_DCIM = ${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).path}")
        KLog.d("DIRECTORY_DOCUMENTS = ${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS).path}")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            KLog.d("DIRECTORY_SCREENSHOTS = ${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_SCREENSHOTS).path}")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            KLog.d("DIRECTORY_AUDIOBOOKS = ${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_AUDIOBOOKS).path}")
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            KLog.d("DIRECTORY_RECORDINGS = ${Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_RECORDINGS).path}")
        }
        KLog.d("getDataDirectory = ${Environment.getDataDirectory()}")
        KLog.d("getExternalStorageDirectory = ${Environment.getExternalStorageDirectory()}")
        KLog.d("getDownloadCacheDirectory = ${Environment.getDownloadCacheDirectory()}")
    }

    ///////////////////////////////////////////////////////////////////////////
    // MENU
    ///////////////////////////////////////////////////////////////////////////
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_about, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        MenuUtil.onOptionsItemSelected(this, item)
        return super.onOptionsItemSelected(item)
    }

    override fun getViewBinding(layoutInflater: LayoutInflater): ActivityAdvanceBinding {
        return ActivityAdvanceBinding.inflate(layoutInflater)
    }

    companion object {

        private const val LOG_MSG = "KLog is a so cool Log Tool!"
        private const val TAG = "KLog"
    }
}