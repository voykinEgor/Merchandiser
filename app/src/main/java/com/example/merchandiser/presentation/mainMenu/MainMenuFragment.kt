package com.example.merchandiser.presentation.mainMenu

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.merchandiser.LOG
import com.example.merchandiser.MerchApp
import com.example.merchandiser.R
import com.example.merchandiser.databinding.FragmentMainMenuBinding
import com.example.merchandiser.domain.TaskItem
import com.example.merchandiser.presentation.ViewModelFactory
import com.example.merchandiser.presentation.customTask.CustomTaskFragmentDirections
import com.example.merchandiser.presentation.mainMenu.recyclerViewAdapters.RecyclerViewAdapter
import com.google.android.gms.tasks.Task
import javax.inject.Inject
import kotlin.math.log

class MainMenuFragment : Fragment() {

    companion object{
        private const val USER_ID = "FDSFDS"
    }

    private var _binding: FragmentMainMenuBinding? = null
    private val binding get() = _binding!!

    private lateinit var sharedPreferences: SharedPreferences

    @Inject
    lateinit var viewmodelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewmodelFactory)[MainMenuViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as MerchApp).component
    }

    private lateinit var rvAdapter: RecyclerViewAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(USER_ID, Context.MODE_PRIVATE)
        val args = MainMenuFragmentArgs.fromBundle(requireArguments())
        rvAdapter = RecyclerViewAdapter()
        binding.recyclerViewTasks.adapter = rvAdapter
        showTasksList(args.userId)

        binding.logoutImageView.setOnClickListener {
            logout()
        }

    }

    private fun showTasksList(userId: Int){
        viewModel.getTasksList(userId)
        viewModel.tasksList.observe(requireActivity()) {tasksList ->

            rvAdapter.submitList(tasksList)
            rvAdapter.onTaskItemClickListener = {
                if (it.id == -1)
                    findNavController().navigate(R.id.action_mainMenuFragment2_to_customTaskFragment)
                else
                    findNavController().navigate(MainMenuFragmentDirections.actionMainMenuFragment2ToTaskFragment(it.id))
            }

        }
    }

    private fun logout(){
        sharedPreferences.edit().apply {
            remove(USER_ID)
            apply()
        }
        findNavController().popBackStack()
    }
}