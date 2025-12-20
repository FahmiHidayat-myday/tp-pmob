package com.naufal.tugas_proyek_pmob.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.naufal.tugas_proyek_pmob.databinding.ItemLowonganBinding
import com.naufal.tugas_proyek_pmob.models.Lowongan
import com.naufal.tugas_proyek_pmob.ui.activity.DetailLowonganActivity

class LowonganAdapter(private var lowonganList: List<Lowongan>) :
    RecyclerView.Adapter<LowonganAdapter.LowonganViewHolder>() {

    inner class LowonganViewHolder(val binding: ItemLowonganBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LowonganViewHolder {
        val binding = ItemLowonganBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LowonganViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LowonganViewHolder, position: Int) {
        val lowongan = lowonganList[position]
        holder.binding.apply {
            tvJobTitle.text = lowongan.judul
            tvCompanyName.text = lowongan.namaPerusahaan
            tvLocation.text = lowongan.lokasi
            imgCompanyLogo.setImageResource(lowongan.logoPerusahaan)
        }

        // Menangani klik pada setiap item
        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, DetailLowonganActivity::class.java).apply {
                // Kirim data ke DetailLowonganActivity (opsional)
                putExtra("ID_LOWONGAN", lowongan.id)
            }
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = lowonganList.size

    // Fungsi untuk memperbarui data saat ada pencarian
    fun filterList(filteredList: List<Lowongan>) {
        lowonganList = filteredList
        notifyDataSetChanged()
    }
}
