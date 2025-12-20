package com.naufal.tugas_proyek_pmob.models// Enum untuk merepresentasikan status lamaran dengan lebih aman
enum class StatusLamaran {
    DILIHAT,
    DIPROSES,
    DITERIMA,
    DITOLAK
}

data class Notifikasi(
    val id: String,
    val judulLowongan: String,
    val namaPerusahaan: String,
    val status: StatusLamaran,
    val timestamp: String // contoh: "2 hari yang lalu"
)
