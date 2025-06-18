package com.example.merchandiser.presentation.auth

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.merchandiser.MerchApp
import com.example.merchandiser.R
import com.example.merchandiser.databinding.FragmentAuthBinding
import com.example.merchandiser.presentation.ViewModelFactory
import com.example.merchandiser.presentation.mainMenu.MainMenuViewModel
import kotlinx.serialization.EncodeDefault
import javax.inject.Inject

class AuthFragment : Fragment() {

    companion object{
        private const val USER_ID = "FDSFDS"
    }

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewmodelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewmodelFactory)[AuthViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as MerchApp).component
    }

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAuthBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireContext().getSharedPreferences(USER_ID, Context.MODE_PRIVATE)

        if (sharedPreferences.contains(USER_ID)){
            findNavController().navigate(R.id.action_authFragment_to_mainMenuFragment2)
        }

        binding.authButton.setOnClickListener {
            val login = binding.editTextEmailPhoneAuth.text.toString()
            val pass = binding.editTextPassAuth.text.toString()
            authUser(login, pass)
        }
    }

    fun authUser(login: String, password: String) {
        viewModel.auth(login, password)
        viewModel.authResponse.observe(requireActivity()) { id ->
            id?.let {
                sharedPreferences.edit().apply {
                    putInt(USER_ID, it)
                    apply()
                }
                findNavController().navigate(R.id.action_authFragment_to_mainMenuFragment2)
            }
        }

        if (!viewModel.errorMessage.value.isNullOrBlank()) {
            Toast.makeText(requireActivity(), viewModel.errorMessage.value, Toast.LENGTH_SHORT)
                .show()
        }
    }
}