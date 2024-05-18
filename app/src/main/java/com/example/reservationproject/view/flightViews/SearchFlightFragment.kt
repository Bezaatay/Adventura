package com.example.reservationproject.view.flightViews

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reservationproject.R
import com.example.reservationproject.adapter.FlightItem1Adapter
import com.example.reservationproject.databinding.FragmentSearchFlightBinding
import com.example.reservationproject.utils.DateFunctions.convertStringToDate
import com.example.reservationproject.utils.DateFunctions.decreaseDateByOneDay
import com.example.reservationproject.utils.DateFunctions.formatDate
import com.example.reservationproject.utils.DateFunctions.formatDateToMountAndDay
import com.example.reservationproject.utils.DateFunctions.formatDateToString
import com.example.reservationproject.utils.DateFunctions.increaseDateByOneDay
import com.example.reservationproject.viewmodel.SearchFlightViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

class SearchFlightFragment : Fragment(), FlightItem1Adapter.OnFlight1ItemClickListener {

    private lateinit var binding: FragmentSearchFlightBinding
    private val viewModel: SearchFlightViewModel by viewModels()
    private var selectedDate: Date? = null

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

        if (date != null) {
            selectedDate = convertStringToDate(date)
            viewModel.fetchFlightsByAirportId(airportId, landingCity, date)
        } else {
            Toast.makeText(requireContext(), "Tarih seçiniz!", Toast.LENGTH_SHORT).show()
        }

        binding.fromWhereTxt.text = departureAirport
        binding.toWhereTxt.text = landingCity
        binding.dateTxt.text = date?.let { formatDate(it) }
        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.flights.observe(viewLifecycleOwner) {
            if (it.isNullOrEmpty()) {
                binding.cannotFindTxt.visibility = View.VISIBLE
                binding.rv.visibility = View.INVISIBLE
            } else {
                val adapter = FlightItem1Adapter(requireContext(), it, this)
                binding.rv.visibility = View.VISIBLE
                binding.rv.adapter = adapter
                binding.cannotFindTxt.visibility = View.INVISIBLE
            }
        }
        viewModel.progressBar.observe(viewLifecycleOwner){
            binding.progressBar.isVisible = it
        }
        binding.nextDayCons.setOnClickListener {
            val nextDay = increaseDateByOneDay(selectedDate!!)
            binding.dateTxt.text = formatDateToMountAndDay(nextDay)
            selectedDate = nextDay
            viewModel.fetchFlightsByAirportId(
                airportId,
                landingCity,
                formatDateToString(selectedDate!!)
            )
            Log.e("selected date",formatDateToString(selectedDate!!).toString())
        }
        binding.previousDayCons.setOnClickListener {
            val previousDay = decreaseDateByOneDay(selectedDate!!)
            binding.dateTxt.text = formatDateToMountAndDay(previousDay)
            selectedDate = previousDay
            viewModel.fetchFlightsByAirportId(
                airportId,
                landingCity,
                formatDateToString(selectedDate!!)
            )
        }
        binding.imageView14.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return binding.root
    }

    override fun onFlight1ItemClick(position: Int, flightId: Long) {
        findNavController().navigate(R.id.action_searchFlightFragment_to_flightItemFragment, Bundle().apply {
            putLong("flightId",flightId)
        })
    }
}