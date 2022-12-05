package com.mark.watermarkcamera.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.mark.watermarkcamera.domain.Account

@Dao
interface AccountDao {
    @Insert
    fun insertAccount(account: Account): Long

    @Query("select * from account")
    fun queryAllAccount(): List<Account>
}