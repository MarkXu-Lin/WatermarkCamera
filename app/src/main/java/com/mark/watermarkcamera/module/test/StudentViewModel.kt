package com.mark.watermarkcamera.module.test

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class StudentViewModel : ViewModel() {
    private var studentIdLiveData = MutableLiveData<String>()

    /**
     * 通过学生id获取对应的分数
     */
    val newScore: LiveData<Int> = Transformations.switchMap(
        studentIdLiveData
    ) { return@switchMap StudentRepository.getStudentScore(studentIdLiveData.value!!) }

    /**
     * 设置学生id
     * @param studentId 学生id
     */
    fun setStudentId(studentId: String) {
        studentIdLiveData.value = studentId
    }
}