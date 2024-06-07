package com.example.reservationproject.view.tourViews

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
import com.example.bezalibrary.service.model.TourElement
import com.example.reservationproject.R
import com.example.reservationproject.adapter.HotelItemAdapter
import com.example.reservationproject.adapter.TourItemAdapter
import com.example.reservationproject.databinding.FragmentSeeAllToursBinding
import com.example.reservationproject.viewmodel.SeeAllToursViewModel

class SeeAllToursFragment : Fragment(), TourItemAdapter.OnTourItemClickListener {

    private lateinit var binding: FragmentSeeAllToursBinding
    private val viewModel: SeeAllToursViewModel by viewModels()
    private var tourItem = mutableListOf<TourElement>()
    val functions = Functions()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSeeAllToursBinding.inflate(inflater, container, false)

        tourItem = ArrayList()
        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.featuredTours.observe(viewLifecycleOwner) {
            it.let {
                val adapter = it?.let { it1 -> TourItemAdapter(requireContext(), it1, this) }
                binding.rv.adapter = adapter
            }
        }
        return binding.root
    }

    override fun onTourItemClick(position: Int, tourId: Long) {
            findNavController().navigate(
                R.id.action_seeAllToursFragment_to_tourItemFragment,
                Bundle().apply {
                    putLong("tourId", tourId)
                })
    }
}