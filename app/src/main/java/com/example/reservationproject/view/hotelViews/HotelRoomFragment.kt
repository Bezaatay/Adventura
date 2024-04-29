package com.example.reservationproject.view.hotelViews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bezalibrary.service.Functions
import com.example.bezalibrary.service.model.HotelRoomElement
import com.example.reservationproject.adapter.HotelRoomAdapter
import com.example.reservationproject.databinding.FragmentHotelRoomBinding
import com.example.reservationproject.viewmodel.HotelRoomViewModel

class HotelRoomFragment : Fragment() , HotelRoomAdapter.OnHotelRoomItemClickListener{

    private lateinit var binding : FragmentHotelRoomBinding
    private val viewModel : HotelRoomViewModel by viewModels()
    private var roomItem = mutableListOf<HotelRoomElement>()
    val functions = Functions()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHotelRoomBinding.inflate(inflater, container, false)

        viewModel.fetchRoomByHotelId(requireArguments().getLong("itemId"))

        roomItem = ArrayList()
        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.rooms.observe(viewLifecycleOwner){
            it?.let {
                val adapter = HotelRoomAdapter(requireContext(), it, this)
                binding.rv.adapter = adapter
            }
        }

        return binding.root
    }

    override fun onHotelRoomItemClick(position: Int, roomId: Long) {
        Log.e("ROOMID",roomId.toString())
    }

}