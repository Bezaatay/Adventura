package com.example.reservationproject.view.hotelViews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentRoomItemBinding
import com.example.reservationproject.utils.DatePicker
import com.example.reservationproject.viewmodel.RoomItemViewModel

class RoomItemFragment : Fragment() {
    private lateinit var binding: FragmentRoomItemBinding
    private val viewModel: RoomItemViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomItemBinding.inflate(inflater, container, false)

        viewModel.fetchRoomById(requireArguments().getLong("roomId"))

        viewModel.room.observe(viewLifecycleOwner) {
            binding.priceTxt.text = it.roomPrice.toString()+"₺"
            binding.roomTypeTxt.text = it.roomType + "Oda"
            Glide.with(requireContext())
                .load(it.roomImage)
                .into(binding.roomImage)
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

        binding.resRoomBtn.setOnClickListener {
            val roomId = requireArguments().getLong("roomId")
            val checkIn = startDateButton.text
            val checkOut = endDateButton.text


        }

        return binding.root
    }
}