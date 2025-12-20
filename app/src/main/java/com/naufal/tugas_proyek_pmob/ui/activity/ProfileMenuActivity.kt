package com.naufal.tugas_proyek_pmob.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.naufal.tugas_proyek_pmob.databinding.ProfileMenuBinding

class ProfileMenuActivity : AppCompatActivity() {

    private lateinit var binding: ProfileMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ProfileMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()

        loadUserData()
    }

    private fun setupClickListeners() {
        binding.header.setOnClickListener {
            finish()
        }

        binding.menuMyProfile.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        binding.menuLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun loadUserData() {
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Log Out")
            .setMessage("Apakah Anda yakin ingin keluar dari akun Anda?")
            .setPositiveButton("Ya, Log Out") { dialog, which ->
                Toast.makeText(this, "Berhasil log out.", Toast.LENGTH_SHORT).show()
                finishAffinity()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
