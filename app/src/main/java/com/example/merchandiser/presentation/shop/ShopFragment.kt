package com.example.merchandiser.presentation.shop

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.merchandiser.LOG
import com.example.merchandiser.MerchApp
import com.example.merchandiser.R
import com.example.merchandiser.databinding.FragmentShopBinding
import com.example.merchandiser.domain.CategoryInTasks
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.ShopsInTasks
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.presentation.CameraFragmentDirections
import com.example.merchandiser.presentation.Error
import com.example.merchandiser.presentation.ViewModelFactory
import com.example.merchandiser.presentation.shop.recyclerViewAdapters.categoryItemAdapter.RecyclerViewItemCategoryAdapter
import com.example.merchandiser.presentation.task.TaskFragmentDirections
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
    private lateinit var shopInTaskItem: ShopsInTasks

    private var permissionsGranted: Boolean = false

    private val taskId: Int by lazy {
        args.taskItem.id
    }

    private val taskItem: TaskItem by lazy {
        args.taskItem
    }


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
        shopInTaskItem = args.shopItem
        val listCategories = shopInTaskItem.categories
        Log.d(LOG, "List Categories: $listCategories")
        setupTextViews(shopInTaskItem, listCategories)
        setupClickListeners()
        setupRecyclerViews(listCategories)
        checkCameraPermission()


    }

    private fun setupClickListeners() {
        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.nextButton.setOnClickListener {
//            Log.d(LOG, "Task:  ShopItemId: ${shopInTaskItem.shopItem.id} \n ShopItem: ${shopInTaskItem.categories}")
            sendPhotos()
        }
    }

    private fun sendPhotos(){
        val categoryData = shopInTaskItem.categories.mapNotNull { category ->
            category.uriList?.let { uris -> category.category.id to uris.toList() }
        }

        viewModel.completeShop(taskId, shopInTaskItem.shopItem.id, categoryData)

        viewModel.loadingState.observe(viewLifecycleOwner) {isLoading ->
            binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.INVISIBLE
            if (isLoading){
                binding.nextButton.backgroundTintList = requireContext().getColorStateList(R.color.disactivated_color)
            }else{
                binding.nextButton.backgroundTintList = requireContext().getColorStateList(R.color.hint_color)
            }
            binding.nextButton.isEnabled = !isLoading
        }

        viewModel.loadingStateList.observe(viewLifecycleOwner) {stateList ->
            val errorMessages = stateList.filterIsInstance<Error>().map { it.message }
            if (errorMessages.isNotEmpty()) {
                showErrorDialog(errorMessages)
            }else{
                if (categoryData.isEmpty()){
                    showErrorDialog(listOf("Загрузите хотя бы одну фотографию"))
                }else{
                    showSuccessDialog()
                }

            }
        }
    }

    private fun showSuccessDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Успешно")
            .setMessage("Все данные были успешно отправлены.")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
                findNavController().navigate(ShopFragmentDirections.actionShopFragmentToTaskFragment(taskItem))
            }
            .show()
    }

    private fun showErrorDialog(errorMessages: List<String>) {
        val message = errorMessages.joinToString("\n") { "• $it" }

        AlertDialog.Builder(requireContext())
            .setTitle("Произошли ошибки")
            .setMessage(message)
            .setPositiveButton("ОК") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }


    private fun setupTextViews(
        shopInTaskItem: ShopsInTasks,
        listCategories: List<CategoryInTasks>
    ) {
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

        categoriesAdapter.onPhotoClick = { photo, category ->
            checkAndLaunchCamera(category)
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
            permissionsGranted = if (cameraGranted && photoGranted) {
                true
            } else {
                false
            }
        }
    }

    fun checkAndLaunchCamera(category: CategoryInTasks) {
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
            permissionsGranted = true
            initialCamera(category)
        } else {
            permissionsGranted = false
            requestCameraPermissionLauncher.launch(permissionsToRequest.toTypedArray())
        }
    }

    private fun initialCamera(category: CategoryInTasks) {
        if (permissionsGranted)
            findNavController().navigate(
                ShopFragmentDirections.actionShopFragmentToCameraFragment(
                    category,
                    shopInTaskItem,
                    taskItem
                )
            )
    }

}