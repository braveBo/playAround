package com.redfin.hw

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.redfin.hw.FoodTruckAdapter.Companion.ARG_FOOD_TRUCK
import com.redfin.hw.data.FoodTruck

class MapActivity : AppCompatActivity(), OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var mapView: MapView
    private lateinit var googleMap: GoogleMap
    private lateinit var viewModel: FoodTruckViewModel
    private lateinit var foodTrucks: List<FoodTruck>
    private var currentFoodTruck: FoodTruck? = null
    private var foodTruckDetailsDialog: BottomSheetDialogFragment? = null
    private var isLaunchFromIntent = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        viewModel = FoodTruckViewModelSingletonFactory.viewModel

        mapView = findViewById(R.id.mapView)
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        currentFoodTruck = intent.getParcelableExtra(ARG_FOOD_TRUCK)

        // prevent multiple bottom dialog
        if (savedInstanceState == null) {
            isLaunchFromIntent = true
        }
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        foodTrucks = getFoodTrucks()
        foodTrucks.forEach { foodTruck ->
            val position = LatLng(foodTruck.latitude, foodTruck.longitude)
            val markerOptions = MarkerOptions()
                .position(position)
                .title(foodTruck.applicant)
                .snippet(foodTruck.optionaltext)
            googleMap.addMarker(markerOptions)?.tag = foodTruck
        }

        // Move the camera to the target food truck's position
        currentFoodTruck?.let {
            val cameraPosition = CameraPosition.Builder()
                .target(LatLng(it.latitude, it.longitude))
                .zoom(15f)
                .build()
            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            if (isLaunchFromIntent)
                showBottomDialog(it)
        }

        googleMap.setOnMarkerClickListener(this)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        val foodTruck = marker.tag as FoodTruck
        showBottomDialog(foodTruck)
        return true
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    private fun showBottomDialog(foodTruck: FoodTruck?) {
        foodTruck?.let {
            foodTruckDetailsDialog?.dismiss()
            foodTruckDetailsDialog = FoodTruckDetailsDialogFragment.newInstance(it)
            foodTruckDetailsDialog!!.show(supportFragmentManager, "food_truck_details_dialog")
        }
    }

    private fun getFoodTrucks(): List<FoodTruck> {
        return viewModel.getCachedFoodTrucks()
    }

    class FoodTruckDetailsDialogFragment : BottomSheetDialogFragment() {
        companion object {

            fun newInstance(foodTruck: FoodTruck): FoodTruckDetailsDialogFragment {
                val args = Bundle()
                args.putParcelable(ARG_FOOD_TRUCK, foodTruck)
                val fragment = FoodTruckDetailsDialogFragment()
                fragment.arguments = args
                return fragment
            }
        }

        override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
            val view = inflater.inflate(R.layout.food_truck_item, container, false)
            val foodTruck = arguments?.getParcelable<FoodTruck>(ARG_FOOD_TRUCK)

            foodTruck?.let {
                val openTime = "${foodTruck.starttime} - ${foodTruck.endtime}"
                view.findViewById<TextView>(R.id.food_truck_name).text = foodTruck.applicant
                view.findViewById<TextView>(R.id.food_truck_address).text = foodTruck.location
                view.findViewById<TextView>(R.id.food_truck_description).text = foodTruck.optionaltext
                view.findViewById<TextView>(R.id.food_truck_opening_hours).text = openTime
            }
            return view
        }
    }
}