package com.naufal.tugas_proyek_pmob.activities

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
import android.content.Intent

class LowonganActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLowonganBinding
    private lateinit var lowonganAdapter: LowonganAdapter
    private val allLowonganList = ArrayList<Lowongan>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLowonganBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        setupRecyclerView()

        // Muat data dummy untuk ditampilkan
        loadDummyData()

        // Setup listener untuk search dan tombol kembali
        setupListeners()
    }

    private fun setupRecyclerView() {
        // Inisialisasi adapter dengan list kosong terlebih dahulu
        lowonganAdapter = LowonganAdapter(ArrayList())
        binding.recyclerViewLowongan.apply {
            layoutManager = LinearLayoutManager(this@LowonganActivity)
            adapter = lowonganAdapter
        }
    }

    private fun loadDummyData() {
        // Ini adalah data contoh, nantinya bisa Anda ambil dari API atau database
        allLowonganList.add(Lowongan("1", "UI/UX Designer", "Gojek", "Jakarta, Indonesia", R.drawable.logo_job))
        allLowonganList.add(Lowongan("2", "Android Developer", "Tokopedia", "Jakarta, Indonesia", R.drawable.logo_job))
        allLowonganList.add(Lowongan("3", "Web Developer", "Traveloka", "Surabaya, Indonesia", R.drawable.logo_job))
        allLowonganList.add(Lowongan("4", "Product Manager", "Bukalapak", "Bandung, Indonesia", R.drawable.logo_job))
        allLowonganList.add(Lowongan("5", "Data Scientist", "Shopee", "Yogyakarta, Indonesia", R.drawable.logo_job))
        allLowonganList.add(Lowongan("6", "Quality Assurance", "Gojek", "Surabaya, Indonesia", R.drawable.logo_job))
        allLowonganList.add(Lowongan("7", "iOS Developer", "Blibli", "Jakarta, Indonesia", R.drawable.logo_job))

        // Tampilkan semua data saat pertama kali dibuka
        lowonganAdapter.filterList(allLowonganList)
    }

    private fun setupListeners() {
        // Tombol Kembali
        binding.btnBack.setOnClickListener {
            finish()
        }

        // Fungsi Pencarian (SearchView)
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
                // Logika pencarian: cari berdasarkan judul atau nama perusahaan
                if (item.judul.lowercase(Locale.ROOT).contains(text.lowercase(Locale.ROOT)) ||
                    item.namaPerusahaan.lowercase(Locale.ROOT).contains(text.lowercase(Locale.ROOT))) {
                    filteredList.add(item)
                }
            }
        }

        // Tampilkan atau sembunyikan pesan "Empty State"
        if (filteredList.isEmpty()) {
            binding.tvEmptyState.visibility = View.VISIBLE
            binding.recyclerViewLowongan.visibility = View.GONE
        } else {
            binding.tvEmptyState.visibility = View.GONE
            binding.recyclerViewLowongan.visibility = View.VISIBLE
        }

        // Perbarui data di adapter
        lowonganAdapter.filterList(filteredList)
    }
}
