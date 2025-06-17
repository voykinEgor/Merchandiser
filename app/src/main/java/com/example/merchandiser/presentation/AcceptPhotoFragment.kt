package com.example.merchandiser.presentation

import android.net.Uri
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
import com.example.merchandiser.domain.ShopsInTasks


class AcceptPhotoFragment : Fragment() {

    private var _binding: FragmentAcceptPhotoBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<AcceptPhotoFragmentArgs>()

//    private lateinit var shopInTaskItem: ShopsInTasks

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
//        shopInTaskItem = args.shopsInTasks
        binding.photoImageView.setImageURI(photoUri)

        binding.acceptButton.setOnClickListener {
//            findNavController().navigate()
        }
    }

//    fun insertPhotoToList(photoUri: Uri, shopInTaskItem: ShopsInTasks){
//        val
//    }
}