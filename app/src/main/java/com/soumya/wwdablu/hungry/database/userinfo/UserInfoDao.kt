package com.soumya.wwdablu.hungry.database.userinfo

import androidx.room.*
import com.soumya.wwdablu.hungry.database.userinfo.UserInfo

@Dao
interface UserInfoDao {

    @Query("SELECT * FROM user_info WHERE is_loggedin = 1")
    fun getLoggedUser() : UserInfo?

    @Query("SELECT * FROM USER_INFO WHERE user_identifier = :userId")
    fun isUserRegistered(userId: String) : UserInfo?

    @Insert
    fun registerLoggedInUser(userInfo: UserInfo)

    @Update
    fun updateLoginStatus(userInfo: UserInfo)

    @Delete
    fun removeUser(userInfo: UserInfo)
}