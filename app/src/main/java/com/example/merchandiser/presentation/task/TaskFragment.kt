package com.example.merchandiser.presentation.task

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.merchandiser.MerchApp
import com.example.merchandiser.databinding.FragmentTaskBinding
import com.example.merchandiser.presentation.ViewModelFactory
import com.example.merchandiser.presentation.task.recyclerViewAdapters.categories.RecyclerViewCategoriesAdapter
import com.example.merchandiser.presentation.task.recyclerViewAdapters.shops.RecyclerViewShopsAdapter
import javax.inject.Inject

class TaskFragment : Fragment() {

    companion object {
        fun newInstance() = TaskFragment()
    }

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy{
        ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]
    }

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<TaskFragmentArgs>()

    private lateinit var categoriesAdapter: RecyclerViewCategoriesAdapter
    private lateinit var shopsAdapter: RecyclerViewShopsAdapter

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
        _binding = FragmentTaskBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCategoriesRV()
        setupShopsRV()

        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupCategoriesRV(){
        categoriesAdapter = RecyclerViewCategoriesAdapter()
        binding.recyclerViewCategories.adapter = categoriesAdapter

        val task = args.task
        val listCategories = viewModel.getCategories(task).toList()
        categoriesAdapter.submitList(listCategories)

    }

    private fun setupShopsRV(){
        shopsAdapter = RecyclerViewShopsAdapter()
        binding.recyclerViewShops.adapter = shopsAdapter

        val task = args.task
        val listShops = viewModel.getShops(task)
        shopsAdapter.submitList(listShops)

    }
}