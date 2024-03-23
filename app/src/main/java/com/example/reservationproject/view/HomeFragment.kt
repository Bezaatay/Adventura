package com.example.reservationproject.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reservationproject.adapter.HotelItemAdapter
import com.example.reservationproject.databinding.FragmentHomeBinding
import com.example.reservationproject.model.HotelElement
import com.example.reservationproject.model.TourElement
import com.example.reservationproject.viewmodel.HomeViewModel


class HomeFragment : Fragment(), HotelItemAdapter.OnItemClickListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels()
    private var hotelItem = mutableListOf<HotelElement>()
    private var tourItem = mutableListOf<TourElement>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        hotelItem = ArrayList()
        binding.popHotelRV.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL, false)

        //viewModel.getFeaturedFlights()
        viewModel.getFeaturedHotels()

        viewModel.featuredHotels.observe(viewLifecycleOwner) {
            it.let {
                val adapter = it?.let { it1 -> HotelItemAdapter(requireContext(), it1, this) }
                binding.popHotelRV.adapter = adapter
            }
        }



        return binding.root
    }

    override fun onItemClick(position: Int) {
        Log.e("on item clicked", position.toString())
    }
}
