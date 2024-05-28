package com.yanyu.klog

import java.io.File

internal class DeleteFileRunnable constructor(
    // 路径
    private val logPath: String,
    // 阈值
    private val threshold: Int) : Runnable {

    private val sysTime = System.currentTimeMillis()
    /**
     * 删除3天前的日志
     *
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
            if (!file.exists()) {
                return
            }
            checkFileType(file)
        }
        catch (ignored: Throwable) {
        }
    }

    companion object {

        internal fun start(logDirectory: String, deleteThreshold: Int) {
            Thread(DeleteFileRunnable(logDirectory, deleteThreshold)).start()
        }
    }
}
