package com.example.merchandiser.presentation.map

import android.app.PendingIntent
import android.content.Context
import android.content.IntentSender
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity.VIBRATOR_MANAGER_SERVICE
import androidx.appcompat.app.AppCompatActivity.VIBRATOR_SERVICE
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.merchandiser.LOG
import com.example.merchandiser.MerchApp
import com.example.merchandiser.R
import com.example.merchandiser.databinding.FragmentMapBinding
import com.example.merchandiser.domain.ShopItem
import com.example.merchandiser.domain.nearestNeighbor.NearestNeighborHamiltonianPath
import com.example.merchandiser.presentation.ViewModelFactory
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.geometry.Polyline
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.map.Map
import com.yandex.mapkit.map.MapObject
import com.yandex.mapkit.map.MapObjectCollection
import com.yandex.mapkit.map.MapObjectDragListener
import com.yandex.mapkit.map.PlacemarkMapObject
import com.yandex.mapkit.mapview.MapView
import com.yandex.runtime.image.ImageProvider
import javax.inject.Inject


class MapFragment : Fragment() {

    companion object {
        fun newInstance() = MapFragment()
        private const val REQUEST_CHECK_SETTINGS = 1020
        private const val STANDARD_ZOOM = 10f
        private const val BUTTON_CLICK_ZOOM = 15f

        private var isMapKitInitialized = false
    }

    private lateinit var mapObjects: MapObjectCollection
    private lateinit var sortedPoints: List<Point>

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<MapFragmentArgs>()

    @Inject
    lateinit var viewmodelFactory: ViewModelFactory

    private val viewModel by lazy {
        ViewModelProvider(this, viewmodelFactory)[MapViewModel::class.java]
    }

    private val component by lazy {
        (requireActivity().application as MerchApp).component
    }

    private var placemark: PlacemarkMapObject? = null

