package com.yanyu.libs.klog

import com.yanyu.libs.klog.config.FileConfig
import java.io.File
import java.util.Date

internal class WriteFileRunnable(
    // 配置文件
    private val fileConfig: FileConfig,
    // 日志级别
    private val level: LogImpl,
    // 日志信息
    private val logInfo: LogInfo,
    // 文件名
    private val fileName: String? = null) : Runnable {

    private val logTime: Date = Date(System.currentTimeMillis())

    override fun run() {
        val file = File(fileConfig.logDirectory)
        if (file.exists()) {
            write2file(file)
        }
        else if (file.mkdirs()) {
            write2file(file)
        }
        else {
            val info = LogInfo.newInstance(2, "LogFileConfig", "log2fileImpl mkdirs failed")
            LogUtil.printLogInfo(LogImpl.E, info.tag, info.withLogInfo())
        }
    }

    private fun checkFileName(): String {
        return if (fileName.isNullOrEmpty()) {
            fileConfig.encapsulationFileName(logTime)
        }
        else {
            fileName
        }
    }

    private fun write2file(file: File) {
        val fileName: String = checkFileName()
        val msg = fileConfig.encapsulationLogMsg(level.name, logInfo.tag, logInfo.headString, logInfo.msg, logTime)
        LogUtil.write2file(logInfo.tag, file, fileName, logInfo.headString, "$msg\n")
    }
}