package com.naufal.tugas_proyek_pmob.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.naufal.tugas_proyek_pmob.R
import com.naufal.tugas_proyek_pmob.adapters.NotifikasiAdapter
import com.naufal.tugas_proyek_pmob.databinding.ActivityNotifikasiBinding
import com.naufal.tugas_proyek_pmob.models.Notifikasi
import com.naufal.tugas_proyek_pmob.models.StatusLamaran

class NotifikasiActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotifikasiBinding
    private lateinit var notifikasiAdapter: NotifikasiAdapter
    private val allNotifikasiList = ArrayList<Notifikasi>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotifikasiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        loadDummyData()
        setupListeners()
        checkEmptyState()
    }

    private fun setupRecyclerView() {
        notifikasiAdapter = NotifikasiAdapter(allNotifikasiList)
        binding.recyclerViewNotifikasi.apply {
            layoutManager = LinearLayoutManager(this@NotifikasiActivity)
            adapter = notifikasiAdapter
        }
    }

    private fun loadDummyData() {
        allNotifikasiList.add(Notifikasi("1", "UI/UX Designer", "Tokopedia", StatusLamaran.DITERIMA, "1 hari yang lalu"))
        allNotifikasiList.add(Notifikasi("2", "Android Developer", "Gojek", StatusLamaran.DIPROSES, "3 hari yang lalu"))
        allNotifikasiList.add(Notifikasi("3", "Web Developer", "Traveloka", StatusLamaran.DILIHAT, "5 hari yang lalu"))
        allNotifikasiList.add(Notifikasi("4", "Product Manager", "Bukalapak", StatusLamaran.DITOLAK, "1 minggu yang lalu"))
        allNotifikasiList.add(Notifikasi("5", "Data Scientist", "Shopee", StatusLamaran.DILIHAT, "2 minggu yang lalu"))
    }

    private fun setupListeners() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

    private fun checkEmptyState() {
        if (allNotifikasiList.isEmpty()) {
            binding.tvEmptyState.visibility = View.VISIBLE
            binding.recyclerViewNotifikasi.visibility = View.GONE
        } else {
            binding.tvEmptyState.visibility = View.GONE
            binding.recyclerViewNotifikasi.visibility = View.VISIBLE
        }
    }
}
