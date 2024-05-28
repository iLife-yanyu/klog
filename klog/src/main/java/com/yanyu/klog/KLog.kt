package com.yanyu.klog

import com.yanyu.klog.config.ConsoleConfig
import com.yanyu.klog.config.FileConfig
import java.io.File
import java.io.PrintWriter
import java.io.StringWriter

@Suppress("unused")
object KLog {

    @JvmStatic
    internal var config: ConsoleConfig? = null
    @JvmStatic
    private var fileConfig: FileConfig? = null
    private const val STACK_TRACE_INDEX_5 = 5
    private const val STACK_TRACE_INDEX_4 = 4

    /**
     * @param console   日志控制台的配置，不能为空
     * @param file      日志文件的配置，可以为空
     * @param deleteTask  删除指定时间节点前的文件
     */
    @JvmStatic
    fun init(console: ConsoleConfig, file: FileConfig? = null, deleteTask: DeleteLogsTask? = null) {
        this.config = console
        this.fileConfig = file
        deleteTask?.start()
    }

    @JvmStatic
    fun v() {
        printLog(LogImpl.V, null, config.iGetDefaultMessage())
    }

    @JvmStatic
    fun v(msg: Any?) {
        printLog(LogImpl.V, null, msg!!)
    }

    @JvmStatic
    fun v(tag: String?, vararg objects: Any) {
        if (!tag.isNullOrEmpty() && objects.isEmpty()) {
            printLog(LogImpl.V, null, tag)
        }
        else {
            printLog(LogImpl.V, tag, *objects)
        }
    }

    @JvmStatic
    fun d() {
        printLog(LogImpl.D, null, config.iGetDefaultMessage())
    }

    @JvmStatic
    fun d(msg: Any?) {
        printLog(LogImpl.D, null, msg!!)
    }

    @JvmStatic
    fun d(tag: String?, vararg objects: Any) {
        if (!tag.isNullOrEmpty() && objects.isEmpty()) {
            printLog(LogImpl.D, null, tag)
        }
        else {
            printLog(LogImpl.D, tag, *objects)
        }
    }

    @JvmStatic
    fun i() {
        printLog(LogImpl.I, null, config.iGetDefaultMessage())
    }

    @JvmStatic
    fun i(msg: Any?) {
        printLog(LogImpl.I, null, msg!!)
    }

    @JvmStatic
    fun i(tag: String?, vararg objects: Any) {
        if (!tag.isNullOrEmpty() && objects.isEmpty()) {
            printLog(LogImpl.I, null, tag)
        }
        else {
            printLog(LogImpl.I, tag, *objects)
        }
    }

    @JvmStatic
    fun w() {
        printLog(LogImpl.W, null, config.iGetDefaultMessage())
    }

    @JvmStatic
    fun w(msg: Any?) {
        printLog(LogImpl.W, null, msg!!)
    }

    @JvmStatic
    fun w(tag: String?, vararg objects: Any) {
        if (!tag.isNullOrEmpty() && objects.isEmpty()) {
            printLog(LogImpl.W, null, tag)
        }
        else {
            printLog(LogImpl.W, tag, *objects)
        }
    }

    @JvmStatic
    fun e() {
        printLog(LogImpl.E, null, config.iGetDefaultMessage())
    }

    @JvmStatic
    fun e(msg: Any?) {
        printLog(LogImpl.E, null, msg!!)
    }

    @JvmStatic
    fun e(tag: String?, vararg objects: Any) {
        if (!tag.isNullOrEmpty() && objects.isEmpty()) {
            printLog(LogImpl.E, null, tag)
        }
        else {
            printLog(LogImpl.E, tag, *objects)
        }
    }

    @JvmStatic
    fun a() {
        printLog(LogImpl.A, null, config.iGetDefaultMessage())
    }

    @JvmStatic
    fun a(msg: Any?) {
        printLog(LogImpl.A, null, msg!!)
    }

    @JvmStatic
    fun a(tag: String?, vararg objects: Any) {
        if (!tag.isNullOrEmpty() && objects.isEmpty()) {
            printLog(LogImpl.A, null, tag)
        }
        else {
            printLog(LogImpl.A, tag, *objects)
        }
    }

    @JvmStatic
    fun json(jsonFormat: String) {
        printLog(LogImpl.J, null, jsonFormat)
    }

    @JvmStatic
    fun json(tag: String, jsonFormat: String) {
        printLog(LogImpl.J, tag, jsonFormat)
    }

    @JvmStatic
    fun file(msg: Any) {
        printFile(null, null, msg)
    }

    @JvmStatic
    fun file(tag: String, msg: Any) {
        printFile(tag, null, msg)
    }

    @JvmStatic
    fun file(tag: String, fileName: String, msg: Any) {
        printFile(tag, fileName, msg)
    }

    @JvmStatic
    fun debug() {
        printDebug(null, config.iGetDefaultMessage())
    }

    @JvmStatic
    fun debug(msg: Any?) {
        printDebug(null, msg!!)
    }

    @JvmStatic
    fun debug(tag: String?, vararg objects: Any) {
        if (!tag.isNullOrEmpty() && objects.isEmpty()) {
            printDebug(null, tag)
        }
        else {
            printDebug(tag, *objects)
        }
    }

    @JvmStatic
    fun extendLog(clazz: Class<*>) {
        if (config?.ableShowLog != true) {
            return
        }
        val msg = "[ (" + clazz.getSimpleName() + LogInfo.getFileSuffix(clazz) + ":1) ] extends "
        printLog(LogImpl.I, null, msg)
    }

    @JvmStatic
    fun trace() {
        printStackTrace()
    }

    @JvmStatic
    fun getLogDirectoryFile(): File {
        return File(fileConfig?.logDirectory ?: "")
    }

    @JvmStatic
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

    @JvmStatic
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

    @JvmStatic
    private fun printDebug(tagStr: String?, vararg objects: Any) {
        val logInfo = LogInfo.newInstance(STACK_TRACE_INDEX_5, tagStr, *objects)
        LogImpl.D.log(logInfo)
    }

    @JvmStatic
    private fun printFile(tagStr: String?, fileName: String?, objectMsg: Any) {
        val fileConfig = fileConfig ?: return
        val logInfo = LogInfo.newInstance(STACK_TRACE_INDEX_5, tagStr, objectMsg)
        fileConfig.log2file(LogImpl.F, logInfo, fileName ?: "KLog.txt")
    }
}

private fun ConsoleConfig?.iGetDefaultMessage(): String {
    return this?.defaultMessage ?: ""
}
