package com.yanyu.klog.config

import com.yanyu.klog.KLog

@Suppress("unused")
abstract class ConsoleConfig(internal val prefixTag: String? = null) {

    internal val disablePrefixTag: Boolean = prefixTag.isNullOrEmpty()

    val defaultTag: String by lazy { configDefaultTag() }
    val defaultMessage: String by lazy { configDefaultMessage() }
    val ableShowLog: Boolean by lazy { configAbleShowLog() }
    val jsonIndent: Int by lazy { configJsonIndent() }

    open fun configDefaultTag(): String {
        return KLog.javaClass.simpleName
    }

    open fun configDefaultMessage(): String {
        return "execute"
    }

    open fun configAbleShowLog(): Boolean {
        return true
    }

    open fun configJsonIndent(): Int {
        return 4
    }
}
