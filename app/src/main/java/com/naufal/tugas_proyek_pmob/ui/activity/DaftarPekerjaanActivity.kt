package com.naufal.tugas_proyek_pmob.ui.activity

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.naufal.tugas_proyek_pmob.databinding.DaftarPekerjaanBinding

class DaftarPekerjaanActivity : AppCompatActivity() {

    private lateinit var binding: DaftarPekerjaanBinding

    private var selectedFileUri: Uri? = null

    private val filePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedFileUri = uri
                binding.tvUploadCV.text = getFileName(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DaftarPekerjaanBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
                Toast.makeText(this, "Lamaran berhasil dikirim!", Toast.LENGTH_LONG).show()
                finish()
            }
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
        val fullName = binding.etFullName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()

        if (fullName.isEmpty()) {
            binding.etFullName.error = "Nama lengkap tidak boleh kosong"
            binding.etFullName.requestFocus()
            return false
        }

        if (email.isEmpty()) {
            binding.etEmail.error = "Email tidak boleh kosong"
            binding.etEmail.requestFocus()
            return false
        }

        if (selectedFileUri == null) {
            Toast.makeText(this, "Silakan unggah CV Anda", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun loadJobDetails() {
    }
}
