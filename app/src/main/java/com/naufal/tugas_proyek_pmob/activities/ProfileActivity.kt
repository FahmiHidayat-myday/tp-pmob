package com.naufal.tugas_proyek_pmob.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.naufal.tugas_proyek_pmob.databinding.ProfileBinding // Import kelas binding yang sesuai

class ProfileActivity : AppCompatActivity() {

    // Deklarasikan variabel untuk view binding
    private lateinit var binding: ProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi binding dan atur layout
        // Perhatian: Nama kelas binding adalah 'ProfileBinding' karena nama file XML adalah 'profile.xml'
        binding = ProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Panggil fungsi untuk mengatur semua aksi klik
        setupClickListeners()

        // Panggil fungsi untuk memuat data profil (misalnya dari database atau API)
        loadUserProfile()
    }

    private fun setupClickListeners() {
        // 1. Tombol Kembali (di pojok kiri atas)
        binding.btnBack.setOnClickListener {
            // Menutup Activity saat ini dan kembali ke Activity sebelumnya
            finish()
        }

        // 3. Tombol Log Out
        binding.btnLogout.setOnClickListener {
            // Tampilkan dialog konfirmasi sebelum benar-benar logout
            showLogoutConfirmationDialog()
        }
    }

    private fun loadUserProfile() {
        // Di sini Anda akan memuat data pengguna dari sumber data Anda (misalnya, database, SharedPreferences, atau API).
        // Untuk saat ini, kita akan menggunakan data placeholder yang ada di XML.
        // Contoh cara mengisi data secara dinamis:
        // binding.tvName.text = "Naufal"
        // binding.tvEmail.text = "naufal.keren@email.com"
        // binding.tvPhoneNumber.text = "+62 123-4567-8901"
        // binding.tvAddress.text = "Surabaya, Indonesia"
        // binding.tvSpecialNeeds.text = "Tidak ada"
        // binding.tvSkills.text = "• Android Development (Kotlin)\n• UI/UX Design"
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Log Out")
            .setMessage("Apakah Anda yakin ingin keluar dari akun Anda?")
            .setPositiveButton("Ya, Log Out") { dialog, which ->
                // Lakukan aksi logout di sini
                // Contoh: Hapus data sesi, lalu arahkan ke halaman login
                // val intent = Intent(this, LoginActivity::class.java)
                // intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                // startActivity(intent)

                // Untuk sementara, kita tampilkan pesan Toast dan tutup aplikasi
                Toast.makeText(this, "Berhasil log out.", Toast.LENGTH_SHORT).show()
                finishAffinity() // Menutup semua activity
            }
            .setNegativeButton("Batal", null) // Tombol "Batal" tidak melakukan apa-apa
            .show()
    }
}
