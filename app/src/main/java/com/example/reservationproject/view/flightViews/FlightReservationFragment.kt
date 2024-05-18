package com.example.reservationproject.view.flightViews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentFlightReservationBinding
import com.example.reservationproject.manager.AppPref
import com.example.reservationproject.viewmodel.FlightReservationViewModel

class FlightReservationFragment : Fragment() {
    private lateinit var binding: FragmentFlightReservationBinding
    private val viewModel: FlightReservationViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlightReservationBinding.inflate(inflater, container, false)
        val appPref = AppPref(requireContext())
        val totalPrice = requireArguments().getLong("totalPrice")
        val flightId = requireArguments().getLong("flightId")
        binding.payBtn.text = "Öde(" + totalPrice + "TL)"
        binding.nameTxt.setText(appPref.getName())
        binding.surnameTxt.setText(appPref.getSurname())

        binding.payBtn.setOnClickListener {
            //stripe
        }

        return binding.root
    }
}