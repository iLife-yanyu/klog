package com.yanyu.klog.config

import com.yanyu.klog.KLog

@Suppress("unused")
abstract class ConsoleConfig(internal val prefixTag: String? = null) {

    internal val disablePrefixTag: Boolean = prefixTag.isNullOrEmpty()

    open val defaultTag: String = KLog.javaClass.simpleName
    open val defaultMessage: String = "execute"
    open val ableShowLog: Boolean = true
    open val jsonIndent = 4
}
