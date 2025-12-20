package com.naufal.tugas_proyek_pmob.db.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "offices")
data class Office(@PrimaryKey(autoGenerate = true)
                  val id: Int = 0,

                  @ColumnInfo(name = "office_name")
                  val officeName: String,

                  val address: String,

    // Kolom ini krusial untuk Google Maps
                  val latitude: Double,

                  val longitude: Double
)