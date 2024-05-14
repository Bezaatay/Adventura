package com.example.reservationproject.view.flightViews

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bezalibrary.service.Functions
import com.example.reservationproject.adapter.FlightItemAdapter
import com.example.reservationproject.databinding.FragmentResFlightBinding
import com.example.bezalibrary.service.model.FlightElement
import com.example.reservationproject.adapter.FlightSearchBarAdapter
import com.example.reservationproject.utils.DatePicker
import com.example.reservationproject.viewmodel.ResFlightViewModel

class ResFlight : Fragment(), FlightItemAdapter.OnFlightItemClickListener,
    FlightSearchBarAdapter.OnAirlineItemClickListener {

    private lateinit var binding: FragmentResFlightBinding
    private val viewModel: ResFlightViewModel by viewModels()

    private var flightItem = mutableListOf<FlightElement>()
    private var selectedFlightId: Long? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResFlightBinding.inflate(inflater, container, false)

        val functions = Functions()

        flightItem = ArrayList()
        binding.rv2.layoutManager = LinearLayoutManager(requireContext())
        functions.getAirport()

        viewModel.isLoadingF.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = it
        }

        viewModel.featuredFlights.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = FlightItemAdapter(requireContext(), it, this, "ResFlight")
                binding.rv2.adapter = adapter
            }
        }

        viewModel.fromWhereText.observe(viewLifecycleOwner) {
            binding.fromWhereEdttx.setText(it)
        }

        viewModel.toWhereText.observe(viewLifecycleOwner) {
            binding.toWhereEdttx.setText(it)
        }

        binding.SearchFlightBtn.setOnClickListener {
            val landingCity = binding.toWhereEdttx.text.toString()
            val airportId = selectedFlightId
            val selectedDate = binding.startDateButton.text

            findNavController().navigate(
                com.example.reservationproject.R.id.action_resFlight_to_searchFlightFragment,
                Bundle().apply {
                    if (airportId != null) {
                        putLong("airportId", airportId)
                        putString("departureAirport", binding.fromWhereEdttx.text.toString())
                        putString("landingCity", landingCity)
                        putString("date", selectedDate.toString())
                    }
                })
        }

        functions.getAirport()

        val listView = binding.searchbarRv

        flightItem = ArrayList()
        binding.searchbarRv.layoutManager = LinearLayoutManager(requireContext())
        functions.getFeaturedFlights()

        viewModel.airports.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = FlightSearchBarAdapter(requireContext(), it, this)
                binding.searchbarRv.adapter = adapter
            }
        }

        binding.fromWhereEdttx.setOnClickListener {
            listView.visibility = View.VISIBLE
        }

        binding.fromWhereEdttx.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.filterAirportNames(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })


        binding.constraint.setOnClickListener {
            listView.visibility = View.INVISIBLE
        }

        binding.travellerCons.setOnClickListener {
            DatePicker.DateUtil.showDatePicker(
                requireContext(),
                viewModel.travelDate.value,
                viewModel::setTravelDate
            )
        }
        viewModel.travelDate.observe(viewLifecycleOwner) { startDate ->
            binding.startDateButton.text = startDate
        }
        return binding.root
    }

    override fun onFlightItemClick(position: Int, flightId: Long) {
        Log.e("flightId", flightId.toString())
    }

    override fun onAirlineItemClick(position: Int, airlineId: Long, airlineName: String) {
        binding.fromWhereEdttx.setText(airlineName)
        binding.searchbarRv.visibility = View.INVISIBLE
        selectedFlightId = airlineId
    }
}