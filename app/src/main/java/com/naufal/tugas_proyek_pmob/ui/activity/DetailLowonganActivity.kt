package com.naufal.tugas_proyek_pmob.ui.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.model.LatLng
import com.naufal.tugas_proyek_pmob.databinding.ActivityDetailLowonganBinding

class DetailLowonganActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailLowonganBinding
    private var jobLocation: LatLng? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailLowonganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
        loadJobDetails()
    }

    private fun setupClickListeners() {
        binding.btnDaftar.setOnClickListener {
            val intent = Intent(this, DaftarPekerjaanActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.btnBukaPeta.setOnClickListener {
            jobLocation?.let {
                openGoogleMaps(it)
            } ?: Toast.makeText(this, "Lokasi pekerjaan tidak tersedia.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun openGoogleMaps(location: LatLng) {
        val markerLabel = binding.tvJobTitle.text.toString()
        val gmmIntentUri = Uri.parse("geo:${location.latitude},${location.longitude}?q=${location.latitude},${location.longitude}($markerLabel)")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")

        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            Toast.makeText(this, "Aplikasi Google Maps tidak ditemukan.", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadJobDetails() {
        val extras = intent.extras
        if (extras != null) {
            val jobTitle = extras.getString("EXTRA_JOB_TITLE")
            val companyName = extras.getString("EXTRA_COMPANY_NAME")
            val description = extras.getString("EXTRA_DESCRIPTION")
            val latitude = extras.getDouble("EXTRA_LATITUDE")
            val longitude = extras.getDouble("EXTRA_LONGITUDE")

            binding.tvJobTitle.text = jobTitle
            binding.tvCompanyName.text = companyName
            binding.tvJobDescription.text = description

            // Simpan lokasi untuk tombol peta
            jobLocation = LatLng(latitude, longitude)

        } else {
            // Fallback jika tidak ada data yang dikirim
            Toast.makeText(this, "Gagal memuat detail lowongan.", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}