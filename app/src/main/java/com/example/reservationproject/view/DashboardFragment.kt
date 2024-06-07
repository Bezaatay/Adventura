package com.example.reservationproject.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bezalibrary.service.RetrofitClient
import com.example.reservationproject.R
import com.example.reservationproject.adapter.FlightTicketAdapter
import com.example.reservationproject.adapter.HotelTicketAdapter
import com.example.reservationproject.adapter.TourTicketAdapter
import com.example.reservationproject.databinding.FragmentDashboardBinding
import com.example.reservationproject.manager.AppPref
import com.example.reservationproject.viewmodel.DashboardViewModel

class DashboardFragment : Fragment(),
    HotelTicketAdapter.OnBlogItemClickListener,
    FlightTicketAdapter.OnBlogItemClickListener,
    TourTicketAdapter.OnBlogItemClickListener {

    private lateinit var binding: FragmentDashboardBinding
    private val viewModel: DashboardViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val token = AppPref(requireContext()).getToken()
        if (token != null) {
            RetrofitClient.setAuthToken(token)
        }

        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getMyFlightReservations()

        val typeOfTicket = arguments?.getString("typeOfTicket")
        if (typeOfTicket != null) {
            when (typeOfTicket) {
                "Flight" -> flightMoves()
                "Hotel" -> hotelMoves()
                "Tour" -> tourMoves()
                else -> flightMoves()
            }
        } else {
            flightMoves()
        }

        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.isVisible = it
        }

        binding.flightTickets.setOnClickListener {
            flightMoves()
        }
        binding.hotelTickets.setOnClickListener {
            hotelMoves()
        }
        binding.tourTickets.setOnClickListener {
            tourMoves()
        }

        viewModel.flightTickets.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = FlightTicketAdapter(requireContext(), it, this)
                binding.rv.adapter = adapter
            }
        }
        viewModel.hotelTickets.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = HotelTicketAdapter(requireContext(), it, this)
                binding.rv.adapter = adapter
            }
        }
        viewModel.tourTickets.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = TourTicketAdapter(requireContext(), it, this)
                binding.rv.adapter = adapter
            }
        }

        return binding.root
    }

    private fun flightMoves() {
        binding.hotelTickets.setBackgroundResource(R.drawable.rounded_corner_with_white_bcg)
        binding.tourTickets.setBackgroundResource(R.drawable.rounded_corner_with_white_bcg)
        binding.flightTickets.setBackgroundResource(R.drawable.rounded_corner_with_blue_bcg)
        viewModel.getMyFlightReservations()
    }

    private fun hotelMoves() {
        binding.flightTickets.setBackgroundResource(R.drawable.rounded_corner_with_white_bcg)
        binding.hotelTickets.setBackgroundResource(R.drawable.rounded_corner_with_blue_bcg)
        binding.tourTickets.setBackgroundResource(R.drawable.rounded_corner_with_white_bcg)
        viewModel.getMyHotelReservations()
    }

    private fun tourMoves() {
        binding.flightTickets.setBackgroundResource(R.drawable.rounded_corner_with_white_bcg)
        binding.hotelTickets.setBackgroundResource(R.drawable.rounded_corner_with_white_bcg)
        binding.tourTickets.setBackgroundResource(R.drawable.rounded_corner_with_blue_bcg)
        viewModel.getMyTourReservations()
    }

    override fun onHotelTicketItemClick(position: Int) {
        // Implement your logic here
    }

    override fun onFlightTicketItemClick(position: Int) {
        // Implement your logic here
    }

    override fun onTourTicketItemClick(position: Int) {
        // Implement your logic here
    }
}
