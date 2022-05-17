package com.coder.challengechapter4binar

import android.content.Context

class Repository(context: Context) {
    private val mDb = AppDatabase.getInstance(context)

    fun getAllClub(): List<Club> {
        return mDb!!.clubDao().getAllClub()
    }

    fun insert(club: Club): Long {
        return mDb!!.clubDao().insertClub(club)
    }

    fun update(club: Club): Int {
        return mDb!!.clubDao().updateClub(club)
    }

    fun delete(club: Club): Int {
        return mDb!!.clubDao().deleteClub(club)
    }

    fun checkUser(username: String, password: String): Boolean {
        return mDb!!.dataUserDao().checkUser(username, password)
    }

    fun addUser(dataUser: DataUser): Long {
        return mDb!!.dataUserDao().addUser(dataUser)
    }
}