    private val locationSettingsLauncher = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { result ->
        when (result.resultCode) {
            android.app.Activity.RESULT_OK -> viewModel.onLocationSettingsSuccess()
            android.app.Activity.RESULT_CANCELED -> viewModel.onLocationSettingsDenied()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (!isMapKitInitialized) {
            MapKitFactory.setApiKey(resources.getString(R.string.MAPKIT_API_KEY))
            MapKitFactory.initialize(requireActivity())
            isMapKitInitialized = true
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
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
        viewModel.stopLocationUpdates()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupClickListeners()
    }

    private fun setupObservers() {
        viewModel.locationSettingsCheck.observe(requireActivity()) { state ->
            Log.d(LOG, "CHANGE: $state")
            if (state != null) {
                checkLocationSettings(state)
            } else {
                viewModel.checkLocationSettings()
            }
        }

        mapObjects = binding.mapAndroid.map.mapObjects.addCollection()

        val points = args.points

        var pointsList: ArrayList<Point> = ArrayList(listOf<Point>())
        if (!points.isNullOrEmpty()) {
            pointsList = points.map { point ->
                Point(point.latitude.toDouble(), point.longitude.toDouble())
            } as ArrayList<Point>
        }

        viewModel.locationState.observe(requireActivity()) { state ->
            Log.d(LOG, "CHANGE STATE: $state")
            when (state) {
                is MapViewModel.LocationState.Coordinates -> {
                    binding.floatingButtonPosition.isEnabled = true
                    sortedPoints = emptyList()
                    if (!points.isNullOrEmpty()) {
                        viewModelWithTask(pointsList, points, state.point)
                    } else {
                        viewModelWithoutTask(state.point)
                    }
                }

                is MapViewModel.LocationState.Disabled -> {
                    binding.floatingButtonPosition.isEnabled = false
                    if (!points.isNullOrEmpty()) {
                        addPointsToMap(pointsList, points)
                        val startPoint =
                            Point(points[0].latitude.toDouble(), points[0].longitude.toDouble())
                        moveCameraToPosition(binding.mapAndroid.map, startPoint, STANDARD_ZOOM)
                    }
                }

                is MapViewModel.LocationState.Enabled -> {
                    binding.floatingButtonPosition.isEnabled = true
                }

                is MapViewModel.LocationState.ResolutionRequired -> {
                    binding.floatingButtonPosition.isEnabled = false
                }

                else -> {
                    binding.floatingButtonPosition.isEnabled = false
                }
            }
        }
    }

    private fun setupClickListeners() {
        binding.backImageView.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.floatingButtonPlus.setOnClickListener {
            moveZoomUp(binding.mapAndroid)
        }

        binding.floatingButtonMinus.setOnClickListener {
            moveZoomDown(binding.mapAndroid)
        }

        binding.floatingButtonPosition.setOnClickListener {
            viewModel.locationState.observe(requireActivity()) { state ->
                if (state is MapViewModel.LocationState.Coordinates) {
                    placemark?.geometry = state.point
                    moveCameraToPosition(binding.mapAndroid.map, state.point, BUTTON_CLICK_ZOOM)
//                    if (placemark?.isDraggable == true){
//                        showBottomSheet(state.point)
//                    }
                }

            }
        }
    }

    private fun viewModelWithTask(
        pointsList: ArrayList<Point>,
        points: Array<ShopItem>,
        userLocation: Point
    ) {
//        binding.textViewInstruction.visibility = View.INVISIBLE

        placemark = mapObjects.addPlacemark(userLocation).apply {
            setIcon(ImageProvider.fromResource(requireActivity(), R.drawable.person_pin))
            isDraggable = false
        }
        pointsList.add(userLocation)
        val shortestPath = findingShortestPath(pointsList, userLocation)
        sortedPoints = shortestPath.map { index -> pointsList[index] }
        pointsList.remove(userLocation)


        moveCameraToPosition(binding.mapAndroid.map, userLocation, STANDARD_ZOOM)
        addPointsToMap(pointsList, points)
        addDashedLineBetweenPoints(sortedPoints)

    }

    private var dragListener: MapObjectDragListener? = null

    private fun viewModelWithoutTask(userLocation: Point) {
//        binding.textViewInstruction.visibility = View.VISIBLE

        placemark?.let {
            dragListener = null
            mapObjects.remove(it)
        }

        placemark = mapObjects.addPlacemark(userLocation).apply {
            setIcon(ImageProvider.fromResource(requireActivity(), R.drawable.place_16dp_red))
            isDraggable = true
        }
        moveCameraToPosition(binding.mapAndroid.map, userLocation, STANDARD_ZOOM)

        dragListener = object : MapObjectDragListener {
            override fun onMapObjectDragStart(p0: MapObject) {
                vibrate()
            }

            override fun onMapObjectDrag(p0: MapObject, p1: Point) {}

            override fun onMapObjectDragEnd(p0: MapObject) {
//                showBottomSheet(placemark!!.geometry)
            }
        }

        placemark?.setDragListener(dragListener!!)
//        showBottomSheet(placemark!!.geometry)

    }

    private fun checkLocationSettings(resolution: PendingIntent) {
        try {
            val intentSenderRequest = IntentSenderRequest.Builder(resolution).build()
            locationSettingsLauncher.launch(intentSenderRequest)
        } catch (e: IntentSender.SendIntentException) {
            Log.e(LOG, "$e")
        }
    }

    private fun moveZoomDown(mapView: MapView) {
        mapView.mapWindow.map.move(
            CameraPosition(
                mapView.mapWindow.map.cameraPosition.target,
                mapView.mapWindow.map.cameraPosition.zoom - 1,
                0f,
                0f
            ),
            Animation(Animation.Type.LINEAR, 0.2f),
            null
        )
    }

    private fun moveZoomUp(mapView: MapView) {
        mapView.mapWindow.map.move(
            CameraPosition(
                mapView.mapWindow.map.cameraPosition.target,
                mapView.mapWindow.map.cameraPosition.zoom + 1,
                0f,
                0f
            ),
            Animation(Animation.Type.LINEAR, 0.2f),
            null
        )
    }

    private fun moveCameraToPosition(map: Map, point: Point, zoom: Float) {
        val cameraCallback = object : Map.CameraCallback {
            override fun onMoveFinished(isFinished: Boolean) {
            }
        }

        map.move(
            CameraPosition(
                point,
                zoom,
                0f,
                0f,
            ),
            Animation(Animation.Type.LINEAR, 1f),
            cameraCallback
        )
    }

    private fun addPointsToMap(points: List<Point>, pointsEntityList: Array<ShopItem>) {
        for (i in 0 until points.size) {
            val placemark = mapObjects.addPlacemark(points[i])
            placemark.setIcon(
                ImageProvider.fromResource(
                    requireActivity(),
                    R.drawable.place_16dp_red
                )
            )
            placemark.userData = pointsEntityList[i].id
//            placemark.addTapListener(placemarkTapListener)
        }
    }

    private fun findingShortestPath(points: List<Point>, startPoint: Point): List<Int> {
        val graph = NearestNeighborHamiltonianPath.buildGraph(points)
        return NearestNeighborHamiltonianPath.nearestNeighborHamiltonianPath(
            graph,
            points,
            startPoint
        )
    }

    private fun vibrate() {
        val vibrator = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val vibratorManager =
                requireActivity().getSystemService(VIBRATOR_MANAGER_SERVICE) as VibratorManager
            vibratorManager.defaultVibrator
        } else {
            requireActivity().getSystemService(VIBRATOR_SERVICE) as Vibrator
        }
        vibrator.vibrate(VibrationEffect.createOneShot(50, VibrationEffect.DEFAULT_AMPLITUDE))

    }

    private fun addDashedLineBetweenPoints(points: List<Point>) {
        if (points.size >= 2) {
            val polyline = mapObjects.addPolyline(Polyline(points))
            polyline.setStrokeColor(R.color.black)// Цвет линии
            polyline.strokeWidth = 5f // Толщина линии
            polyline.setDashLength(10f) // Длина каждого пунктира
            polyline.setGapLength(5f) // Длина промежутка между пунктирами
        }
    }
}