package com.yanyu.logsample

import android.app.Application
import com.yanyu.klog.DeleteLogsTask
import com.yanyu.klog.KLog
import com.yanyu.klog.getAppPath
import com.yanyu.logsample.config.ConsoleConfigImpl
import com.yanyu.logsample.config.FileConfigImpl

class KLogApplication : Application() {

    override fun onCreate() {
        val logDirectory = getAppPath()
        KLog.init(ConsoleConfigImpl(), FileConfigImpl(logDirectory), DeleteLogsTask(logDirectory))
        super.onCreate()
    }
}