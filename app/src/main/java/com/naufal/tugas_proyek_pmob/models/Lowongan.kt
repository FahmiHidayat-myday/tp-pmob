package com.naufal.tugas_proyek_pmob.models

data class Lowongan(
    val id: String,
    val judul: String,
    val namaPerusahaan: String,
    val lokasi: String,
    val logoPerusahaan: Int, // Resource drawable
    val deskripsi: String, // Deskripsi pekerjaan
    val latitude: Double, // Untuk Google Maps
    val longitude: Double // Untuk Google Maps
)
