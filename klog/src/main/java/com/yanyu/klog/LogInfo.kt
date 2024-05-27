package com.yanyu.klog

internal class LogInfo private constructor(val tag: String, val msg: String, val headString: String) {

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

        fun getFileSuffix(javaClass: Class<StackTraceElement>): String {
            return if (isKotlinClass(javaClass)) {
                ".kt"
            }
            else {
                ".java"
            }
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
    }
}