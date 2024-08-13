package com.moin.qrcode.ui.screens.QRScanner

import android.widget.Toast
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage

class QRCodeAnalyzer(private val onQRCodeScanned: (String) -> Unit): ImageAnalysis.Analyzer {
    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        val mediaImage = imageProxy.image
        val image = InputImage.fromMediaImage(mediaImage!!, imageProxy.imageInfo.rotationDegrees)
        val scanner = BarcodeScanning.getClient()
        scanner.process(image)
            .addOnSuccessListener { barCodes ->
                for (barCode in barCodes){
                    onQRCodeScanned(barCode.rawValue?: "")
                }
            }
            .addOnFailureListener {

            }
            .addOnCompleteListener {
                imageProxy.close()
            }
    }
}