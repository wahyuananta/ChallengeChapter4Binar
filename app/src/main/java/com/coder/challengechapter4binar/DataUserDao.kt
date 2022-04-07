package com.coder.challengechapter4binar

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query


@Dao
interface DataUserDao {
    @Query("SELECT EXISTS(SELECT * FROM DataUser WHERE username = :username and password = :password)")
    fun checkUser(username: String, password: String): Boolean

    @Insert(onConflict = REPLACE)
    fun addUser(dataUser: DataUser): Long
}