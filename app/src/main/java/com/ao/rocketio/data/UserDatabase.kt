package com.ao.rocketio.data

import androidx.room.Database
import androidx.room.RoomDatabase

/* Defined database */
@Database(entities = [BEUser::class], version=1)
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao
}