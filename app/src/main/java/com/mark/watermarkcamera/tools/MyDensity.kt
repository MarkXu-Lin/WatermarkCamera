package com.mark.watermarkcamera.tools

import android.annotation.SuppressLint
import com.mark.watermarkcamera.MainApplication
import java.text.SimpleDateFormat
import java.util.*

/**
 * dp 转 px
 */
fun dp2px(dp: Float): Float = dp * MainApplication.getApp().resources.displayMetrics.density


fun Float.dp() = dp2px(this)


fun Int.dp() = dp2px(this.toFloat()).toInt()


/**
 * sp 转 px
 */
fun sp2px(sp: Float): Float = sp * MainApplication.getApp().resources.displayMetrics.scaledDensity


fun Float.sp() = sp2px(this)


fun Int.sp() = sp2px(this.toFloat()).toInt()


/**
 * 获取屏幕宽度
 */
fun getScreenWidth() = MainApplication.getApp().resources.displayMetrics.widthPixels


/**
 * 根据时间戳获取时间
 */
@SuppressLint("SimpleDateFormat")
fun getDateHHmm(data: Long) = SimpleDateFormat("HH:mm").format(Date(data))