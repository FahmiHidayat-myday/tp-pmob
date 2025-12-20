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

    private val jobLocation = LatLng(-7.2575, 112.7521)

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
            openGoogleMaps()
        }
    }

    private fun openGoogleMaps() {
        val markerLabel = "Lokasi Pekerjaan"

        val gmmIntentUri = Uri.parse("geo:${jobLocation.latitude},${jobLocation.longitude}?q=${jobLocation.latitude},${jobLocation.longitude}($markerLabel)")

        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

        mapIntent.setPackage("com.google.android.apps.maps")

        if (mapIntent.resolveActivity(packageManager) != null) {
            startActivity(mapIntent)
        } else {
            Toast.makeText(this, "Aplikasi Google Maps tidak ditemukan.", Toast.LENGTH_LONG).show()
        }
    }

    private fun loadJobDetails() {
    }
}