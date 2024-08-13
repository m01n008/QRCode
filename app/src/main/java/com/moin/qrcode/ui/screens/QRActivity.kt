package com.moin.qrcode.ui.screens

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.moin.qrcode.R
import com.moin.qrcode.databinding.ActivityQrBinding
import com.moin.qrcode.ui.adapters.QRViewPagerAdapter

class QRActivity:AppCompatActivity() {

    private lateinit var binding: ActivityQrBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val qrViewPagerAdapter = QRViewPagerAdapter(this)
        binding.viewPager.adapter = qrViewPagerAdapter
        TabLayoutMediator(binding.qrTabLayout, binding.viewPager){tab, position ->
           tab.text = when (position) {
               0 -> "Generate QR"
               1 -> "Scan QR"
               else -> ""
           }
        }.attach()

    }
}