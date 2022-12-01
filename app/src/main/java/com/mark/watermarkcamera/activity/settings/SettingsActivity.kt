package com.mark.watermarkcamera.activity.settings

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.mark.watermarkcamera.R
import com.mark.watermarkcamera.base.BaseActivity
import com.mark.watermarkcamera.bean.Student
import com.mark.watermarkcamera.databinding.ActivitySettingsBinding
import com.mark.watermarkcamera.module.test.StudentViewModel

class SettingsActivity : BaseActivity<ActivitySettingsBinding>() {
    private val studentViewModel by viewModels<StudentViewModel>()

    override fun initView() {
        studentViewModel.newScore.observe(this){
            binding.tvShow.text = it.toString()
        }
        binding.btnOk.setOnClickListener {
            studentViewModel.setStudentId(binding.etId.text.toString())
        }
    }
}