package com.example.reservationproject.view.hotelViews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.bezalibrary.service.Functions
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentHotelItemBinding
import com.example.reservationproject.viewmodel.HotelItemViewModel

class HotelItemFragment : Fragment() {
    private lateinit var binding: FragmentHotelItemBinding
    private val viewModel: HotelItemViewModel by viewModels()
    val functions = Functions()
    private var hotelId: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHotelItemBinding.inflate(inflater, container, false)

        viewModel.fetchHotelById(requireArguments().getLong("itemId"))

        viewModel.featuredHotels.observe(viewLifecycleOwner) { hotel ->
            hotelId = hotel.id.toString()
            binding.hotelNameTxt.text = hotel.name
            binding.locationTxt.text = hotel.locationName
            binding.priceTxt.text = "${hotel.price} ${hotel.currencyName}"
            binding.ratingBar.rating = hotel.stars.toFloat()
            Glide.with(requireContext())
                .load(hotel.image)
                .into(binding.photoImgView)
        }

        binding.findRoomBtn.setOnClickListener {
            findNavController().navigate(R.id.action_itemFragment_to_hotelRoomFragment, Bundle().apply {
                hotelId?.let { it1 -> putLong("itemId", it1.toLong()) }
            })}
        return binding.root
    }
}