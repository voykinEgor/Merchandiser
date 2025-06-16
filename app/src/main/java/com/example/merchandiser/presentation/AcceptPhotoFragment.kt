package com.example.merchandiser.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.merchandiser.R
import com.example.merchandiser.databinding.FragmentAcceptPhotoBinding


class AcceptPhotoFragment : Fragment() {

    private var _binding: FragmentAcceptPhotoBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<AcceptPhotoFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAcceptPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photoUri = args.uri.toUri()
        binding.photoImageView.setImageURI(photoUri)

        binding.acceptButton.setOnClickListener {
            findNavController().navigate()
        }
    }
}