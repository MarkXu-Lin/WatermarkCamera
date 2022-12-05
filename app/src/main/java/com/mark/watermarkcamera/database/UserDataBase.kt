package com.mark.watermarkcamera.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.mark.watermarkcamera.MainApplication
import com.mark.watermarkcamera.dao.AccountDao
import com.mark.watermarkcamera.domain.Account

@Database(entities = [Account::class], version = 1)
abstract class UserDataBase : RoomDatabase() {
    abstract val accountDao: AccountDao

    companion object {
        val userDb: UserDataBase by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Room.databaseBuilder(
                MainApplication.getApp(),
                UserDataBase::class.java,
                "user.db"
            )
                .allowMainThreadQueries()
                .build()
        }
    }
}