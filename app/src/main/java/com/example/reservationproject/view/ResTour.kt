package com.example.reservationproject.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bezalibrary.service.Functions
import com.example.reservationproject.R
import com.example.reservationproject.adapter.FlightItemAdapter
import com.example.reservationproject.adapter.TourItemAdapter
import com.example.reservationproject.databinding.FragmentResTourBinding
import com.example.reservationproject.model.FlightElement
import com.example.reservationproject.model.TourElement
import com.example.reservationproject.viewmodel.ResFlightViewModel
import com.example.reservationproject.viewmodel.ResTourViewModel

class ResTour : Fragment(), TourItemAdapter.OnItemClickListener {
    private lateinit var binding: FragmentResTourBinding
    private val viewModel: ResTourViewModel by viewModels()
    private var tourItem = mutableListOf<TourElement>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResTourBinding.inflate(inflater, container, false)
        val functions = Functions()

        tourItem = ArrayList()
        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        functions.getFeaturedTours()

        viewModel.featuredTours.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = TourItemAdapter(requireContext(), it, this)
                binding.rv.adapter = adapter
            }
        }
        return binding.root
    }

    override fun onItemClick(position: Int) {
        Log.e("on item clicked", position.toString())
    }

}