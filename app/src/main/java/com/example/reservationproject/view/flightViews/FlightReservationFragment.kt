package com.example.reservationproject.view.flightViews

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.bezalibrary.service.RetrofitClient
import com.example.bezalibrary.service.model.FlightRes
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
        val token = AppPref(requireContext()).getToken()
        Log.e("token", token.toString())
        if (token != null) {
            RetrofitClient.setAuthToken(token)
        }
        val totalPrice = requireArguments().getLong("totalPrice")
        val flightId = requireArguments().getLong("flightId")
        binding.payBtn.text = "Öde(" + totalPrice + "$)"
        binding.nameTxt.setText(appPref.getName())
        binding.surnameTxt.setText(appPref.getSurname())

        binding.ageTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val text = s.toString()
                if (text.isNotEmpty() && text.toInt() > 100) {
                    Toast.makeText(requireContext(), "Yaşınızı doğru giriniz", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        })

        binding.constraint.setOnClickListener {
            binding.seatImg.visibility = View.INVISIBLE
        }
        binding.showSeatsImg.setOnClickListener {
            binding.seatImg.visibility = View.VISIBLE
        }
        binding.payBtn.setOnClickListener {
            if (binding.nameTxt.text.isNullOrEmpty() ||
                binding.surnameTxt.text.isNullOrEmpty() ||
                binding.ageTxt.text.isNullOrEmpty() ||
                binding.mailTxt.text.isNullOrEmpty()
            ) {

                Toast.makeText(
                    requireContext(),
                    "Tüm alanları eksiksiz doldurduğunuza emin olunuz!",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                val newFlightRes = FlightRes(
                    binding.nameTxt.text.toString(),
                    binding.surnameTxt.text.toString(),
                    binding.mailTxt.text.toString(),
                    binding.phoneTxt.text.toString(),
                    flightId.toInt(),
                    binding.ageTxt.text.toString().toInt()
                )
                viewModel.createFlightReservation(newFlightRes)
            }
        }

        viewModel.isRes.observe(viewLifecycleOwner) {
            if (it == "Bir sorun oluştu!") {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            } else {
                findNavController().navigate(
                    R.id.action_flightReservationFragment_to_paymentFragment,
                    Bundle().apply {
                        putString("url", it.toString())
                        putString("typeOfTicket","Flight")
                    })
            }
        }

        return binding.root
    }
}



