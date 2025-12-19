package com.naufal.tugas_proyek_pmob.activities

import android.content.Intent
import android.net.Uri // PERLU IMPORT INI
import android.os.Bundle
import android.widget.Toast // PERLU IMPORT INI
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng // PERLU IMPORT INI
import com.naufal.tugas_proyek_pmob.databinding.ActivityDetailLowonganBinding

class DetailLowonganActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailLowonganBinding

    // Simpan koordinat lokasi untuk dibuka di Google Maps
    private val jobLocation = LatLng(-7.2575, 112.7521) // Contoh: Surabaya

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLowonganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Panggil fungsi untuk mengatur semua aksi klik
        setupClickListeners()

        // Muat data detail lowongan
        loadJobDetails()
    }

    private fun setupClickListeners() {
        // 1. Tombol Daftar Pekerjaan
        binding.btnDaftar.setOnClickListener {
            val intent = Intent(this, DaftarPekerjaanActivity::class.java)
            startActivity(intent)
        }

        // 2. Tombol Kembali
        binding.btnBack.setOnClickListener {
            finish()
        }

        // ================= AWAL PERBAIKAN =================
        // 3. Atur klik pada tombol untuk MEMBUKA APLIKASI GOOGLE MAPS
        binding.btnBukaPeta.setOnClickListener {
            openGoogleMaps()
        }
        // ================= AKHIR PERBAIKAN =================
    }

    // FUNGSI BARU UNTUK MEMBUKA GOOGLE MAPS
    private fun openGoogleMaps() {
        // Nama label untuk marker di Google Maps
        val markerLabel = "Lokasi Pekerjaan"

        // Buat Uri untuk Intent Google Maps
        val gmmIntentUri = Uri.parse("geo:${jobLocation.latitude},${jobLocation.longitude}?q=${jobLocation.latitude},${jobLocation.longitude}($markerLabel)")

        // Buat Intent dengan aksi VIEW
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

        // Tentukan package Google Maps secara eksplisit
        mapIntent.setPackage("com.google.android.apps.maps")

        // Periksa apakah aplikasi Google Maps terinstall
        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            // Jika tidak, beri tahu pengguna
            Toast.makeText(this, "Aplikasi Google Maps tidak ditemukan.", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadJobDetails() {
        // Fungsi ini tetap sama
        // ...
    }
}