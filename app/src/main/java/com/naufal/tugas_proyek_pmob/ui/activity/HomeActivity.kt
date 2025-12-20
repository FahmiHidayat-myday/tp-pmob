package com.naufal.tugas_proyek_pmob.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.naufal.tugas_proyek_pmob.databinding.ActivityHomeBinding
import com.naufal.tugas_proyek_pmob.ui.activity.LowonganActivity
import com.naufal.tugas_proyek_pmob.ui.activity.NotifikasiActivity
import com.naufal.tugas_proyek_pmob.ui.activity.ProfileMenuActivity

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.imageView.setOnClickListener {
            val intent = Intent(this, ProfileMenuActivity::class.java)
            startActivity(intent)
        }

        binding.cardLowongan.setOnClickListener {

            val intent = Intent(this, LowonganActivity::class.java)
            startActivity(intent)

        }

        binding.cardNotifikasi.setOnClickListener {
            val intent = Intent(this, NotifikasiActivity::class.java)
            startActivity(intent)
        }

        binding.locationCardButton.setOnClickListener {
            val intent = Intent(this, DetailLowonganActivity::class.java)
            startActivity(intent)
        }
    }
}
