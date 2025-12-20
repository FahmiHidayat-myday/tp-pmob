package com.naufal.tugas_proyek_pmob.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.naufal.tugas_proyek_pmob.R

class MainActivity : AppCompatActivity() {

    private val DURASI_SPLASH: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            // Selalu arahkan ke LoginActivity setelah splash screen
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            // Tutup MainActivity agar tidak bisa kembali ke splash screen
            finish()
        }, DURASI_SPLASH)
    }
}
