package com.example.reservationproject.view.tourViews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.reservationproject.databinding.FragmentTourItemBinding
import com.example.reservationproject.viewmodel.TourItemViewModel

class TourItemFragment : Fragment() {

private lateinit var binding : FragmentTourItemBinding
private val viewModel : TourItemViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTourItemBinding.inflate(inflater, container, false)

        viewModel.getTourById(requireArguments().getLong("tourId"))
        viewModel.tour.observe(viewLifecycleOwner){
            binding.tourNameTxt.text = it.name
            binding.locationTxt.text = it.location
            binding.descriptionTxt.text = it.description
            binding.priceTxt.text = "${it.tourAdultPrice} ${it.currencyName}"
            binding.babyPriceTxt.text = "${it.tourChildPrice} ${it.currencyName}"
            binding.ratingBar.rating = it.stars.toFloat()
            Glide.with(requireContext())
                .load(it.image)
                .into(binding.photoImgView)
        }
        return binding.root
    }
}