package com.moin.qrcode.ui.adapters

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.moin.qrcode.ui.screens.QRGeneration.QRGeneratorView
import com.moin.qrcode.ui.screens.QRScanner.QRScannerView

class QRViewPagerAdapter(activity: AppCompatActivity): FragmentStateAdapter(activity) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
         return  when (position){
                0 -> QRGeneratorView()
                1 -> QRScannerView()
                else -> throw IllegalStateException()
            }




    }

}