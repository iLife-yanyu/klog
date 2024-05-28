package com.yanyu.klog

import android.util.Log
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.nio.charset.StandardCharsets

internal object LogUtil {

    @JvmStatic
    private val LINE_SEPARATOR: String = System.getProperty("line.separator") ?: "/"
    private const val MAX_LENGTH = 4000

    fun printLogInfo(level: LogImpl, tag: String, msg: String) {
        var index = 0
        val length = msg.length
        val countOfSub = length / MAX_LENGTH
        if (countOfSub > 0) {
            for (i in 0 until countOfSub) {
                val sub = msg.substring(index, index + MAX_LENGTH)
                level.logImpl(tag, sub)
                index += MAX_LENGTH
            }
            level.logImpl(tag, msg.substring(index, length))
        }
        else {
            level.logImpl(tag, msg)
        }
    }

    fun printLogJsonInfo(json: LogImpl, logInfo: LogInfo) {
        val tag = logInfo.tag
        val msg = logInfo.msg
        val headString = logInfo.headString
        var message: String = try {
            if (msg.startsWith("{")) {
                val jsonObject = JSONObject(msg)
                jsonObject.toString(KLog.config?.jsonIndent ?: 4)
            }
            else if (msg.startsWith("[")) {
                val jsonArray = JSONArray(msg)
                jsonArray.toString(KLog.config?.jsonIndent ?: 4)
            }
            else {
                msg
            }
        }
        catch (e: JSONException) {
            msg
        }
        printTopLine(json, tag)
        message = headString + LINE_SEPARATOR + message
        val lines: Array<String> = message.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        for (line in lines) {
            json.logImpl(tag, "║ $line")
        }
        printBottomLine(json, tag)
    }

    private fun printTopLine(json: LogImpl, tag: String) {
        json.logImpl(
            tag, "╔═══════════════════════════════════════════════════════════════════════════════════════"
        )
    }

    private fun printBottomLine(json: LogImpl, tag: String) {
        json.logImpl(
            tag, "╚═══════════════════════════════════════════════════════════════════════════════════════"
        )
    }

    fun write2file(tag: String?, targetDirectory: File, fileName: String, headString: String, msg: String) {
        if (write(targetDirectory, fileName, msg)) {
            Log.d("KLogFileImpl", "$tag $headString save log success! location is >>> " + targetDirectory.absolutePath + "/" + fileName)
        }
        else {
            Log.e("KLogFileImpl", "$tag $headString save log failed!")
        }
    }

    private fun write(dic: File, fileName: String, allMsg: String): Boolean {
        val file = File(dic, fileName)
        var outputStream: OutputStream? = null
        var outputStreamWriter: OutputStreamWriter? = null
        return try {
            outputStream = FileOutputStream(file, true)
            outputStreamWriter = OutputStreamWriter(outputStream, StandardCharsets.UTF_8)
            var tmpMsg = allMsg
            while (tmpMsg.length > 5000) {
                val sub = tmpMsg.substring(0, 5000)
                outputStreamWriter.write(sub)
                tmpMsg = tmpMsg.substring(5000)
            }
            outputStreamWriter.write(tmpMsg)
            true
        }
        catch (e: Exception) {
            e.printStackTrace()
            val logInfo = LogInfo.newInstance(2, "KLogFileImpl", "error on ${e.message ?: "empty"}")
            LogImpl.E.log(logInfo)
            false
        }
        finally {
            try {
                outputStream?.close()
                outputStreamWriter?.let {
                    it.flush()
                    it.close()
                }
            }
            catch (_: Throwable) {

            }
        }
    }
}