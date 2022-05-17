package com.ao.rocketio.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.Room
import java.util.concurrent.Executors

/* User repository setup database, CRUD methods and initialization */
class UserRepositoryInDB private constructor(private val context: Context) {

    private val database: UserDatabase = Room.databaseBuilder(context.applicationContext, UserDatabase::class.java, "user-database").build()

    private val userDao = database.userDao()

    fun getAll(): LiveData<List<BEUser>> = userDao.getAll()

    fun getById(id: Int) = userDao.getById(id)

    private val executor = Executors.newSingleThreadExecutor()

    fun insert(user: BEUser) {
        executor.execute{ userDao.insert(user) }
    }

    fun update(user: BEUser) {
        executor.execute{ userDao.update(user
        ) }
    }

    fun delete(user: BEUser) {
        executor.execute{ userDao.delete(user) }
    }

    companion object {
        private var Instance: UserRepositoryInDB? = null

        fun initialize(context: Context) {
            if (Instance == null)
                Instance = UserRepositoryInDB(context)
        }

        fun get(): UserRepositoryInDB {
            if (Instance != null) return Instance!!
            throw IllegalStateException("User repo not initialized")
        }
    }
}