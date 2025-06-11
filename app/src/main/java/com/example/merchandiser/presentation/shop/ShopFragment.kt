package com.example.merchandiser.presentation.shop

import android.content.Context
import androidx.fragment.app.viewModels
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.merchandiser.LOG
import com.example.merchandiser.MerchApp
import com.example.merchandiser.R
import com.example.merchandiser.data.models.transfer.CategoryItemTransfer
import com.example.merchandiser.databinding.FragmentShopBinding
import com.example.merchandiser.databinding.FragmentTaskBinding
import com.example.merchandiser.presentation.ViewModelFactory
import com.example.merchandiser.presentation.task.TaskFragmentArgs
import com.example.merchandiser.presentation.task.TaskViewModel
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
        setupTextViews()
        setupClickListeners()

    }

    private fun setupClickListeners(){
        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }

    }

    private fun setupTextViews(){
        val shopItem = args.shopItem
        val listCategories = args.categoryList.items

        binding.shopTextView.text = shopItem.name
        binding.addressTextView.text = "Адрес: ${shopItem.address}"
        binding.categoriesTextView.text = extractCategoriesName(listCategories)
    }

    private fun extractCategoriesName(listCategories: List<CategoryItemTransfer>): String {
        val categoryNames = listCategories.map { it.name.lowercase() }
        val categoriesString = categoryNames.joinToString(", ")
        return "Категории: $categoriesString"
    }
}