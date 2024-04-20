package com.example.reservationproject.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bezalibrary.service.Functions
import com.example.reservationproject.adapter.HotelItemAdapter
import com.example.reservationproject.databinding.FragmentResHotelBinding
import com.example.reservationproject.model.HotelElement
import com.example.reservationproject.utils.DatePicker
import com.example.reservationproject.viewmodel.ResHotelViewModel
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class ResHotel : Fragment(), HotelItemAdapter.OnItemClickListener {

    private lateinit var binding: FragmentResHotelBinding
    private val viewModel: ResHotelViewModel by viewModels()

    private var hotelItemAdapter: HotelItemAdapter? = null
    private var hotelItem = mutableListOf<HotelElement>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResHotelBinding.inflate(inflater, container, false)

        hotelItem = ArrayList()
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        val functions = Functions()

        functions.getFeaturedHotels()


        viewModel.featuredHotels.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = HotelItemAdapter(requireContext(), it, this)
                binding.rv.adapter = adapter
            }
        }
        val startDateButton = binding.startDateButton
        val endDateButton = binding.endDateButton

        viewModel.startDate.observe(viewLifecycleOwner) { startDate ->
            startDateButton.text = startDate
        }

        viewModel.endDate.observe(viewLifecycleOwner) { endDate ->
            endDateButton.text = endDate
        }

        binding.startCons.setOnClickListener {
            DatePicker.DateUtil.showDatePicker(
                requireContext(),
                viewModel.startDate.value,
                viewModel::setStartDate
            )
        }

        binding.endCons.setOnClickListener {
            DatePicker.DateUtil.showDatePicker(
                requireContext(),
                viewModel.endDate.value,
                viewModel::setEndDate
            )
        }

        return binding.root
    }

    override fun onItemClick(position: Int) {
        Log.e("onitemclicked", position.toString())
    }
}