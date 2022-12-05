package com.mark.watermarkcamera.domain

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
class Account() {
    @PrimaryKey(autoGenerate = true)
    var accountId: Int = 0
    var loginAccount: String = ""
    var loginPsw: String = ""

    @Ignore
    constructor(loginAccount: String, loginPsw: String): this(){
        this.loginAccount = loginAccount
        this.loginPsw = loginPsw
    }
}