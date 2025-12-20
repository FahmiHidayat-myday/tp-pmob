package com.naufal.tugas_proyek_pmob.ui.activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.naufal.tugas_proyek_pmob.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi Firebase Auth menggunakan cara standar
        auth = FirebaseAuth.getInstance()

        // Cek jika pengguna sudah login, langsung ke halaman utama
        if (auth.currentUser != null) {
            navigateToMainPage()
        }


        binding.loginButton.setOnClickListener {
            val email = binding.usernameEditText.text.toString().trim()
            val password = binding.passwordEditText.text.toString().trim()
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Email dan Password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            loginUser(email, password)
        }

        // Menambahkan listener untuk tombol ke halaman register
        binding.tvToRegister.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    private fun loginUser(email: String, password: String) {
        showLoading(true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                showLoading(false)
                if (task.isSuccessful) {
                    // Login berhasil
                    Toast.makeText(this, "Login Berhasil.", Toast.LENGTH_SHORT).show()
                    navigateToMainPage()
                } else {
                    // Jika login gagal, tampilkan pesan error
                    Toast.makeText(
                        baseContext,
                        "Otentikasi Gagal: ${task.exception?.message}",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    private fun navigateToMainPage() {
        val intent = Intent(this, HomeActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        // ProgressBar tidak ada di XML, jadi baris ini dinonaktifkan untuk mencegah crash
        // binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}