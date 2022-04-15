package com.coder.challengechapter4binar

import androidx.annotation.WorkerThread

class Repository(private val dataUserDao: DataUserDao, private val clubDao: ClubDao) {

    val allClub: List<Club> = clubDao.getAllClub()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(club: Club): Long {
        return clubDao.insertClub(club)
    }

    fun update(club: Club): Int {
        return clubDao.updateClub(club)
    }

    fun delete(club: Club): Int {
        return clubDao.deleteClub(club)
    }

    fun insertDataUser(dataUser: DataUser): Long {
        return dataUserDao.addUser(dataUser)
    }
}