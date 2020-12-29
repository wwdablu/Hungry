package com.soumya.wwdablu.hungry.database

import android.content.Context
import androidx.room.Room

object HungryDatabase {

    private var INSTANCE: HungryAppDatabase? = null

    @Synchronized
    fun getDB(context: Context) : HungryAppDatabase {

        if(INSTANCE == null) {
            INSTANCE = buildRoomDatabase(context)
        }

        return INSTANCE!!
    }

    private fun buildRoomDatabase(context: Context) : HungryAppDatabase {

        return Room.databaseBuilder(context, HungryAppDatabase::class.java, "hungry.db")
                .build()
    }
}