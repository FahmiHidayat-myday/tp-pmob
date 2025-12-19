package com.naufal.tugas_proyek_pmob.models // Buat package 'models' jika belum ada

data class Lowongan(
    val id: String,
    val judul: String,
    val namaPerusahaan: String,
    val lokasi: String,
    val logoPerusahaan: Int // Kita gunakan Int untuk resource drawable sementara
)
