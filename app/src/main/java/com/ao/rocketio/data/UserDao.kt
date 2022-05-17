package com.ao.rocketio.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ao.rocketio.data.BEUser

/* Defined interface for database CRUD user */
@Dao
interface UserDao {

    @Query("SELECT * from BEUser order by id")
    fun getAll(): LiveData<List<BEUser>>

    @Query("SELECT * from BEUser where id = (:id)")
    fun getById(id: Int): LiveData<BEUser>

    @Insert
    fun insert(user: BEUser)

    @Update
    fun update(user: BEUser)

    @Delete
    fun delete(user: BEUser)
}