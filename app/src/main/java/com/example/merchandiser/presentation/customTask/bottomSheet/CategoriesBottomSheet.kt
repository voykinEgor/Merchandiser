package com.example.merchandiser.presentation.customTask.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.merchandiser.databinding.BottomSheetCategoriesSelectionBinding
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.presentation.customTask.adapter.categories.CategoriesAdapter
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class CategoriesBottomSheet(
    private val categoryList: List<CategoryItem>
) : BottomSheetDialogFragment() {

    private var _binding: BottomSheetCategoriesSelectionBinding? = null
    private val binding get() = _binding!!

    private var onCategorySelected: ((CategoryItem) -> Unit)? = null

    private lateinit var adapter: CategoriesAdapter

    // Для передачи callback из Activity/Fragment
    fun setOnCategorySelectedListener(listener: (CategoryItem) -> Unit) {
        onCategorySelected = listener
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = BottomSheetCategoriesSelectionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CategoriesAdapter(categoryList) { selectedCategory ->
            onCategorySelected?.invoke(selectedCategory)
            dismiss()
        }

        binding.rvCategories.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@CategoriesBottomSheet.adapter // Устанавливаем адаптер
            setHasFixedSize(true) // Оптимизация, если все элементы одинакового размера
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}