package com.example.merchandiser.presentation.customTask

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.merchandiser.MerchApp
import com.example.merchandiser.databinding.FragmentCustomTaskBinding
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.ShopsInTasks
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.presentation.ViewModelFactory
import com.example.merchandiser.presentation.customTask.adapter.categories.AddedCategoriesAdapter
import com.example.merchandiser.presentation.customTask.bottomSheet.CategoriesBottomSheet
import com.example.merchandiser.presentation.customTask.bottomSheet.ShopBottomSheet
import javax.inject.Inject

class CustomTaskFragment : Fragment() {

    companion object{
        private const val USER_ID = "FDSFDS"
    }

    private var _binding: FragmentCustomTaskBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var sharedPreferences: SharedPreferences

    private val viewModel by lazy{
        ViewModelProvider(this, viewModelFactory)[CustomTaskViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as MerchApp).component
    }
    private var shopItem: ShopItem? = null
    private val _addedCategories = mutableListOf<CategoryItem>()
    private lateinit var addedCategoriesAdapter: AddedCategoriesAdapter

    private lateinit var taskItem: TaskItem
    private lateinit var shopInTaskItem: ShopsInTasks



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCustomTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(USER_ID, Context.MODE_PRIVATE)
        val userId = sharedPreferences.getInt(USER_ID, -1)

        viewModel.getAllShop()
        viewModel.getAllCategories()
        setupClickListeners(userId)
        observeViewModel()
    }

    private fun observeViewModel(){
        viewModel.taskCreationResult.observe(viewLifecycleOwner) {isSuccess ->
            if (isSuccess){
                findNavController().navigate(CustomTaskFragmentDirections.actionCustomTaskFragmentToShopFragment(shopInTaskItem, taskItem))
            }else{
                Toast.makeText(requireContext(), "Возникла ошибка при создании задания, требуется перезайти в приложение", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupClickListeners(userId: Int){
        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.shopCard.setOnClickListener{
            openStoresBottomSheet()
        }

        setupAddedStoresRecyclerView()

        binding.nextButton.setOnClickListener {
            checkFieldsAndCreateTask(userId)
        }

        binding.mapImageView.setOnClickListener {
            if (shopItem == null){
                Toast.makeText(requireContext(), "Сначала выберите магазин", Toast.LENGTH_SHORT).show()
            }else{
                findNavController().navigate(CustomTaskFragmentDirections.actionCustomTaskFragmentToMapFragment(arrayOf(shopItem)))
            }
        }
    }

    private fun checkFieldsAndCreateTask(userId: Int){
        if (shopItem == null || _addedCategories.isEmpty()){
            Toast.makeText(requireContext(), "Выберите категорию и магазин", Toast.LENGTH_SHORT).show()
        }else{
            shopInTaskItem = viewModel.createShopInTask(shopItem!!, _addedCategories)
            taskItem = viewModel.createTaskItem(shopInTaskItem, _addedCategories, userId)
        }

    }

    private fun openStoresBottomSheet() {
        val bottomSheet = ShopBottomSheet(viewModel.shopsList.value!!)
        bottomSheet.setOnStoreSelectedListener { selectedShop ->
            binding.textViewShop.text = selectedShop.name
            binding.addressTextView.text = selectedShop.address
            shopItem = selectedShop
        }

        bottomSheet.show(childFragmentManager, "StoresBottomSheet")
    }

    private fun openCategoriesBottomSheet() {
        val bottomSheet = CategoriesBottomSheet(viewModel.categoriesList.value!!)
        bottomSheet.setOnCategorySelectedListener { selectedCategory ->
//            binding.textViewShop.text = selectedShop.name
            _addedCategories.add(selectedCategory)
            addedCategoriesAdapter.notifyItemInserted(_addedCategories.size - 1)
            binding.recyclerViewCategories.smoothScrollToPosition(_addedCategories.size - 1)
        }

        bottomSheet.show(childFragmentManager, "CategoriesBottomSheet")
    }

    private fun setupAddedStoresRecyclerView() {

        addedCategoriesAdapter = AddedCategoriesAdapter(
            _addedCategories,
            onRemoveClick = { category ->
                // Удаление магазина
                val position = _addedCategories.indexOf(category)
                if (position != -1) {
                    _addedCategories.removeAt(position)
                    addedCategoriesAdapter.notifyItemRemoved(position)
                }
            },
            onAddClick = {
                openCategoriesBottomSheet()
            }
        )

        binding.recyclerViewCategories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = addedCategoriesAdapter
//            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        }
    }
}