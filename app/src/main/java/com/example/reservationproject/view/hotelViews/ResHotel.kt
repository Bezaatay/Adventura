package com.example.reservationproject.view.hotelViews

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.example.reservationproject.databinding.FragmentResHotelBinding
import com.example.bezalibrary.service.model.HotelElement
import com.example.reservationproject.R
import com.example.reservationproject.adapter.LocationSearchBarAdapter
import com.example.reservationproject.viewmodel.ResHotelViewModel

class ResHotel : Fragment(), HotelItemAdapter.OnHotelItemClickListener,
    LocationSearchBarAdapter.OnLocationItemClickListener {

    private lateinit var binding: FragmentResHotelBinding
    private val viewModel: ResHotelViewModel by viewModels()
    private var hotelItem = mutableListOf<HotelElement>()
    private var selectedLocationId: Long? = null

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
                val adapter = HotelItemAdapter(requireContext(), it, this, "HomeFragment")
                binding.rv.adapter = adapter
            }
        }

        val listView = binding.searchbarRv

        binding.searchbarRv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.fetchLocation()

        viewModel.locations.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = LocationSearchBarAdapter(requireContext(), it, this)
                binding.searchbarRv.adapter = adapter
            }
            binding.searchText.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    viewModel.filterLocationNames(s.toString())
                }

                override fun afterTextChanged(s: Editable?) {}
            })

        }
        binding.constraint.setOnClickListener {
            listView.visibility = View.INVISIBLE
        }
        binding.searchText.setOnClickListener {
            listView.visibility = View.VISIBLE
        }
        binding.SearchHotelBtn.setOnClickListener {
            val locationId = selectedLocationId
            findNavController().navigate(R.id.action_resHotel_to_searchHotelFragment,Bundle().apply {
                if(locationId != null){
                    selectedLocationId?.let { it1 -> putLong("locationId", it1) }
                }
            })
        }
        return binding.root
    }

    override fun onHotelItemClick(itemId: Long) {
        findNavController().navigate(R.id.action_resHotel_to_itemFragment, Bundle().apply {
            putLong("itemId", itemId)
            Log.e("itemid",itemId.toString())
        })
    }

    override fun onLocationNameItemClick(position: Int, locationId: Long, locationName: String) {
        binding.searchText.setText(locationName)
        binding.searchbarRv.visibility = View.INVISIBLE
        selectedLocationId = locationId
    }
}