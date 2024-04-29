package com.example.reservationproject.view.flightViews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.FlightElement
import com.example.bezalibrary.service.model.TourElement
import com.example.reservationproject.R
import com.example.reservationproject.adapter.FlightItemAdapter
import com.example.reservationproject.adapter.TourItemAdapter
import com.example.reservationproject.databinding.FragmentSeeAllFlightsBinding
import com.example.reservationproject.viewmodel.SeeAllFlightsViewModel

class SeeAllFlightsFragment : Fragment() , FlightItemAdapter.OnFlightItemClickListener {

    private lateinit var binding: FragmentSeeAllFlightsBinding
    private val viewModel : SeeAllFlightsViewModel by viewModels()
    private var flightItem = mutableListOf<FlightElement>()
    val functions = Functions()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       binding = FragmentSeeAllFlightsBinding.inflate(inflater,container,false)

        flightItem = ArrayList()
        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.featuredFlight.observe(viewLifecycleOwner) {
            it.let {
                val adapter = it?.let { it1 -> FlightItemAdapter(requireContext(), it1, this) }
                binding.rv.adapter = adapter
            }
        }

        return binding.root
    }
    override fun onFlightItemClick(position: Int, flightId: Long) {
        Log.e("flightId",flightId.toString())
    }
}