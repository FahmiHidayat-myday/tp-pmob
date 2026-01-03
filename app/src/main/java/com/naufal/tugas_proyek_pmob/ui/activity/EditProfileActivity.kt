package com.naufal.tugas_proyek_pmob.ui.activity

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.naufal.tugas_proyek_pmob.databinding.ActivityEditProfileBinding

class EditProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        loadUserData()

        binding.btnSave.setOnClickListener {
            saveUserData()
        }
    }

    private fun loadUserData() {
        val userId = auth.currentUser?.uid ?: return

        val userRef = database.getReference("users").child(userId)
        userRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val fullName = snapshot.child("fullName").getValue(String::class.java)
                    val phoneNumber = snapshot.child("phoneNumber").getValue(String::class.java)
                    val address = snapshot.child("address").getValue(String::class.java)

                    binding.etFullName.setText(fullName)
                    binding.etPhoneNumber.setText(phoneNumber)
                    binding.etAddress.setText(address)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@EditProfileActivity, "Gagal memuat data.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun saveUserData() {
        val fullName = binding.etFullName.text.toString().trim()
        val phoneNumber = binding.etPhoneNumber.text.toString().trim()
        val address = binding.etAddress.text.toString().trim()
        val userId = auth.currentUser?.uid ?: return

        if (fullName.isEmpty() || phoneNumber.isEmpty() || address.isEmpty()) {
            Toast.makeText(this, "Semua kolom harus diisi", Toast.LENGTH_SHORT).show()
            return
        }

        val userUpdates = mapOf(
            "fullName" to fullName,
            "phoneNumber" to phoneNumber,
            "address" to address
        )

        val userRef = database.getReference("users").child(userId)
        userRef.updateChildren(userUpdates)
            .addOnSuccessListener {
                Toast.makeText(this, "Profil berhasil diperbarui", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke halaman profil
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal memperbarui profil", Toast.LENGTH_SHORT).show()
            }
    }
}