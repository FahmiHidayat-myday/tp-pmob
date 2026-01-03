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

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        setupClickListeners()
        loadUserData()
    }

    override fun onResume() {
        super.onResume()
        // Muat ulang data setiap kali kembali ke activity ini untuk menampilkan perubahan
        loadUserData()
    }

    private fun setupClickListeners() {
        binding.header.setOnClickListener {
            finish()
        }

        // Listener untuk tombol Edit Profil
        binding.menuEditProfile.setOnClickListener {
            startActivity(Intent(this, EditProfileActivity::class.java))
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
        val user = auth.currentUser ?: return

        val userRef = database.getReference("users").child(user.uid)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val fullName = snapshot.child("fullName").getValue(String::class.java)
                    val email = snapshot.child("email").getValue(String::class.java)

                    binding.tvName.text = fullName ?: email // Tampilkan nama lengkap, atau email jika nama kosong
                    binding.tvEmail.text = email
                } else {
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
            .setPositiveButton("Ya, Log Out") { _, _ ->
                auth.signOut()
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Batal", null)
            .show()
    }
}
