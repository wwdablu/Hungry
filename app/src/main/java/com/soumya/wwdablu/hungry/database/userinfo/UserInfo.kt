package com.soumya.wwdablu.hungry.database.userinfo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_info")
data class UserInfo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "is_loggedin")
    val isLoggedIn: Boolean,

    @ColumnInfo(name = "user_identifier")
    val userIdentifier: String
)