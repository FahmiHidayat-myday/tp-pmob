package com.naufal.tugas_proyek_pmob.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.naufal.tugas_proyek_pmob.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.registerButton.setOnClickListener {
            val email = binding.registerUsernameEditText.text.toString().trim()
            val password = binding.registerPasswordEditText.text.toString().trim()

            if (email.isEmpty()) {
                binding.registerUsernameEditText.error = "Email tidak boleh kosong"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                binding.registerPasswordEditText.error = "Password tidak boleh kosong"
                return@setOnClickListener
            }

            if (password.length < 6) {
                binding.registerPasswordEditText.error = "Password minimal 6 karakter"
                return@setOnClickListener
            }

            registerUser(email, password)
        }

        binding.backButton.setOnClickListener {
            finish()
        }
    }

    private fun registerUser(email: String, password: String) {
        showLoading(true)

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    val userMap = mapOf("email" to email)

                    user?.let {
                        database.getReference("users").child(it.uid).setValue(userMap)
                            .addOnSuccessListener {
                                showLoading(false)
                                Toast.makeText(this, "Pendaftaran berhasil! Silakan login.", Toast.LENGTH_LONG).show()

                                // Arahkan kembali ke halaman Login
                                val intent = Intent(this, LoginActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener { e ->
                                showLoading(false)
                                Toast.makeText(this, "Gagal menyimpan data: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }

                } else {
                    showLoading(false)
                    Toast.makeText(
                        baseContext,
                        "Pendaftaran Gagal: ${task.exception?.message}",
                        Toast.LENGTH_LONG,
                    ).show()
                }
            }
    }

    private fun showLoading(isLoading: Boolean) {
        // Anda perlu menambahkan ProgressBar ke XML dengan ID progressBar agar ini berfungsi
        // binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}