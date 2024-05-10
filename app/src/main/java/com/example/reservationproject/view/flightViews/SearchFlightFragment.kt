package com.example.reservationproject.view.flightViews

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bezalibrary.service.Functions
import com.example.reservationproject.adapter.FlightItem1Adapter
import com.example.reservationproject.databinding.FragmentSearchFlightBinding
import com.example.reservationproject.viewmodel.SearchFlightViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class SearchFlightFragment : Fragment(), FlightItem1Adapter.OnFlight1ItemClickListener {

    private lateinit var binding: FragmentSearchFlightBinding
    private val viewModel: SearchFlightViewModel by viewModels()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchFlightBinding.inflate(inflater, container, false)

        val airportId = requireArguments().getLong("airportId")
        val landingCity = requireArguments().getString("landingCity")
        val departureAirport = requireArguments().getString("departureAirport")
        val date = requireArguments().getString("date")

        binding.fromWhereTxt.text = departureAirport
        binding.toWhereTxt.text = landingCity
        binding.dateTxt.text = date?.let { formatDate(it) }

        if (date != null) {
            viewModel.fetchFlightsByAirportId(airportId, landingCity, date)
        }

        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.flights.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.cannotFindTxt.visibility = View.VISIBLE
            } else {
                val adapter = FlightItem1Adapter(requireContext(), it, this)
                binding.rv.adapter = adapter
            }
        }

        return binding.root
    }


    override fun onFlight1ItemClick(position: Int, flightIdd: Long) {
        Log.e("item id", flightIdd.toString())
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun formatDate(inputDate: String): String {
        val inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy", Locale.getDefault())
        val outputFormatter = DateTimeFormatter.ofPattern("dd MMMM", Locale.getDefault())

        return try {
            val date = LocalDate.parse(inputDate, inputFormatter)
            date.format(outputFormatter)
        } catch (e: Exception) {
            e.printStackTrace()
            ""
        }
    }
}