package com.carlos.whentorun.presentation.xml.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.carlos.whentorun.R
import com.carlos.whentorun.databinding.FragmentWeatherBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class WeatherFragment : Fragment(R.layout.fragment_weather) {

    private lateinit var viewModel: WeatherViewModel

    private var _binding: FragmentWeatherBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWeatherBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[WeatherViewModel::class.java]

        setUpRecyclerView()
        setUpMap()
        subscribeToObservers()
    }

    private fun setUpRecyclerView() {
        binding.rvWeatherList.apply {
            adapter = WeatherListAdapter()
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun setUpMap() {
        binding.mvWeather.apply {
            onCreate(null)
            onResume()
            getMapAsync { map ->
                map.setOnMapClickListener {
                    viewModel.updateLocation(it)
                }
            }
        }
    }

    private fun subscribeToObservers() {
        viewModel.viewState.observe(viewLifecycleOwner) { state ->
            when {
                state.showError -> {
                    showErrorDialog()
                    binding.pbLoading.isVisible = false
                    binding.rvWeatherList.isVisible = false
                }

                state.isLoading -> {
                    binding.pbLoading.isVisible = true
                    binding.rvWeatherList.isVisible = false
                }

                else -> {
                    binding.pbLoading.isVisible = false
                    binding.rvWeatherList.isVisible = true

                    (binding.rvWeatherList.adapter as WeatherListAdapter).weatherList =
                        state.weather

                    state.location?.let { location ->
                        updateMap(location, state.updateCameraPosition)
                    }
                }
            }
        }
    }

    private fun updateMap(
        location: LatLng,
        updateCameraPosition: Boolean
    ) {
        binding.mvWeather.getMapAsync { map ->
            map.clear()
            map.addMarker(MarkerOptions().position(location))
            if (updateCameraPosition)
                map.moveCamera(
                    CameraUpdateFactory.newCameraPosition(fromLatLngZoom(location, 10f))
                )
        }
    }

    private fun showErrorDialog() {
        AlertDialog.Builder(requireContext()).setTitle(getString(R.string.dialog_error_title))
            .setMessage(getString(R.string.dialog_error_description))
            .setNeutralButton(getString(R.string.dialog_error_ok)) { dialog, _ ->
                dialog.dismiss()
            }.create().show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}