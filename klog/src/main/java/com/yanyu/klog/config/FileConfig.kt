package com.yanyu.klog.config

import android.annotation.SuppressLint
import com.yanyu.klog.LogImpl
import com.yanyu.klog.LogInfo
import com.yanyu.klog.WriteFileRunnable
import java.text.SimpleDateFormat
import java.util.Date
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

abstract class FileConfig constructor(val logDirectory: String) {

    private val logLevels: Int by lazy(LazyThreadSafetyMode.NONE) { getAbleLogLevels() }
    private val executor: ExecutorService by lazy(LazyThreadSafetyMode.NONE) { newSingleThreadExecutor() }
    private val nameFormat: SimpleDateFormat by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { getFileNameFormat() }
    private val timeFormat: SimpleDateFormat by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { getLogTimeFormat() }

    init {
        if (logDirectory.isEmpty() || logDirectory.isBlank()) {
            throw RuntimeException("logDirectory is empty")
        }
        LogImpl.V.log(LogInfo.newInstance(2, "LogFileConfig", logDirectory))
    }

    @SuppressLint("SimpleDateFormat")
    protected open fun getFileNameFormat(): SimpleDateFormat {
        return SimpleDateFormat("yyyy-MM-dd")
    }

    @SuppressLint("SimpleDateFormat")
    protected open fun getLogTimeFormat(): SimpleDateFormat {
        return SimpleDateFormat("HH:mm:ss.SSS")
    }

    protected open fun newSingleThreadExecutor(): ExecutorService {
        return Executors.newSingleThreadExecutor()
    }

    /**
     * 返回可以输出到日志文件的日志级别
     * example { LogLevel.E or LogLevel.W }
     * @return
     */
    protected open fun getAbleLogLevels(): Int {
        return LogLevel.E or LogLevel.W
    }

    fun ableLogFile(level: Int): Boolean {
        return logLevels and level == level
    }

    internal fun log2file(level: LogImpl, logInfo: LogInfo, fileName: String? = null) {
        executor.execute(WriteFileRunnable(this, level, logInfo, fileName))
    }

    open fun encapsulationFileName(logLevel: String, logTime: Date): String {
        return "${nameFormat.format(logTime)}.txt"
    }

    open fun encapsulationLogMsg(logLevel: String, tag: String, headString: String, msg: String, logTime: Date): String {
        return "${timeFormat.format(logTime)} $logLevel $tag $msg"
    }
}