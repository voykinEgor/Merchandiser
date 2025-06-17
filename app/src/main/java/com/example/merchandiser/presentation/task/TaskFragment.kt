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
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.ShopsInTasks
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.presentation.ViewModelFactory
import com.example.merchandiser.presentation.task.recyclerViewAdapters.categories.RecyclerViewCategoriesAdapter
import com.example.merchandiser.presentation.task.recyclerViewAdapters.shops.RecyclerViewShopsAdapter
import javax.inject.Inject

class TaskFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel by lazy{
        ViewModelProvider(this, viewModelFactory)[TaskViewModel::class.java]
    }

    private var _binding: FragmentTaskBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<TaskFragmentArgs>()

    private val component by lazy {
        (requireActivity().application as MerchApp).component
    }

    private lateinit var categoriesAdapter: RecyclerViewCategoriesAdapter
    private lateinit var shopsAdapter: RecyclerViewShopsAdapter

    private lateinit var categoriesList: List<CategoryItem>

    private lateinit var taskItem: TaskItem
    private lateinit var shopsInTasksList: List<ShopsInTasks>




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
        taskItem = args.task
        binding.merchTextView.text = taskItem.name
        shopsInTasksList = viewModel.getShops(taskItem)
        setupCategoriesRV(taskItem)
        setupShopsRV()
        setupClickListeners()



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupClickListeners(){
        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }

        shopsAdapter.onItemClickListener = {shopInTask ->
            findNavController().navigate(TaskFragmentDirections.actionTaskFragmentToShopFragment(null, shopInTask))
        }
    }

    private fun setupCategoriesRV(task: TaskItem){
        categoriesAdapter = RecyclerViewCategoriesAdapter()
        binding.recyclerViewCategories.adapter = categoriesAdapter

        val listCategories = viewModel.getCategories(task).toList()
        categoriesList = listCategories
        categoriesAdapter.submitList(listCategories)

    }

    private fun setupShopsRV(){
        shopsAdapter = RecyclerViewShopsAdapter()
        binding.recyclerViewShops.adapter = shopsAdapter
        shopsAdapter.submitList(shopsInTasksList)
    }
}