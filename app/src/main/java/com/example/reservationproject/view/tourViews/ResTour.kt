package com.example.reservationproject.view.tourViews

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bezalibrary.service.Functions
import com.example.reservationproject.adapter.TourItemAdapter
import com.example.reservationproject.databinding.FragmentResTourBinding
import com.example.bezalibrary.service.model.TourElement
import com.example.reservationproject.R
import com.example.reservationproject.adapter.LocationSearchBarTourAdapter
import com.example.reservationproject.adapter.TourTypesAdapter
import com.example.reservationproject.viewmodel.ResTourViewModel

class ResTour : Fragment(), TourItemAdapter.OnTourItemClickListener,
    LocationSearchBarTourAdapter.OnLocationItemClickListener,
    TourTypesAdapter.OnTourTypeItemClickListener {
    private lateinit var binding: FragmentResTourBinding
    private val viewModel: ResTourViewModel by viewModels()
    private var tourItem = mutableListOf<TourElement>()
    private var selectedTourTypeId: Long? = null
    private var isTourType: Boolean? = null
    val functions = Functions()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentResTourBinding.inflate(inflater, container, false)

        tourItem = ArrayList()
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        binding.searchbar.layoutManager = LinearLayoutManager(requireContext())
        binding.searchbar2.layoutManager = LinearLayoutManager(requireContext())

        functions.getFeaturedTours()
        viewModel.featuredTours.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = TourItemAdapter(requireContext(), it, this)
                binding.rv.adapter = adapter
            }
        }

        viewModel.fetchLocationNames()
        viewModel.getTourTypes()

        val listView = binding.searchbar
        val listView2 = binding.searchbar2

        viewModel.tourLocations.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = LocationSearchBarTourAdapter(requireContext(), it, this)
                listView.adapter = adapter
            }
        }
        viewModel.tourTypes.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = TourTypesAdapter(requireContext(), it, this)
                listView2.adapter = adapter
            }
        }

        binding.searchText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.filterTourNames(s.toString())

                if (!binding.searchText.text.isNullOrEmpty()) {
                    binding.textView15.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.light_grey
                        )
                    )
                    binding.imageView19.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.grey_hiking
                        )
                    )
                    binding.searchText2.setHintTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.light_grey
                        )
                    )
                } else {
                    binding.textView15.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                    binding.imageView19.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.white_hiking
                        )
                    )
                    binding.searchText2.setHintTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )

                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
        binding.searchText2.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.filterTourTypes(s.toString())

                if (!binding.searchText2.text.isNullOrEmpty()) {
                    binding.textView14.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.light_grey
                        )
                    )
                    binding.imageView6.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.grey_destination
                        )
                    )
                    binding.searchText.setHintTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.light_grey
                        )
                    )
                } else {
                    binding.textView14.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                    binding.imageView6.setImageDrawable(
                        ContextCompat.getDrawable(
                            requireContext(),
                            R.drawable.white_destination
                        )
                    )
                    binding.searchText.setHintTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )

                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })



        binding.searchText.setOnClickListener {
            if (!binding.searchText2.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Ülke silinmelidir!", Toast.LENGTH_SHORT).show()
                binding.searchText.isFocusableInTouchMode = false

            } else {
                listView.visibility = View.VISIBLE
                listView2.visibility = View.INVISIBLE
                binding.searchText.isFocusableInTouchMode = true
            }
        }

        binding.searchText2.setOnClickListener {
            if (!binding.searchText.text.isNullOrEmpty()) {
                Toast.makeText(requireContext(), "Tur Tipi silinmelidir!", Toast.LENGTH_SHORT)
                    .show()
                binding.searchText2.isFocusableInTouchMode = false

            } else {
                listView2.visibility = View.VISIBLE
                listView.visibility = View.INVISIBLE
                binding.searchText2.isFocusableInTouchMode = true
            }
        }

        binding.constraint.setOnClickListener {
            listView.visibility = View.INVISIBLE
            listView2.visibility = View.INVISIBLE
        }
        binding.SearchTourBtn.setOnClickListener {

            listView2.visibility = View.VISIBLE
            listView.visibility = View.INVISIBLE

            if(binding.searchText.text.isNullOrEmpty() && binding.searchText2.text.isNullOrEmpty()){
                Toast.makeText(requireContext(), "Bir şeyler gir!", Toast.LENGTH_SHORT).show()
            }
            else{
                if (binding.searchText2.text.isNullOrEmpty() || (isTourType == false)) {

                    findNavController().navigate(
                        R.id.action_resTour_to_searchTourFragment, Bundle().apply {
                            putString("locationName", binding.searchText.text.toString())
                        })
                } else {

                    findNavController().navigate(
                        R.id.action_resTour_to_searchTourFragment, Bundle().apply {
                            selectedTourTypeId?.let { it1 -> putLong("selectedTourTypeId", it1) }
                        })
                }
            }
        }
        return binding.root
    }

    override fun onTourItemClick(position: Int, tourId: Long) {
        findNavController().navigate(R.id.action_resTour_to_tourItemFragment,
            Bundle().apply {
                putLong("tourId", tourId)
            })
    }

    override fun onLocationNameItemClick(position: Int, locationName: String) {
        binding.searchText.setText(locationName)
        binding.searchbar.visibility = View.INVISIBLE
        isTourType = false
        selectedTourTypeId = null
    }

    override fun onTourTypeNameItemClick(position: Int, tourTypeId: Long, tourTypeName: String) {
        binding.searchText2.setText(tourTypeName)
        binding.searchbar2.visibility = View.INVISIBLE
        selectedTourTypeId = tourTypeId
        isTourType = true
    }
}