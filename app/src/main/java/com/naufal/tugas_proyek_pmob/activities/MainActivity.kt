package com.naufal.tugas_proyek_pmob.activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.naufal.tugas_proyek_pmob.R

class MainActivity : AppCompatActivity() {

    // Tentukan durasi splash screen dalam milidetik (misalnya, 2 detik)
    private val DURASI_SPLASH: Long = 2000

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Atur layout untuk MainActivity. Sebaiknya ini adalah layout khusus splash screen.
        // Jika Anda belum punya, Anda bisa buat activity_main.xml hanya dengan logo di tengah.
        setContentView(R.layout.activity_home)

        // Sembunyikan ActionBar agar tampilan lebih bersih
        supportActionBar?.hide()

        // Gunakan Handler untuk menunda eksekusi dan berpindah ke HomeActivity
        Handler(Looper.getMainLooper()).postDelayed({
            // Buat Intent untuk memulai HomeActivity
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)

            // Tutup MainActivity agar pengguna tidak bisa kembali ke splash screen
            finish()
        }, DURASI_SPLASH)
    }
}
