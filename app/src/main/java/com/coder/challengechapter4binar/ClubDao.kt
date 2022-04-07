package com.coder.challengechapter4binar

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE

@Dao
interface ClubDao {
    @Query("SELECT * FROM Club")
    fun getAllClub():List<Club>

    @Insert(onConflict = REPLACE)
    fun insertClub(club: Club):Long

    @Update
    fun updateClub(club: Club):Int

    @Delete
    fun deleteClub(club: Club):Int
}