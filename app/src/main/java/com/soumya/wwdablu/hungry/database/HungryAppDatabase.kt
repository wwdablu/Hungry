package com.soumya.wwdablu.hungry.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.soumya.wwdablu.hungry.database.userinfo.UserInfo
import com.soumya.wwdablu.hungry.database.userinfo.UserInfoDao

@Database(entities = [UserInfo::class], exportSchema = false, version = 1)
abstract class HungryAppDatabase : RoomDatabase() {

    abstract fun UserInfoDao() : UserInfoDao
}