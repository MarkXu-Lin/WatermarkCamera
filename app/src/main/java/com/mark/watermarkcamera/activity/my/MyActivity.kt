package com.mark.watermarkcamera.activity.my

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.mark.watermarkcamera.R
import com.mark.watermarkcamera.bean.User
import com.mark.watermarkcamera.database.UserDataBase
import com.mark.watermarkcamera.databinding.ActivityMyBinding
import com.mark.watermarkcamera.domain.Account
import com.mark.watermarkcamera.tools.dp
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

@AndroidEntryPoint
class MyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMyBinding

    @Inject
    lateinit var user: User
    @Inject
    lateinit var okHttpClient: OkHttpClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my)
//        val user = User(1, "xlk")
        binding.dataUser = user
        user.username.set("哈哈哈")
        val request = Request.Builder()
            .url("https://www.wanandroid.com/article/list/0/json")
            .build()
        okHttpClient.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {

            }

            override fun onResponse(call: Call, response: Response) {
                user.username.set(response.body?.string())
            }

        })
//        binding.btnUsername.setOnClickListener {
//            user.username.set(binding.etUsername.text.toString())
//        }
        generateAccount()
    }

    private fun generateAccount() {
//        for (i in 0 until 10000){
//            val account = Account("num-${i}", "psw-${i}")
//            val count = UserDataBase.userDb.accountDao.insertAccount(account)
//            Log.d("xlktable", "插入数据：$account，插入状态：${count}")
//        }
//        val list = UserDataBase.userDb.accountDao.queryAllAccount()
//        Log.d("xlktable", list.toString())
//        for (account in list) {
//            val textView = TextView(this)
//            textView.text = "账号：${account.loginAccount}, 密码：${account.loginPsw}"
//            textView.textSize = 16f.dp()
//            binding.llMain.addView(textView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
//        }
    }
}