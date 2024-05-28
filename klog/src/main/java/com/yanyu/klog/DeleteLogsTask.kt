package com.yanyu.klog

import java.io.File

/**
 * 删除指定路径下的 days 天前的日志文件，默认3天前
 */
class DeleteLogsTask(private val logPath: String, days: Int = 3) : Runnable {

    private val threshold: Long
    private val sysTime = System.currentTimeMillis()

    init {
        if (days < 1) {
            throw RuntimeException("threshold must be greater than 1 day")
        }
        threshold = days * 1000L * 60 * 60 * 24
    }

    /**
     * @param file 一定是文件类型
     */
    private fun checkDeleteFile(file: File) {
        try {
            if (file.lastModified() < sysTime - threshold) {
                file.deleteOnExit()
            }
        }
        catch (ignored: Throwable) {
        }
    }

    private fun loopDelete(file: File) {
        val files = file.listFiles()
        if (files != null) {
            for (f in files) {
                checkFileType(f)
            }
        }
    }

    private fun checkFileType(f: File) {
        if (f.isDirectory()) {
            loopDelete(f)
        }
        else {
            checkDeleteFile(f)
        }
    }

    override fun run() {
        try {
            val file = File(logPath)
            if (file.exists()) {
                checkFileType(file)
            }
        }
        catch (ignored: Throwable) {
        }
    }

    fun start() {
        Thread(this).start()
    }
}
