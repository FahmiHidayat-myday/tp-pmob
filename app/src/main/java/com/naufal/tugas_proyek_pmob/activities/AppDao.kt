package com.naufal.tugas_proyek_pmob.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.naufal.tugas_proyek_pmob.db.entities.Office
import com.naufal.tugas_proyek_pmob.db.entities.UserProfile

@Dao
interface AppDao {

    // --- Fungsi untuk UserProfile ---

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserProfile(userProfile: UserProfile)

    @Query("SELECT * FROM user_profiles WHERE id = :userId")
    suspend fun getUserProfileById(userId: Int): UserProfile?

    @Query("SELECT * FROM user_profiles")
    suspend fun getAllUserProfiles(): List<UserProfile>


    // --- Fungsi untuk Office ---

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOffice(office: Office)

    @Query("SELECT * FROM offices")
    suspend fun getAllOffices(): List<Office>

    @Query("SELECT * FROM offices WHERE id = :officeId")
    suspend fun getOfficeById(officeId: Int): Office?
}