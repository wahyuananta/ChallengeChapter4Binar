package com.coder.challengechapter4binar

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Club(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "Liga") var liga: String,
    @ColumnInfo(name = "tim_home") var tim_home: String,
    @ColumnInfo(name = "tim_away") var tim_away: String,
    @ColumnInfo(name = "tanggal") var tanggal: String,
    @ColumnInfo(name = "jam") var jam: String
): Parcelable
