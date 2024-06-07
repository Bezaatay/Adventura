package com.example.reservationproject.view.hotelViews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reservationproject.R
import com.example.reservationproject.adapter.HotelItemAdapter
import com.example.reservationproject.databinding.FragmentSearchHotelBinding
import com.example.reservationproject.viewmodel.SearchHotelViewModel

class SearchHotelFragment : Fragment() , HotelItemAdapter.OnHotelItemClickListener{

    private lateinit var binding : FragmentSearchHotelBinding
    private val viewModel : SearchHotelViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchHotelBinding.inflate(inflater,container,false)

        viewModel.fetchHotels(requireArguments().getLong("locationId"))

        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.hotels.observe(viewLifecycleOwner){
            val adapter = HotelItemAdapter(requireContext(),it,this,"SearchHotelFragment")
            binding.rv.adapter = adapter
        }

        binding.imageView14.setOnClickListener {
            requireActivity().supportFragmentManager.popBackStack()
        }

        return binding.root
    }

    override fun onHotelItemClick(itemId: Long) {
        findNavController().navigate(R.id.action_searchHotelFragment_to_hotelRoomFragment, Bundle().apply {
            putLong("itemId",itemId)
        })
    }

}