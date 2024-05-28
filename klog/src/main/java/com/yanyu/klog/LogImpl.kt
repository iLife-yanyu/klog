package com.yanyu.klog

import android.util.Log
import com.yanyu.klog.config.LogLevel

internal enum class LogImpl(val level: Int) {

    V(LogLevel.V) {

        override fun logImpl(tag: String, msg: String) {
            Log.v(tag, msg)
        }
    },
    D(LogLevel.D) {

        override fun logImpl(tag: String, msg: String) {
            Log.d(tag, msg)
        }
    },
    I(LogLevel.I) {

        override fun logImpl(tag: String, msg: String) {
            Log.i(tag, msg)
        }
    },
    W(LogLevel.W) {

        override fun logImpl(tag: String, msg: String) {
            Log.w(tag, msg)
        }
    },
    E(LogLevel.E) {

        override fun logImpl(tag: String, msg: String) {
            Log.e(tag, msg)
        }
    },
    A(LogLevel.A) {

        override fun logImpl(tag: String, msg: String) {
            Log.wtf(tag, msg)
        }
    },
    J(LogLevel.J) {

        override fun logImpl(tag: String, msg: String) {
            Log.v(tag, msg)
        }

        override fun log(logInfo: LogInfo) {
            LogUtil.printLogJsonInfo(this, logInfo)
        }
    },
    F(LogLevel.F) {

        override fun logImpl(tag: String, msg: String) {
            Log.e(tag, msg)
        }
    },
    EXTEND(LogLevel.EXTEND) {

        override fun logImpl(tag: String, msg: String) {
            Log.i(tag, msg)
        }

        override fun log(logInfo: LogInfo) {
            LogUtil.printLogInfo(this, logInfo.tag, logInfo.withExtendInfo())
        }
    };

    open fun logImpl(tag: String, msg: String) {
        throw RuntimeException("not implement")
    }

    open fun log(logInfo: LogInfo) {
        LogUtil.printLogInfo(this, logInfo.tag, logInfo.withLogInfo())
    }
}