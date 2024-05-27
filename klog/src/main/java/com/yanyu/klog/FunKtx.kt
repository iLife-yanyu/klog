package com.yanyu.klog

import android.content.Context
import android.text.TextUtils
import com.yanyu.klog.config.ConsoleConfig
import java.io.File

/**
 * 判断一个类是Java还是Kotlin
 */
internal fun isKotlinClass(obj: Any): Boolean {
    val annotations = obj.javaClass.annotations ?: return false
    for (i in annotations.indices) {
        if (annotations[i].toString().contains("kotlin")) {
            return true
        }
    }
    return false
}

fun ConsoleConfig?.getTag(tag: String): String {
    if (this == null) {
        return tag
    }
    return if (disablePrefixTag && TextUtils.isEmpty(tag)) {
        defaultTag
    }
    else if (!disablePrefixTag) {
        prefixTag!!
    }
    else {
        tag
    }
}

private fun mkdirs(path: String): String {
    try {
        val file = File(path)
        if (!file.exists()) {
            val mkdirs = file.mkdirs()
            LogImpl.D.logImpl("LogPathKtx", "$path create ok is $mkdirs")
        }
    }
    catch (e: Throwable) {
        e.printStackTrace()
        LogImpl.E.logImpl("LogPathKtx", "$path create ok exception $e")
    }
    return path
}

fun Context.getAppPath(): String {
    return mkdirs("${filesDir.path}/$packageName/file")
}
