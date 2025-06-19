package com.example.merchandiser.presentation.customTask

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.merchandiser.MerchApp
import com.example.merchandiser.databinding.FragmentCustomTaskBinding
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.presentation.ViewModelFactory
import com.example.merchandiser.presentation.customTask.adapter.categories.AddedCategoriesAdapter
import com.example.merchandiser.presentation.customTask.adapter.categories.CategoriesAdapter
import com.example.merchandiser.presentation.customTask.bottomSheet.CategoriesBottomSheet
import com.example.merchandiser.presentation.customTask.bottomSheet.ShopBottomSheet
import javax.inject.Inject

class CustomTaskFragment : Fragment() {

    private var _binding: FragmentCustomTaskBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy{
        ViewModelProvider(this, viewModelFactory)[CustomTaskViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as MerchApp).component
    }

    private val _addedCategories = mutableListOf<CategoryItem>()
    private lateinit var addedCategoriesAdapter: AddedCategoriesAdapter

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

        viewModel.getAllShop()
        viewModel.getAllCategories()

        setupObservers()
        setupClickListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupObservers(){

    }

    private fun setupClickListeners(){
        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.shopCard.setOnClickListener{
            openStoresBottomSheet()
        }

        setupAddedStoresRecyclerView()
    }

    private fun openStoresBottomSheet() {
        val bottomSheet = ShopBottomSheet(viewModel.shopsList.value!!)
        bottomSheet.setOnStoreSelectedListener { selectedShop ->
            binding.textViewShop.text = selectedShop.name
            binding.addressTextView.text = selectedShop.address
            // Дополнительные действия с выбранным магазином
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