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
import com.example.bezalibrary.service.Functions
import com.example.reservationproject.adapter.HotelItemAdapter
import com.example.reservationproject.databinding.FragmentSeeAllHotelBinding
import com.example.bezalibrary.service.model.HotelElement
import com.example.reservationproject.R
import com.example.reservationproject.viewmodel.SeeAllHotelViewModel

class SeeAllHotelFragment : Fragment(), HotelItemAdapter.OnHotelItemClickListener {

    private lateinit var binding : FragmentSeeAllHotelBinding
    private val viewModel: SeeAllHotelViewModel by viewModels()
    private var hotelItem = mutableListOf<HotelElement>()
    val functions = Functions()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeeAllHotelBinding.inflate(inflater, container, false)

        functions.getFeaturedHotels()

        hotelItem = ArrayList()
        binding.rv.layoutManager = LinearLayoutManager(requireContext())


        viewModel.featuredHotels.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = HotelItemAdapter(requireContext(), it, this,"SeeAllHotelFragment")
                binding.rv.adapter = adapter
            }
        }
        return binding.root
    }

    override fun onHotelItemClick(itemId: Long) {
        Log.e("ID",itemId.toString())
        findNavController().navigate(R.id.action_seeAllHotelFragment_to_hotelRoomFragment, Bundle().apply {
            putLong("itemId",itemId)
        })
    }

}