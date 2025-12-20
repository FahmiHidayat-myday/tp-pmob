package com.naufal.tugas_proyek_pmob.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.naufal.tugas_proyek_pmob.databinding.ProfileMenuBinding

class ProfileMenuActivity : AppCompatActivity() {

    private lateinit var binding: ProfileMenuBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

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
        val user = auth.currentUser
        if (user == null) {
            // Jika tidak ada sesi, kembali ke halaman login
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            return
        }

        // Ambil data dari Firebase Realtime Database
        val userRef = database.getReference("users").child(user.uid)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val email = snapshot.child("email").getValue(String::class.java)
                    // Tampilkan data ke UI
                    binding.tvName.text = email // Sementara nama ditampilkan sebagai email
                    binding.tvEmail.text = email
                } else {
                    // Fallback jika data tidak ada di database
                    binding.tvName.text = "Pengguna"
                    binding.tvEmail.text = user.email
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileMenuActivity, "Gagal memuat data.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Log Out")
            .setMessage("Apakah Anda yakin ingin keluar dari akun Anda?")
            .setPositiveButton("Ya, Log Out") { dialog, which ->
                // Hapus sesi login
                auth.signOut()
                // Kembali ke alur awal
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
