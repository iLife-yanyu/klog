package com.yanyu.logsample

import android.app.Application
import com.yanyu.libs.klog.DeleteLogsTask
import com.yanyu.libs.klog.KLog
import com.yanyu.logsample.config.ConsoleConfigImpl
import com.yanyu.logsample.config.FileConfigImpl

class KLogApplication : Application() {

    override fun onCreate() {
        initLogConfigs()
        super.onCreate()
    }

    private fun initLogConfigs() {
        val logDirectory = KLog.mkdirsOfLog(this)
        val consoleConfig = ConsoleConfigImpl()
        val fileConfig = FileConfigImpl(logDirectory)
        val deleteTask = DeleteLogsTask(logDirectory)
        KLog.init(consoleConfig, fileConfig/* 如果日志不要写到文件里面，就不要传 FileConfig */, deleteTask)
    }
}