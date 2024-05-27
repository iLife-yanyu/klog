package com.yanyu.klog.config

object LogLevel {

    @JvmStatic
    val V = 1 shl 0

    @JvmStatic
    val D = 1 shl 1

    @JvmStatic
    val I = 1 shl 2

    @JvmStatic
    val W = 1 shl 3

    @JvmStatic
    val E = 1 shl 4

    @JvmStatic
    val A = 1 shl 5

    @JvmStatic
    val J = 1 shl 6 // JSON

    @JvmStatic
    val F = 1 shl 7 // FILE
}