package com.yanyu.logsample.util

import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import androidx.viewbinding.ViewBinding
import com.yanyu.logsample.R
import com.yanyu.logsample.base.BaseActivity

object MenuUtil {

    fun <VB : ViewBinding> onOptionsItemSelected(activity: BaseActivity<VB>, item: MenuItem) {
        when (item.itemId) {
            R.id.action_github -> activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse("https://github.com/ZhaoKaiQiang/KLog")
                )
            )
            R.id.action_blog -> activity.startActivity(
                Intent(
                    Intent.ACTION_VIEW, Uri.parse("http://blog.csdn.net/zhaokaiqiang1992")
                )
            )
        }
    }
}