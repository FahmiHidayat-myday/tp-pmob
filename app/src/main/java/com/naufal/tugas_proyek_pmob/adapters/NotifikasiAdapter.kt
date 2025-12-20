package com.naufal.tugas_proyek_pmob.adapters

import android.content.res.ColorStateList
import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.naufal.tugas_proyek_pmob.R
import com.naufal.tugas_proyek_pmob.databinding.ItemNotifikasiBinding
import com.naufal.tugas_proyek_pmob.models.Notifikasi
import com.naufal.tugas_proyek_pmob.models.StatusLamaran

class NotifikasiAdapter(private val notifikasiList: List<Notifikasi>) :
    RecyclerView.Adapter<NotifikasiAdapter.NotifikasiViewHolder>() {

    inner class NotifikasiViewHolder(val binding: ItemNotifikasiBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotifikasiViewHolder {
        val binding = ItemNotifikasiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NotifikasiViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NotifikasiViewHolder, position: Int) {
        val notifikasi = notifikasiList[position]
        val context = holder.itemView.context

        holder.binding.apply {
            tvJobTitle.text = "${notifikasi.judulLowongan} di ${notifikasi.namaPerusahaan}"
            tvTimestamp.text = notifikasi.timestamp

            // Atur teks, warna, dan ikon berdasarkan status
            when (notifikasi.status) {
                StatusLamaran.DITERIMA -> {
                    tvStatus.text = "Lamaran Diterima"
                    tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_diterima))
                    imgStatusIcon.setImageResource(R.drawable.baseline_check_circle_24)
                    imgStatusIcon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.status_diterima))
                }
                StatusLamaran.DITOLAK -> {
                    tvStatus.text = "Lamaran Ditolak"
                    tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_ditolak))
                    imgStatusIcon.setImageResource(R.drawable.baseline_cancel_24)
                    imgStatusIcon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.status_ditolak))
                }
                StatusLamaran.DIPROSES -> {
                    tvStatus.text = "Lamaran Diproses"
                    tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_diproses))
                    imgStatusIcon.setImageResource(R.drawable.baseline_hourglass_top_24)
                    imgStatusIcon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.status_diproses))
                }
                StatusLamaran.DILIHAT -> {
                    tvStatus.text = "Lamaran Dilihat"
                    tvStatus.setTextColor(ContextCompat.getColor(context, R.color.status_dilihat))
                    imgStatusIcon.setImageResource(R.drawable.baseline_visibility_24)
                    imgStatusIcon.imageTintList = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.status_dilihat))
                }
            }
        }
    }

    override fun getItemCount(): Int = notifikasiList.size
}
