package com.example.reservationproject.view.tourViews

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.reservationproject.R
import com.example.reservationproject.adapter.TourItemAdapter
import com.example.reservationproject.databinding.FragmentSearchTourBinding
import com.example.reservationproject.viewmodel.SearchTourViewModel

class SearchTourFragment : Fragment(), TourItemAdapter.OnTourItemClickListener {
    private lateinit var binding: FragmentSearchTourBinding
    private val viewModel: SearchTourViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchTourBinding.inflate(inflater, container, false)
        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        val locationName =requireArguments().getString("locationName")
        val tourTypeId = requireArguments().getLong("selectedTourTypeId")
        
        if (locationName != null) {
           viewModel.fetchToursByLocationName(locationName)
        }
        if(tourTypeId.toInt() != 0){
            viewModel.getToursByTourId(tourTypeId)
        }

        viewModel.tours.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = TourItemAdapter(requireContext(), it, this)
                binding.rv.adapter = adapter
            }
        }
        viewModel.toursByTourId.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = TourItemAdapter(requireContext(), it, this)
                binding.rv.adapter = adapter
            }
        }

        return binding.root
    }

    override fun onTourItemClick(position: Int, tourId: Long) {
        findNavController().navigate(
            R.id.action_searchTourFragment_to_tourItemFragment,
            Bundle().apply {
                putLong("tourId", tourId)
            })
    }
}