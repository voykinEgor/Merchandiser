package com.example.merchandiser.presentation.map

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.merchandiser.R
import com.example.merchandiser.databinding.FragmentMapBinding
import com.yandex.mapkit.MapKitFactory

class MapFragment : Fragment() {

    companion object {
        fun newInstance() = MapFragment()
    }

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MapViewModel by viewModels()

    private var isMapKitInitialized = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isMapKitInitialized) {
            MapKitFactory.setApiKey(resources.getString(R.string.MAPKIT_API_KEY))
            MapKitFactory.initialize(requireActivity())
            isMapKitInitialized = true
        }
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        binding.mapAndroid.onStart()
    }

    override fun onStop() {
        super.onStop()
        MapKitFactory.getInstance().onStop()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}