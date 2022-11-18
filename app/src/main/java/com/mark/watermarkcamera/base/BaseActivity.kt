package com.mark.watermarkcamera.base

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.gyf.immersionbar.ktx.immersionBar
import com.mark.watermarkcamera.R
import java.lang.reflect.ParameterizedType


open class BaseActivity<T: ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: T
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //利用反射，调用指定ViewBinding中的inflate方法填充视图
        val superType = javaClass.genericSuperclass
        binding = if (superType is ParameterizedType){
            val clazz = superType.actualTypeArguments[0] as Class<*>
            val method = clazz.getMethod("inflate", LayoutInflater::class.java)
            method.invoke(null, layoutInflater) as T
        }else{
            customViewBinDing() ?: return
        }
        setContentView(binding.root)
        initSystemBarTint(false)
    }
    open fun customViewBinDing(): T? = null

    protected fun initSystemBarTint(isSystemBarTranslucent: Boolean){
        if (isSystemBarTranslucent){
            // 状态栏全透明
            return
        }
        immersionBar {
            statusBarColor(R.color.colorPrimary)
            navigationBarColor(R.color.colorPrimary)
        }
    }

}