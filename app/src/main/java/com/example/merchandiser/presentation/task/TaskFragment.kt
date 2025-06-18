package com.example.merchandiser.presentation.task

import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.merchandiser.MerchApp
import com.example.merchandiser.databinding.FragmentTaskBinding
import com.example.merchandiser.domain.CategoryItem
import com.example.merchandiser.domain.ShopsInTasks
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.isInternetAvailable
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


    private lateinit var connectivityManager: ConnectivityManager


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

        connectivityManager =
            requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        getLocatePermission()

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

        shopsAdapter.onItemClickListener = {
            findNavController().navigate(
                TaskFragmentDirections.actionTaskFragmentToShopFragment(
                    it,
                    taskItem
                )
            )
        }

        binding.mapActionButton.setOnClickListener {
            val listShops = shopsInTasksList.map { it.shopItem }
            if (listShops.isNotEmpty()) {
                if (isInternetAvailable(connectivityManager)) {
                    findNavController().navigate(
                        TaskFragmentDirections.actionTaskFragmentToMapFragment(
                            listShops.toTypedArray()
                        )
                    )
                } else {
                    Toast.makeText(
                        requireActivity(),
                        "Нет подключения к интернету",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                findNavController().navigate(
                    TaskFragmentDirections.actionTaskFragmentToMapFragment(null)
                )
                Toast.makeText(requireActivity(), "Данные еще не загружены", Toast.LENGTH_SHORT)
                    .show()
            }

        shopsAdapter.onItemClickListener = {shopInTask ->
            findNavController().navigate(TaskFragmentDirections.actionTaskFragmentToShopFragment(shopInTask, taskItem))
        }
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

        val task = args.task
        val listShops = viewModel.getShops(task)
        shopsAdapter.submitList(listShops)

    }

    private fun getLocatePermission() {
        if (ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireActivity(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(),
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                1
            )
        }
    }
}