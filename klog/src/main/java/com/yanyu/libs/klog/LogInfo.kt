package com.yanyu.libs.klog

import android.text.TextUtils
import com.yanyu.libs.klog.config.ConsoleConfig

internal class LogInfo private constructor(var tag: String, val msg: String, val headString: String) {

    fun withLogInfo(): String {
        return headString + msg
    }

    fun withExtendInfo(): String {
        return msg + headString
    }

    companion object {

        private const val PARAM = "Param"
        private const val NULL = "null"

        fun newInstance(traceIndex: Int, tagStr: String?, vararg objects: Any): LogInfo {
            val stackTrace = Thread.currentThread().stackTrace
            val targetElement = stackTrace[traceIndex]
            val fileName = targetElement.fileName
            val methodName = targetElement.methodName
            var lineNumber = targetElement.lineNumber
            if (lineNumber < 0) {
                lineNumber = 0
            }
            val tag = KLog.config?.getTag(tagStr ?: fileName) ?: ""
            val msg = getObjectsString(*objects)
            val headString = "[ ($fileName:$lineNumber)#$methodName ] "
            return LogInfo(tag, msg, headString)
        }

        fun getJavaOrKt(clazz: Class<out Any>): String {
            val annotations = clazz.annotations
            for (annotation in annotations) {
                val toString = annotation.toString()
                if (toString.contains("kotlin")) {
                    return ".kt"
                }
            }
            return ".java"
        }

        private fun getObjectsString(vararg objects: Any): String {
            return if (objects.size > 1) {
                val stringBuilder = StringBuilder()
                stringBuilder.append("\n")
                for (i in objects.indices) {
                    val `object` = objects[i]
                    stringBuilder.append(PARAM).append("[").append(i).append("]").append(" = ").append(`object`.toString()).append("\n")
                }
                stringBuilder.toString()
            }
            else if (objects.size == 1) {
                val `object` = objects[0]
                `object`.toString()
            }
            else {
                NULL
            }
        }

        private fun ConsoleConfig?.getTag(tag: String): String {
            if (this == null) {
                return tag
            }
            val emptyCurrentTag = TextUtils.isEmpty(tag)
            if (disablePrefixTag) {
                return if (emptyCurrentTag) {
                    defaultTag
                }
                else {
                    tag
                }
            }
            else {
                return if (emptyCurrentTag) {
                    defaultTag
                }
                else {
                    "$prefixTag-$tag"
                }
            }
        }
    }
}