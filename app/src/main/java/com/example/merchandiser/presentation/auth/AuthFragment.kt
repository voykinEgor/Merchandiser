package com.example.merchandiser.presentation.auth

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.merchandiser.R
import com.example.merchandiser.databinding.FragmentAuthBinding
import kotlin.math.log

class AuthFragment : Fragment() {

    private var _binding: FragmentAuthBinding? = null
    private val binding get() = _binding!!

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
        super.onViewCreated(view, savedInstanceState)

        binding.authButton.setOnClickListener {
            authUser(binding.editTextEmailPhoneAuth.text.toString(), binding.editTextPassAuth.text.toString())
        }
    }

    fun authUser(login: String, password: String){
        if (login == "111" && password == "111"){
            findNavController().navigate(AuthFragmentDirections.actionAuthFragmentToMainMenuFragment2(1))
        }
    }
}