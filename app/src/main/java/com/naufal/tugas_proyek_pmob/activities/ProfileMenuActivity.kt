package com.naufal.tugas_proyek_pmob.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.naufal.tugas_proyek_pmob.databinding.ProfileMenuBinding // Import kelas binding yang sesuai

class ProfileMenuActivity : AppCompatActivity() {

    // Deklarasikan variabel untuk view binding
    // Nama kelas binding 'ProfileMenuBinding' dibuat dari nama file 'profile_menu.xml'
    private lateinit var binding: ProfileMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi binding dan atur layout
        binding = ProfileMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Panggil fungsi untuk mengatur semua aksi klik
        setupClickListeners()

        // Muat data pengguna (misalnya dari SharedPreferences atau API)
        loadUserData()
    }

    private fun setupClickListeners() {
        // 1. Tombol Kembali di Header
        binding.header.setOnClickListener {
            finish() // Menutup activity dan kembali ke halaman sebelumnya
        }

        // 2. Menu "My Profile"
        binding.menuMyProfile.setOnClickListener {
            // Arahkan pengguna ke halaman detail profil
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // 3. Menu "Log Out"
        binding.menuLogout.setOnClickListener {
            // Tampilkan dialog konfirmasi sebelum logout
            showLogoutConfirmationDialog()
        }
    }

    private fun loadUserData() {
        // Di sini Anda bisa memuat data pengguna dan menampilkannya
        // Contoh:
        // binding.tvName.text = "Nama Pengguna dari DB"
        // binding.tvEmail.text = "email.pengguna@example.com"
        // Anda juga bisa memuat gambar profil menggunakan library seperti Glide atau Coil
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Log Out")
            .setMessage("Apakah Anda yakin ingin keluar dari akun Anda?")
            .setPositiveButton("Ya, Log Out") { dialog, which ->
                // Lakukan aksi logout di sini (hapus sesi, dll)
                Toast.makeText(this, "Berhasil log out.", Toast.LENGTH_SHORT).show()

                // Arahkan ke halaman login dan hapus semua activity sebelumnya
                // val intent = Intent(this, LoginActivity::class.java)
                // intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                // startActivity(intent)

                // Untuk sementara, kita tutup semua activity
                finishAffinity()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
