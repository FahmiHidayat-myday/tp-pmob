package com.naufal.tugas_proyek_pmob.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.naufal.tugas_proyek_pmob.R

class MapLokerActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map) // Menggunakan layout activity_map.xml

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Di sini Anda akan menerima koordinat dari Intent
        // Untuk sementara, kita gunakan lokasi contoh
        val lokasiPekerjaan = LatLng(-7.2575, 112.7521) // Contoh: Surabaya

        mMap.addMarker(MarkerOptions().position(lokasiPekerjaan).title("Lokasi Pekerjaan"))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lokasiPekerjaan, 15f))
    }
}
