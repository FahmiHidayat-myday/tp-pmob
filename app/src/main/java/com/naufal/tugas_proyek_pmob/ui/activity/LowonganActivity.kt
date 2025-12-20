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
        allLowonganList.add(Lowongan("1", "Customer Service (Disabilitas)", "Grab Indonesia", "Jakarta, Indonesia", R.drawable.logo_job, "Program Grab Inklusif untuk rekan disabilitas fisik dan rungu.", -6.2088, 106.8456)) // Jakarta
        allLowonganList.add(Lowongan("2", "Crew Store (Disabilitas)", "Alfabel (Alfamart)", "Tangerang, Indonesia", R.drawable.logo_job, "Program Alfabel memberikan kesempatan bagi penyandang disabilitas fisik dan rungu untuk bekerja di gerai.", -6.1781, 106.63)) // Tangerang
        allLowonganList.add(Lowongan("3", "Barista (Tuli)", "Sunyi Coffee", "Jakarta, Indonesia", R.drawable.logo_job, "Kafe inklusif yang mempekerjakan teman tuli sebagai barista dan pengelola operasional.", -6.2297, 106.8093)) // Jakarta Selatan
        allLowonganList.add(Lowongan("4", "Administrative Staff", "PT Telkom Indonesia", "Bandung, Indonesia", R.drawable.logo_job, "Rekrutmen khusus disabilitas melalui jalur BUMN yang menyediakan fasilitas kerja aksesibel.", -6.9175, 107.6191)) // Bandung
        allLowonganList.add(Lowongan("5", "Data Entry Specialist", "PT Unilever Indonesia", "Jakarta, Indonesia", R.drawable.logo_job, "Mendukung lingkungan kerja yang beragam dan inklusif bagi karyawan dengan disabilitas.", -6.1751, 106.8650)) // Jakarta Pusat
        allLowonganList.add(Lowongan("6", "Digital Marketing", "ThisAble Enterprise", "Jakarta, Indonesia", R.drawable.logo_job, "Lembaga penyalur kerja bentukan Angkie Yudistia yang fokus pada pemberdayaan disabilitas.", -6.2415, 106.8227)) // Jakarta Selatan
        allLowonganList.add(Lowongan("7", "Production Operator", "PT Schneider Electric", "Batam, Indonesia", R.drawable.logo_job, "Perusahaan yang telah mendapatkan penghargaan atas komitmen mempekerjakan tenaga kerja disabilitas.", 1.1445, 104.0093)) // Batam

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