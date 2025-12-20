package com.naufal.tugas_proyek_pmob.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.tugas_proyek_pmob.R
import com.naufal.tugas_proyek_pmob.adapters.LowonganAdapter
import com.naufal.tugas_proyek_pmob.databinding.ActivityLowonganBinding
import com.naufal.tugas_proyek_pmob.models.Lowongan
import java.util.Locale

class LowonganActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLowonganBinding
    private lateinit var lowonganAdapter: LowonganAdapter
    private val allLowonganList = ArrayList<Lowongan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLowonganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadDummyData()
        setupListeners()
    }

    private fun setupRecyclerView() {
        lowonganAdapter = LowonganAdapter(ArrayList())
        binding.recyclerViewLowongan.apply {
            layoutManager = LinearLayoutManager(this@LowonganActivity)
            adapter = lowonganAdapter
        }
    }

    private fun loadDummyData() {
        // Data contoh
        allLowonganList.add(Lowongan("1", "UI/UX Designer", "Gojek", "Jakarta, Indonesia", R.drawable.logo_job, "Deskripsi lengkap Gojek...", -6.2088, 106.8456))
        allLowonganList.add(Lowongan("2", "Android Developer", "Tokopedia", "Jakarta, Indonesia", R.drawable.logo_job, "Deskripsi lengkap Tokopedia...", -6.1751, 106.8650))

        lowonganAdapter.filterList(allLowonganList)
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }

        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filter(newText)
                return true
            }
        })
    }

    private fun filter(text: String?) {
        val filteredList = ArrayList<Lowongan>()
        if (text != null) {
            for (item in allLowonganList) {
                if (item.judul.lowercase(Locale.ROOT).contains(text.lowercase(Locale.ROOT)) ||
                    item.namaPerusahaan.lowercase(Locale.ROOT).contains(text.lowercase(Locale.ROOT))) {
                    filteredList.add(item)
                }
            }
        }

        if (filteredList.isEmpty()) {
            binding.tvEmptyState.visibility = View.VISIBLE
            binding.recyclerViewLowongan.visibility = View.GONE
        } else {
            binding.tvEmptyState.visibility = View.GONE
            binding.recyclerViewLowongan.visibility = View.VISIBLE
        }

        lowonganAdapter.filterList(filteredList)
    }
}
