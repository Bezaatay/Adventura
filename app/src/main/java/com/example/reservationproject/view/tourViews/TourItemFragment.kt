package com.example.reservationproject.view.tourViews

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
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


        return binding.root
    }
}