package com.yanyu.logsample.config

import com.yanyu.klog.config.FileConfig
import com.yanyu.klog.config.LogLevel

class FileConfigImpl(logDirectory: String) : FileConfig("$logDirectory/klogs") {

    override fun getAbleLogLevels(): Int {
        return LogLevel.E or LogLevel.W
    }
}