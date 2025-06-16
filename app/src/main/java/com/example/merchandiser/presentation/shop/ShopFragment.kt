package com.example.merchandiser.presentation.shop

import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.appcompat.app.AlertDialog
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.merchandiser.LOG
import com.example.merchandiser.MerchApp
import com.example.merchandiser.R
import com.example.merchandiser.data.models.transfer.CategoryItemTransfer
import com.example.merchandiser.data.models.transfer.ShopItemTransfer
import com.example.merchandiser.databinding.FragmentShopBinding
import com.example.merchandiser.presentation.ViewModelFactory
import com.example.merchandiser.presentation.shop.recyclerViewAdapters.attachPhotoAdapter.RecyclerViewPhotoAdapter
import com.example.merchandiser.presentation.shop.recyclerViewAdapters.categoryItemAdapter.RecyclerViewItemCategoryAdapter
import java.util.jar.Manifest
import javax.inject.Inject
import kotlin.getValue

class ShopFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy{
        ViewModelProvider(this, viewModelFactory)[ShopViewModel::class.java]
    }

    private var _binding: FragmentShopBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<ShopFragmentArgs>()

    private val component by lazy {
        (requireActivity().application as MerchApp).component
    }

    private lateinit var categoriesAdapter: RecyclerViewItemCategoryAdapter
    private lateinit var requestCameraPermissionLauncher: ActivityResultLauncher<Array<String>>





    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentShopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val shopItem = args.shopItem
        val listCategories = args.categoryList.items
        setupTextViews(shopItem, listCategories)
        setupClickListeners()
        setupRecyclerViews(listCategories)
        checkCameraPermission()

    }

    private fun setupClickListeners(){
        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun setupTextViews(shopItem: ShopItemTransfer, listCategories: List<CategoryItemTransfer>){
        binding.shopTextView.text = shopItem.name
        binding.addressTextView.text = "Адрес: ${shopItem.address}"
        binding.categoriesTextView.text = extractCategoriesName(listCategories)
    }

    private fun extractCategoriesName(listCategories: List<CategoryItemTransfer>): String {
        val categoryNames = listCategories.map { it.name.lowercase() }
        val categoriesString = categoryNames.joinToString(", ")
        return "Категории: $categoriesString"
    }

    private fun setupRecyclerViews(listCategories: List<CategoryItemTransfer>){
        categoriesAdapter = RecyclerViewItemCategoryAdapter()
        binding.recyclerViewCategories.adapter = categoriesAdapter
        categoriesAdapter.submitList(listCategories)

        categoriesAdapter.photoAdapter.onItemClickListener = {
            checkAndLaunchCamera()
        }
    }

    private fun checkCameraPermission() {
        requestCameraPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            val cameraGranted = permissions[android.Manifest.permission.CAMERA] == true
            val photoGranted = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                permissions[android.Manifest.permission.READ_MEDIA_IMAGES] == true
            } else {
                permissions[android.Manifest.permission.READ_EXTERNAL_STORAGE] == true
            }
            if (cameraGranted && photoGranted) {
                initialCamera()
            } else {
                checkAndLaunchCamera()
            }
        }
    }



    fun checkAndLaunchCamera() {
        val cameraPermission = android.Manifest.permission.CAMERA
        val photoPermission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            android.Manifest.permission.READ_MEDIA_IMAGES
        } else {
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        }

        val permissionsToRequest = mutableListOf<String>()

        if (ContextCompat.checkSelfPermission(requireActivity(), photoPermission) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(photoPermission)
        }
        if (ContextCompat.checkSelfPermission(requireActivity(), cameraPermission) != PackageManager.PERMISSION_GRANTED) {
            permissionsToRequest.add(cameraPermission)
        }

        if (permissionsToRequest.isEmpty()) {
            initialCamera()
        } else {
            requestCameraPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }

    private fun initialCamera() {
        findNavController().navigate(R.id.action_shopFragment_to_cameraFragment)
    }

}