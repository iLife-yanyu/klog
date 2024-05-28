package com.yanyu.klog.config

import com.yanyu.klog.LogImpl

abstract class AbstractConfig constructor() {

    private val logLevels: Int by lazy(LazyThreadSafetyMode.NONE) { configAbleLogLevels() }

    /**
     * 返回可以输出到日志文件的日志级别
     * 默认所有的日志都可以输出到控制台
     * @return
     */
    protected open fun configAbleLogLevels(): Int {
        var lelvels = 0
        for (impl in LogImpl.values()) {
            lelvels = lelvels or impl.level
        }
        return lelvels
    }

    fun ableLogFile(level: Int): Boolean {
        return logLevels and level == level
    }
}