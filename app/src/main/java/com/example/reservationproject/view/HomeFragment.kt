package com.example.reservationproject.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bezalibrary.service.Functions
import com.example.reservationproject.R
import com.example.reservationproject.adapter.FlightItemAdapter
import com.example.reservationproject.adapter.HotelItemAdapter
import com.example.reservationproject.adapter.TourItemAdapter
import com.example.bezalibrary.service.model.FlightElement
import com.example.bezalibrary.service.model.HotelElement
import com.example.bezalibrary.service.model.TourElement
import com.example.reservationproject.databinding.FragmentHomeBinding
import com.example.reservationproject.viewmodel.HomeViewModel


class HomeFragment : Fragment(), HotelItemAdapter.OnHotelItemClickListener,
    TourItemAdapter.OnTourItemClickListener, FlightItemAdapter.OnFlightItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private var hotelItem = mutableListOf<HotelElement>()
    private var tourItem = mutableListOf<TourElement>()
    private var flightItem = mutableListOf<FlightElement>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val functions = Functions()

        hotelItem = ArrayList()
        tourItem = ArrayList()
        flightItem = ArrayList()


        binding.popHotelRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.popTourRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        binding.popFlightRV.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)

        functions.getFeaturedFlights()
        functions.getFeaturedHotels()
        functions.getFeaturedTours()

        viewModel.featuredHotels.observe(viewLifecycleOwner) {
            it.let {
                val adapter =
                    it?.let { it1 -> HotelItemAdapter(requireContext(), it1, this, "HomeFragment") }
                binding.popHotelRV.adapter = adapter
            }
        }
        viewModel.featuredTours.observe(viewLifecycleOwner) {
            it.let {
                val adapter = it?.let { it1 -> TourItemAdapter(requireContext(), it1, this) }
                binding.popTourRV.adapter = adapter
            }
        }
        viewModel.featuredFlights.observe(viewLifecycleOwner) {
            it.let {
                val adapter = it?.let { it1 -> FlightItemAdapter(requireContext(), it1, this,"HomeFragment") }
                binding.popFlightRV.adapter = adapter
            }
        }
        binding.seeAllHotel.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_seeAllHotelFragment)
        }
        binding.seeAllTours.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_seeAllToursFragment)
        }
        binding.seeAllFlights.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_home_to_seeAllFlightsFragment)
        }
        return binding.root
    }


    override fun onHotelItemClick(itemId: Long) {
        findNavController().navigate(R.id.action_navigation_home_to_itemFragment, Bundle().apply {
            putLong("itemId", itemId)
        })
    }

    override fun onTourItemClick(position: Int, tourId: Long) {
        findNavController().navigate(
            R.id.action_navigation_home_to_tourItemFragment,
            Bundle().apply {
                putLong("tourId", tourId)
            })
    }

    override fun onFlightItemClick(position: Int, flightId: Long) {
        findNavController().navigate(R.id.action_navigation_home_to_flightItemFragment,
            Bundle().apply {
                putLong("flightId", flightId)
            })
    }

}
