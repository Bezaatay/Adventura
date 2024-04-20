package com.example.reservationproject.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bezalibrary.service.Functions
import com.example.reservationproject.R
import com.example.reservationproject.adapter.FlightItemAdapter
import com.example.reservationproject.databinding.FragmentResFlightBinding
import com.example.reservationproject.model.FlightElement
import com.example.reservationproject.utils.DatePicker
import com.example.reservationproject.viewmodel.ResFlightViewModel

class ResFlight : Fragment(), FlightItemAdapter.OnItemClickListener {

    private lateinit var binding: FragmentResFlightBinding
    private val viewModel: ResFlightViewModel by viewModels()

    private var flightItem = mutableListOf<FlightElement>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResFlightBinding.inflate(inflater, container, false)

        val adultNumEdttx = binding.adultNumEdttx
        val childNumEdttx = binding.childNumEdttx
        val babyNumEdttx = binding.babyNumEdttx
        val adultAdd = binding.adultAdd
        val childAdd = binding.childAdd
        val babyAdd = binding.babyAdd
        val adultMinus = binding.adultMinus
        val childMinus = binding.childMinus
        val babyMinus = binding.babyMinus

        val functions = Functions()

        flightItem = ArrayList()
        binding.rv2.layoutManager = LinearLayoutManager(requireContext())
        functions.getFeaturedFlights()

        viewModel.featuredFlights.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = FlightItemAdapter(requireContext(), it, this)
                binding.rv2.adapter = adapter
            }
        }

        viewModel.fromWhereText.observe(viewLifecycleOwner) {
            binding.fromWhereEdttx.setText(it)
        }

        viewModel.toWhereText.observe(viewLifecycleOwner) {
            binding.toWhereEdttx.setText(it)
        }
        viewModel.travelDate.observe(viewLifecycleOwner) { startDate ->
            binding.startDateButton.text = startDate
        }

        binding.changeRotationBtn.setOnClickListener {
            viewModel.swapTexts(binding.fromWhereEdttx.text, binding.toWhereEdttx.text)
        }

        viewModel.adultNumber.observe(viewLifecycleOwner) { number ->
            adultNumEdttx.text = number.toString()
        }

        viewModel.childNumber.observe(viewLifecycleOwner) { number ->
            childNumEdttx.text = number.toString()
        }

        viewModel.babyNumber.observe(viewLifecycleOwner) { number ->
            babyNumEdttx.text = number.toString()
        }

        viewModel.selectedFlightType.observe(viewLifecycleOwner) { selectedFlightType ->
            Log.e("FLIGHT TYPE", selectedFlightType)
        }
        binding.travellerCons.setOnClickListener {
            DatePicker.DateUtil.showDatePicker(
                requireContext(),
                viewModel.travelDate.value,
                viewModel::setTravelDate
            )
        }
        binding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            val selectedRadioButton = binding.root.findViewById<RadioButton>(checkedId)
            val selectedFlightType = selectedRadioButton.text.toString()

            viewModel.setSelectedFlightType(selectedFlightType)
        }

        binding.SearchFlightBtn.setOnClickListener {
            val fromWhere = binding.fromWhereEdttx.text
            val toWhere = binding.toWhereEdttx.text
            val departureTime = binding.startDateButton.text
            val adultSeat = binding.adultNumEdttx.text
            val childSeat = binding.childNumEdttx.text
            val babySeat = binding.babyNumEdttx.text
            val flightType = viewModel.selectedFlightType.value
            Log.e("flight info", "$fromWhere,$toWhere,$departureTime,$babySeat,$flightType")
        }

        adultAdd.setOnClickListener {
            viewModel.incrementAdultNumber()
        }

        adultMinus.setOnClickListener {
            viewModel.decrementAdultNumber()
        }

        childAdd.setOnClickListener {
            viewModel.incrementChildNumber()
        }

        childMinus.setOnClickListener {
            viewModel.decrementChildNumber()
        }

        babyAdd.setOnClickListener {
            viewModel.incrementBabyNumber()
        }

        babyMinus.setOnClickListener {
            viewModel.decrementBabyNumber()
        }

        return binding.root
    }

    override fun onItemClick(position: Int) {
        Log.e("on item clicked", position.toString())
    }
}