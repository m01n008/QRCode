package com.moin.qrcode.ui.screens.QRScanner

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.core.resolutionselector.ResolutionSelector
import androidx.camera.core.resolutionselector.ResolutionStrategy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.common.util.concurrent.ListenableFuture
import com.moin.qrcode.databinding.FragmentQrscannerBinding


class QRScannerView: Fragment() {
    private val TAG = "QRScannerView"

    private var _fragmentQrScannerBinding: FragmentQrscannerBinding? = null
    private val fragmentQrScannerBinding get() = _fragmentQrScannerBinding!!
    private lateinit var cameraProviderFuture: ListenableFuture<ProcessCameraProvider>
    private  var imageAnalysis: ImageAnalysis? = null
    private var previewView: PreviewView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentQrScannerBinding = FragmentQrscannerBinding.inflate(inflater,container, false)
        return fragmentQrScannerBinding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        previewView =  fragmentQrScannerBinding.qrScanPreview
        cameraPermissionCheck()
    }
    private fun cameraPermissionCheck(){
        val cameraPermissionRequestLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted: Boolean ->
                if (isGranted) {
                        setCameraListeners()
                }
                else {
                    Toast.makeText(context, "camera permission required to scan QRCode", Toast.LENGTH_SHORT).show()
                }
        }
        if (ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
            setCameraListeners()
        }
        else{
            cameraPermissionRequestLauncher.launch(Manifest.permission.CAMERA)
        }


    }

    private fun setCameraListeners(){
        cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            startCamera(cameraProvider)
        },ContextCompat.getMainExecutor(requireContext()))
    }

    private fun startCamera(processCameraProvider: ProcessCameraProvider){
        val preview = Preview.Builder()
            .build()
            .also {
                it.setSurfaceProvider(previewView?.surfaceProvider)
            }

        imageAnalysis = ImageAnalysis.Builder()
            .setResolutionSelector(ResolutionSelector.Builder().setResolutionStrategy(
                ResolutionStrategy.HIGHEST_AVAILABLE_STRATEGY).build())
            .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
            .build()

        imageAnalysis!!.setAnalyzer(ContextCompat.getMainExecutor(requireContext()),QRCodeAnalyzer{ result ->
            handleQRCodeResult(result)
        })
        val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

        try {
            processCameraProvider.unbindAll()
            processCameraProvider.bindToLifecycle(this, cameraSelector, preview, imageAnalysis)
        }
        catch (e: Exception){
            Log.e(TAG,"Camera usecase binding failed: ${e.message}");
        }

    }

    private fun handleQRCodeResult(result: String){
        fragmentQrScannerBinding.qrRetrievedText.text = result
        fragmentQrScannerBinding.qrRetrievedText.movementMethod = ScrollingMovementMethod()
    }
}