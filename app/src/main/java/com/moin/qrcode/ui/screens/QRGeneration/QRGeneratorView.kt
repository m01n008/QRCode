package com.moin.qrcode.ui.screens.QRGeneration

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.get
import androidx.fragment.app.Fragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter
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
        fragmentQRGenBinding.genQR.setOnClickListener {
            generateQRCode()
        }
        return fragmentQRGenBinding.root


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _fragmentQRGenBinding = null
    }

fun  generateQRCode(){
    val content = fragmentQRGenBinding.qrInputText.text.toString()
    val qrCodeWriter = QRCodeWriter()
    val bitmapMatrix = qrCodeWriter.encode(content,BarcodeFormat.QR_CODE,512,512)
    val bitmapWidth = bitmapMatrix.width
    val bitmapHeight = bitmapMatrix.height
    val bitmap = Bitmap.createBitmap(bitmapWidth,bitmapHeight,Bitmap.Config.RGB_565)
    for (x in 0 until bitmapWidth){
        for (y in 0 until bitmapHeight){
            bitmap.setPixel(x, y, if (bitmapMatrix.get(x,y)) Color.BLACK else Color.WHITE)
        }
    }
    fragmentQRGenBinding.qrImageView.setImageBitmap(bitmap)

}


}