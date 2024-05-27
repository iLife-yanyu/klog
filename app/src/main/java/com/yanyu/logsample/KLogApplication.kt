package com.yanyu.logsample

import android.app.Application
import com.yanyu.klog.KLog
import com.yanyu.klog.getAppPath
import com.yanyu.logsample.config.ConsoleConfigImpl
import com.yanyu.logsample.config.FileConfigImpl

class KLogApplication : Application() {

    override fun onCreate() {
        KLog.init(ConsoleConfigImpl(), FileConfigImpl(getAppPath()))
        super.onCreate()
    }
}