package com.example.merchandiser.presentation.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.findNavController
import com.example.merchandiser.MerchApp
import com.example.merchandiser.R
import com.example.merchandiser.databinding.FragmentAuthBinding
import com.example.merchandiser.presentation.mainMenu.MainMenuFragmentArgs
import com.example.merchandiser.presentation.mainMenu.MainMenuFragmentDirections
import javax.inject.Inject

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModel: AuthViewModel

    private val component by lazy {
        (requireActivity().application as MerchApp).component
    }

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        component.inject(this)
        super.onViewCreated(view, savedInstanceState)

        binding.authButton.setOnClickListener {
            val login = binding.editTextEmailPhoneAuth.text.toString()
            val pass = binding.editTextPassAuth.text.toString()
            authUser(login, pass)

        }
    }

    fun authUser(login: String, password: String) {
        viewModel.auth(login, password)
        viewModel.authResponse.observe(requireActivity()){id ->
            id?.let { findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToMainMenuFragment2(it)) }
        }

        if (!viewModel.errorMessage.value.isNullOrBlank()){
            Toast.makeText(requireActivity(), viewModel.errorMessage.value, Toast.LENGTH_SHORT).show()
        }
    }
}