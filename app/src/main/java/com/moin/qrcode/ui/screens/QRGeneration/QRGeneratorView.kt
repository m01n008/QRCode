package com.moin.qrcode.ui.screens.QRGeneration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.moin.qrcode.databinding.FragmentQrgeneratorBinding

class QRGeneratorView: Fragment() {
    private var _fragmentQRGenBinding: FragmentQrgeneratorBinding? = null
    private val fragmentQRGenBinding get() = _fragmentQRGenBinding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _fragmentQRGenBinding = FragmentQrgeneratorBinding.inflate(inflater,container, false)
        return fragmentQRGenBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentQRGenBinding = null
    }


}