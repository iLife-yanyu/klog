package com.yanyu.libs.klog.config

import com.yanyu.libs.klog.KLog

@Suppress("unused")
abstract class ConsoleConfig(internal val prefixTag: String? = null) : AbstractConfig() {

    internal val disablePrefixTag: Boolean = prefixTag.isNullOrEmpty()

    val defaultTag: String by lazy(LazyThreadSafetyMode.NONE) { configDefaultTag() }
    val defaultMessage: String by lazy(LazyThreadSafetyMode.NONE) { configDefaultMessage() }
    val jsonIndent: Int by lazy(LazyThreadSafetyMode.NONE) { configJsonIndent() }

    open fun configDefaultTag(): String {
        return KLog.javaClass.simpleName
    }

    open fun configDefaultMessage(): String {
        return "execute"
    }

    open fun configJsonIndent(): Int {
        return 4
    }

    open fun getExtendLogOfTag(): String {
        return "TagExtendLog"
    }
}
