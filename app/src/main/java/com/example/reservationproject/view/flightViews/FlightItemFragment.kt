package com.example.reservationproject.view.flightViews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentFlightItemBinding
import com.example.reservationproject.viewmodel.FlightItemViewModel

class FlightItemFragment : Fragment() {
    private lateinit var binding: FragmentFlightItemBinding
    private val viewModel: FlightItemViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlightItemBinding.inflate(inflater, container, false)



        return binding.root
    }
}