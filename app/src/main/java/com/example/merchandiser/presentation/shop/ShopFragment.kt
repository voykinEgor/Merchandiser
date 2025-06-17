package com.example.merchandiser.presentation.shop

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.merchandiser.MerchApp
import com.example.merchandiser.R
import com.example.merchandiser.databinding.FragmentShopBinding
import com.example.merchandiser.domain.CategoryInTasks
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.ShopsInTasks
import com.example.merchandiser.presentation.ViewModelFactory
import com.example.merchandiser.presentation.shop.recyclerViewAdapters.categoryItemAdapter.RecyclerViewItemCategoryAdapter
import javax.inject.Inject

class ShopFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy {
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
        val shopInTaskItem = args.shopItem
        val listCategories = shopInTaskItem.categories

        setupTextViews(shopInTaskItem, listCategories)
        setupClickListeners()
        setupRecyclerViews(listCategories)
        checkCameraPermission()


    }

    private fun setupClickListeners() {
        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun setupTextViews(shopInTaskItem: ShopsInTasks, listCategories: List<CategoryInTasks>) {
        binding.shopTextView.text = shopInTaskItem.shopItem.name
        binding.addressTextView.text = "Адрес: ${shopInTaskItem.shopItem.address}"
        binding.categoriesTextView.text = extractCategoriesName(listCategories.map { it.category })
    }

    private fun extractCategoriesName(listCategories: List<CategoryItem>): String {
        val categoryNames = listCategories.map { it.name.lowercase() }
        val categoriesString = categoryNames.joinToString(", ")
        return "Категории: $categoriesString"
    }

    private fun setupRecyclerViews(listCategories: List<CategoryInTasks>) {
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

        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                photoPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            permissionsToRequest.add(photoPermission)
        }
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                cameraPermission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
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