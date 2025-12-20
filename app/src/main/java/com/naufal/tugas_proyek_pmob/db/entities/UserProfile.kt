package com.naufal.tugas_proyek_pmob.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_profiles")
data class UserProfile(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "full_name")
    val fullName: String,

    val email: String,

    @ColumnInfo(name = "phone_number")
    val phoneNumber: String,

    val address: String,

    @ColumnInfo(name = "special_needs")
    val specialNeeds: String?, // Dibuat nullable jika tidak semua pengguna memilikinya

    val skills: String? // Dibuat nullable
)