package com.yanyu.klog

import com.yanyu.klog.config.ConsoleConfig
import com.yanyu.klog.config.FileConfig
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter

@Suppress("unused")
object KLog {

    internal var config: ConsoleConfig? = null
    private var fileConfig: FileConfig? = null
    private const val STACK_TRACE_INDEX_5 = 5
    private const val STACK_TRACE_INDEX_4 = 4

    fun init(consoleConfigImpl: ConsoleConfig, fileConfigImpl: FileConfig,
        // 删除阈值，传入负数则不删除，默认删除三天前的日志文件
             deleteThreshold: Int = 1000 * 60 * 60 * 24 * 3) {
        this.config = consoleConfigImpl
        this.fileConfig = fileConfigImpl
        if (deleteThreshold > 0) {
            DeleteFileRunnable.start(fileConfigImpl.logDirectory, deleteThreshold)
        }
    }

    fun v() {
        printLog(LogImpl.V, null, config.iGetDefaultMessage())
    }

    fun v(msg: Any?) {
        printLog(LogImpl.V, null, msg!!)
    }

    fun v(tag: String?, vararg objects: Any) {
        if (!tag.isNullOrEmpty() && objects.isEmpty()) {
            printLog(LogImpl.V, null, tag)
        }
        else {
            printLog(LogImpl.V, tag, *objects)
        }
    }

    fun d() {
        printLog(LogImpl.D, null, config.iGetDefaultMessage())
    }

    fun d(msg: Any?) {
        printLog(LogImpl.D, null, msg!!)
    }

    fun d(tag: String?, vararg objects: Any) {
        if (!tag.isNullOrEmpty() && objects.isEmpty()) {
            printLog(LogImpl.D, null, tag)
        }
        else {
            printLog(LogImpl.D, tag, *objects)
        }
    }

    fun i() {
        printLog(LogImpl.I, null, config.iGetDefaultMessage())
    }

    fun i(msg: Any?) {
        printLog(LogImpl.I, null, msg!!)
    }

    fun i(tag: String?, vararg objects: Any) {
        if (!tag.isNullOrEmpty() && objects.isEmpty()) {
            printLog(LogImpl.I, null, tag)
        }
        else {
            printLog(LogImpl.I, tag, *objects)
        }
    }

    fun w() {
        printLog(LogImpl.W, null, config.iGetDefaultMessage())
    }

    fun w(msg: Any?) {
        printLog(LogImpl.W, null, msg!!)
    }

    fun w(tag: String?, vararg objects: Any) {
        if (!tag.isNullOrEmpty() && objects.isEmpty()) {
            printLog(LogImpl.W, null, tag)
        }
        else {
            printLog(LogImpl.W, tag, *objects)
        }
    }

    fun e() {
        printLog(LogImpl.E, null, config.iGetDefaultMessage())
    }

    fun e(msg: Any?) {
        printLog(LogImpl.E, null, msg!!)
    }

    fun e(tag: String?, vararg objects: Any) {
        if (!tag.isNullOrEmpty() && objects.isEmpty()) {
            printLog(LogImpl.E, null, tag)
        }
        else {
            printLog(LogImpl.E, tag, *objects)
        }
    }

    fun a() {
        printLog(LogImpl.A, null, config.iGetDefaultMessage())
    }

    fun a(msg: Any?) {
        printLog(LogImpl.A, null, msg!!)
    }

    fun a(tag: String?, vararg objects: Any) {
        if (!tag.isNullOrEmpty() && objects.isEmpty()) {
            printLog(LogImpl.A, null, tag)
        }
        else {
            printLog(LogImpl.A, tag, *objects)
        }
    }

    fun json(jsonFormat: String) {
        printLog(LogImpl.J, null, jsonFormat)
    }

    fun json(tag: String, jsonFormat: String) {
        printLog(LogImpl.J, tag, jsonFormat)
    }

    fun file(msg: Any) {
        printFile(null, null, msg)
    }

    fun file(tag: String, msg: Any) {
        printFile(tag, null, msg)
    }

    fun file(tag: String, fileName: String, msg: Any) {
        printFile(tag, fileName, msg)
    }

    fun debug() {
        printDebug(null, config.iGetDefaultMessage())
    }

    fun debug(msg: Any?) {
        printDebug(null, msg!!)
    }

    fun debug(tag: String?, vararg objects: Any) {
        if (!tag.isNullOrEmpty() && objects.isEmpty()) {
            printDebug(null, tag)
        }
        else {
            printDebug(tag, *objects)
        }
    }

    fun extendLog(clazz: Class<*>) {
        if (config?.ableShowLog != true) {
            return
        }
        val msg = "[ (" + clazz.getSimpleName() + LogInfo.getFileSuffix(clazz) + ":1) ] extends "
        printLog(LogImpl.I, null, msg)
    }

    fun trace() {
        printStackTrace()
    }

    fun getLogFile(): File {
        return File(fileConfig?.logDirectory ?: "")
    }

    private fun printStackTrace() {
        val tr = Throwable()
        val sw = StringWriter()
        val pw = PrintWriter(sw)
        tr.printStackTrace(pw)
        pw.flush()
        val message = sw.toString()
        val traceString = message.split("\\n\\t".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        val sb = StringBuilder()
        sb.append("\n")
        for (trace in traceString) {
            if (trace.contains("com.socks.library.KLog")) {
                continue
            }
            sb.append(trace).append("\n")
        }
        val logInfo = LogInfo.newInstance(STACK_TRACE_INDEX_4, null, sb.toString())
        LogImpl.D.log(logInfo)
    }

    private fun printLog(level: LogImpl, tagStr: String?, vararg objects: Any) {
        if (config?.ableShowLog == true) {
            val logInfo = LogInfo.newInstance(STACK_TRACE_INDEX_5, tagStr, *objects)
            level.log(logInfo)
            val fileConfig = fileConfig ?: return
            if (fileConfig.ableLogFile(level.level)) {
                fileConfig.log2file(level, logInfo)
            }
        }
        else {
            val fileConfig = fileConfig ?: return
            if (fileConfig.ableLogFile(level.level)) {
                val logInfo = LogInfo.newInstance(STACK_TRACE_INDEX_5, tagStr, *objects)
                fileConfig.log2file(level, logInfo)
            }
        }
    }

    private fun printDebug(tagStr: String?, vararg objects: Any) {
        val logInfo = LogInfo.newInstance(STACK_TRACE_INDEX_5, tagStr, *objects)
        LogImpl.D.log(logInfo)
    }

    private fun printFile(tagStr: String?, fileName: String?, objectMsg: Any) {
        val fileConfig = fileConfig ?: return
        val logInfo = LogInfo.newInstance(STACK_TRACE_INDEX_5, tagStr, objectMsg)
        fileConfig.log2file(LogImpl.F, logInfo, fileName ?: "KLog.txt")
    }
}

private fun ConsoleConfig?.iGetDefaultMessage(): String {
    return this?.defaultMessage ?: ""
}
