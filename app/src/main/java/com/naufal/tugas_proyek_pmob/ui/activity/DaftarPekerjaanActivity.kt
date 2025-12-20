package com.naufal.tugas_proyek_pmob.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.naufal.tugas_proyek_pmob.databinding.DaftarPekerjaanBinding
import java.util.Date

class DaftarPekerjaanActivity : AppCompatActivity() {

    private lateinit var binding: DaftarPekerjaanBinding
    private var selectedFileUri: Uri? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    // Variabel untuk menampung detail pekerjaan
    private var jobTitle: String? = null
    private var companyName: String? = null

    private val filePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let {
                uri ->
                selectedFileUri = uri
                binding.tvUploadCV.text = getFileName(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DaftarPekerjaanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi Firebase
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        setupClickListeners()
        loadJobDetails()
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnUploadCV.setOnClickListener {
            openFilePicker()
        }

        binding.btnKirimLamaran.setOnClickListener {
            if (validateInput()) {
                submitApplication()
            }
        }
    }

    private fun submitApplication() {
        val fullName = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()
        val cvFileName = selectedFileUri?.let { getFileName(it) } ?: "Tidak ada file"
        val userId = auth.currentUser?.uid

        if (userId == null) {
            Toast.makeText(this, "Anda harus login untuk melamar.", Toast.LENGTH_LONG).show()
            return
        }

        // Membuat ID unik untuk setiap lamaran
        val applicationId = database.reference.child("applications").push().key

        if (applicationId == null) {
            Toast.makeText(this, "Gagal membuat lamaran, coba lagi.", Toast.LENGTH_SHORT).show()
            return
        }

        val applicationData = mapOf(
            "userId" to userId,
            "jobTitle" to jobTitle,
            "companyName" to companyName,
            "applicantName" to fullName,
            "applicantEmail" to email,
            "cvFileName" to cvFileName,
            "status" to "Terkirim",
            "timestamp" to Date().time
        )

        // Simpan ke database di bawah node userId
        database.getReference("applications").child(userId).child(applicationId).setValue(applicationData)
            .addOnSuccessListener {
                Toast.makeText(this, "Lamaran berhasil dikirim!", Toast.LENGTH_LONG).show()
                finish() // Kembali ke halaman detail
            }
            .addOnFailureListener {
                Toast.makeText(this, "Gagal mengirim lamaran: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        filePickerLauncher.launch(intent)
    }

    private fun getFileName(uri: Uri): String {
        var fileName: String? = null
        contentResolver.query(uri, null, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                if (nameIndex != -1) {
                    fileName = cursor.getString(nameIndex)
                }
            }
        }
        return fileName ?: "File Terpilih"
    }

    private fun validateInput(): Boolean {
        if (binding.etFullName.text.toString().trim().isEmpty()) {
            binding.etFullName.error = "Nama lengkap tidak boleh kosong"
            return false
        }
        if (binding.etEmail.text.toString().trim().isEmpty()) {
            binding.etEmail.error = "Email tidak boleh kosong"
            return false
        }
        if (selectedFileUri == null) {
            Toast.makeText(this, "Silakan unggah CV Anda", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
    }

    private fun loadJobDetails() {
        // Ambil data yang dikirim dari DetailLowonganActivity
        jobTitle = intent.getStringExtra("EXTRA_JOB_TITLE")
        companyName = intent.getStringExtra("EXTRA_COMPANY_NAME")

        // Tampilkan di UI
        binding.tvJobTitle.text = jobTitle
        binding.tvCompanyName.text = companyName
    }
}
