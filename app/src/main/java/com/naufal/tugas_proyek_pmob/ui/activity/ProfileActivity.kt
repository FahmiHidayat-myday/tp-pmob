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
import com.naufal.tugas_proyek_pmob.databinding.ProfileBinding

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        setupClickListeners()
    }

    override fun onResume() {
        super.onResume()
        // Muat ulang data setiap kali kembali ke halaman ini
        loadUserProfile()
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnLogout.setOnClickListener {
            showLogoutConfirmationDialog()
        }
    }

    private fun loadUserProfile() {
        val user = auth.currentUser
        if (user == null) {
            // Jika tidak ada sesi, kembali ke halaman login
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            return
        }

        val userRef = database.getReference("users").child(user.uid)
        userRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    // Ambil semua data dari database
                    val fullName = snapshot.child("fullName").getValue(String::class.java)
                    val email = snapshot.child("email").getValue(String::class.java)
                    val phoneNumber = snapshot.child("phoneNumber").getValue(String::class.java)
                    val address = snapshot.child("address").getValue(String::class.java)

                    // Tampilkan data ke UI, berikan nilai default jika null
                    binding.tvName.text = fullName ?: "Nama belum diatur"
                    binding.tvEmail.text = email ?: "Email tidak tersedia"
                    binding.tvPhoneNumber.text = phoneNumber ?: "No. HP belum diatur"
                    binding.tvAddress.text = address ?: "Alamat belum diatur"

                } else {
                    // Fallback jika data pengguna belum ada
                    binding.tvName.text = "Pengguna Baru"
                    binding.tvEmail.text = user.email
                    binding.tvPhoneNumber.text = "-"
                    binding.tvAddress.text = "-"
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity, "Gagal memuat data: ${error.message}", Toast.LENGTH_SHORT).show()
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
