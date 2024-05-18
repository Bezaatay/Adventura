package com.example.reservationproject.view.flightViews

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentFlightItemBinding
import com.example.reservationproject.manager.AppPref
import com.example.reservationproject.utils.DateFunctions
import com.example.reservationproject.view.LogRegActivity
import com.example.reservationproject.viewmodel.FlightItemViewModel

class FlightInfoItemFragment : Fragment() {
    private lateinit var binding: FragmentFlightItemBinding
    private val viewModel: FlightItemViewModel by viewModels()
    private var totalPrice: Long? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFlightItemBinding.inflate(inflater, container, false)
        val dateFunc = DateFunctions
        val token = AppPref(requireContext()).getToken()
        val flightId = requireArguments().getLong("flightId")
        viewModel.getFlight(flightId)
        viewModel.flight.observe(viewLifecycleOwner) {
            Glide.with(requireContext()).load(it.airlineImage).into(binding.airlinePhoto)
            binding.airlineNameTxt.text = it.airlineName
            binding.priceTxt.text = it.adultSeatPrice.toString() + " ₺"
            totalPrice = it.adultSeatPrice
            binding.arrivalTimeTxt.text = dateFunc.convertDateTimeToHourAndMinute(it.arrivalTime)
            binding.durationTxt.text = it.duration.toString() + " Saat"
            binding.departureTimeTxt.text =
                dateFunc.convertDateTimeToHourAndMinute(it.departureTime)
            binding.fullDepartureInfoTxt.text =
                dateFunc.convertDateToFullDate(it.departureTime) + ", " + it.departureCity
            binding.fullArrivalInfoTxt.text =
                dateFunc.convertDateToFullDate(it.arrivalTime) + ", " + it.landingCity
            binding.airportInfoTxt.text = it.airportName
        }

        binding.goOnBtn.setOnClickListener {
            if (token == null) {
                showLoginConfirmationDialog()
            } else {
                findNavController().navigate(
                    R.id.action_flightItemFragment_to_flightReservationFragment,
                    Bundle().apply {
                        putLong("flightId", flightId)
                        totalPrice?.let { it1 -> putLong("totalPrice", it1) }
                    })
            }
        }

        binding.imageView14.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }
        return binding.root
    }
    private fun showLoginConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Kulllanıcı Bilgisi Boş")
        builder.setMessage("Rezervasyon yapabilmek için kullanıcı girişi yapmanız gerekmektedir.")

        builder.setPositiveButton("Giriş Yap") { dialog, _ ->
            val intent = Intent(requireActivity(), LogRegActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}