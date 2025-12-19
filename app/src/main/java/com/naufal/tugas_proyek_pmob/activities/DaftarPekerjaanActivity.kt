package com.naufal.tugas_proyek_pmob.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.naufal.tugas_proyek_pmob.databinding.DaftarPekerjaanBinding // Perhatikan nama kelas binding ini

class DaftarPekerjaanActivity : AppCompatActivity() {

    // Deklarasikan variabel untuk view binding
    // Nama kelas 'DaftarPekerjaanBinding' dibuat dari nama file 'daftar_pekerjaan.xml'
    private lateinit var binding: DaftarPekerjaanBinding

    // Variabel untuk menyimpan URI dari file yang dipilih
    private var selectedFileUri: Uri? = null

    // Gunakan ActivityResultLauncher untuk menangani hasil dari pemilih file
    private val filePickerLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            result.data?.data?.let { uri ->
                selectedFileUri = uri
                // Tampilkan nama file yang dipilih di UI
                binding.tvUploadCV.text = getFileName(uri)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inisialisasi binding dan atur layout
        binding = DaftarPekerjaanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Panggil fungsi untuk mengatur semua aksi klik
        setupClickListeners()

        // Muat data pekerjaan yang dilamar (dari Intent)
        loadJobDetails()
    }

    private fun setupClickListeners() {

        // 1. Tombol Kembali di Header
        binding.btnBack.setOnClickListener {
            finish() // Menutup activity
        }

        // 2. Tombol "Unggah Dokumen"
        binding.btnUploadCV.setOnClickListener {
            openFilePicker()
        }

        // 3. Tombol "Kirim Lamaran"
        binding.btnKirimLamaran.setOnClickListener {
            if (validateInput()) {
                // Lakukan proses pengiriman data ke server/API
                // ... (logika upload file dan data) ...

                // Tampilkan pesan sukses dan kembali ke halaman sebelumnya
                Toast.makeText(this, "Lamaran berhasil dikirim!", Toast.LENGTH_LONG).show()
                finish()
            }
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_GET_CONTENT).apply {
            type = "*/*" // Izinkan semua jenis file
            addCategory(Intent.CATEGORY_OPENABLE)
            // Anda bisa lebih spesifik dengan tipe MIME
            // val mimeTypes = arrayOf("application/pdf", "application/msword", "application/vnd.openxmlformats-officedocument.wordprocessingml.document")
            // putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
        }
        filePickerLauncher.launch(intent)
    }

    // Fungsi helper untuk mendapatkan nama file dari URI
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
        // Muat detail pekerjaan dari intent yang dikirim oleh DetailLowonganActivity
        // Contoh:
        // val jobTitle = intent.getStringExtra("JOB_TITLE")
        // binding.tvJobTitle.text = jobTitle
    }
}
