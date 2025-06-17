package com.example.merchandiser.presentation

import android.app.Activity.RESULT_OK
import android.content.ContentValues
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.merchandiser.LOG
import com.example.merchandiser.R
import com.example.merchandiser.databinding.FragmentCameraBinding
import com.example.merchandiser.databinding.FragmentShopBinding
import com.example.merchandiser.domain.CategoryInTasks
import com.example.merchandiser.domain.ShopsInTasks

class CameraFragment : Fragment() {

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<CameraFragmentArgs>()

    private lateinit var imageCapture: ImageCapture

    private lateinit var shopInTaskItem: CategoryInTasks

    private val galleryOpen =
        registerForActivityResult(
            ActivityResultContracts.GetContent())
        { uri ->
            uri?.let {
                findNavController().navigate(CameraFragmentDirections.actionCameraFragmentToAcceptPhotoFragment(it.toString(), shopInTaskItem))
                Log.d(LOG, "Uri picked in gallery: $it")
            }
        }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        shopInTaskItem = args.categoryInShop
        launchCameraCapture()
        setupClickListeners()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }





    private fun setupClickListeners(){
        binding.imageCaptureButton.setOnClickListener {
            capturePhoto()
        }

        binding.galleryButton.setOnClickListener {
            galleryOpen.launch("image/*")
        }

        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }
    }

//    private fun finishCameraActivity(uri: Uri){
//        findNavController().popBackStack()
//        intent.putExtra("imageURI", uri.toString())
//        setResult(RESULT_OK, intent)
//        finish()
//    }

    private fun launchCameraCapture() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (e: Exception) {
                Log.e(LOG, "Camera use case failed", e)
            }
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun capturePhoto() {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "photo_${System.currentTimeMillis()}")
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/MyApp")
            }
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(requireContext().contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onError(exc: ImageCaptureException) {
                    Log.e(LOG, "Photo capture failed: ${exc.message}", exc)
                }

                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    findNavController().navigate(CameraFragmentDirections.actionCameraFragmentToAcceptPhotoFragment(output.savedUri.toString(), shopInTaskItem))
                    Log.d(LOG, "Photo capture succeeded: ${output.savedUri}")
                }
            }
        )
    }
}