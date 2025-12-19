package com.naufal.tugas_proyek_pmob.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.naufal.tugas_proyek_pmob.databinding.ActivityHomeBinding // Import kelas binding yang digenerate

class HomeActivity : AppCompatActivity() {

    // Deklarasikan variabel untuk view binding
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi binding dan atur layout
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Panggil fungsi untuk mengatur semua aksi klik
        setupClickListeners()
    }

    private fun setupClickListeners() {
        // 1. Klik pada ikon profil di pojok kanan atas
        binding.imageView.setOnClickListener {
            // Arahkan ke ProfileActivity
            val intent = Intent(this, ProfileMenuActivity::class.java)
            startActivity(intent)
        }

        // 2. Klik pada Card "Daftar Lowongan"
        binding.cardLowongan.setOnClickListener {

            val intent = Intent(this, LowonganActivity::class.java)
            startActivity(intent)

        }

        // 3. Klik pada Card "Daftar Notifikasi"
        binding.cardNotifikasi.setOnClickListener {
            val intent = Intent(this, NotifikasiActivity::class.java)
            startActivity(intent)
            // Arahkan ke Activity yang menampilkan notifikasi
            // Misalnya, kita beri nama 'NotifikasiActivity'
            // val intent = Intent(this, NotifikasiActivity::class.java)
            // startActivity(intent)
        }

        // 4. Klik pada tombol "Pencarian Berdasarkan Lokasi" di bawah
        binding.locationCardButton.setOnClickListener {
            // Arahkan ke MapLokerActivity
            val intent = Intent(this, DetailLowonganActivity::class.java)
            startActivity(intent)
        }
    }
}
