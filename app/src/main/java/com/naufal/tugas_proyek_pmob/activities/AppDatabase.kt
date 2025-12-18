package com.naufal.tugas_proyek_pmob.activities
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.naufal.tugas_proyek_pmob.db.dao.AppDao
import com.naufal.tugas_proyek_pmob.db.entities.Office
import com.naufal.tugas_proyek_pmob.db.entities.UserProfile

@Database(entities = [UserProfile::class, Office::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {
        // Volatile agar instance ini selalu up-to-date untuk semua thread
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // Jika instance sudah ada, kembalikan.
            // Jika tidak, buat database baru (synchronized untuk mencegah race condition)
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database" // Nama file database di perangkat
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}