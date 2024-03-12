package com.example.reservationproject.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.reservationproject.adapter.HotelItemAdapter
import com.example.reservationproject.databinding.FragmentProductBinding
import com.example.reservationproject.model.HotelElement
import com.example.reservationproject.repo.ServiceInterface
import com.example.reservationproject.service.ApiClient
import com.example.reservationproject.viewmodel.ProductViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductFragment : Fragment() , HotelItemAdapter.OnItemClickListener {

    private lateinit var binding: FragmentProductBinding
    private val viewModel: ProductViewModel by viewModels()

    private var recyclerView: RecyclerView? = null
    private var hotelItemAdapter: HotelItemAdapter? = null
    private var hotelItem = mutableListOf<HotelElement>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductBinding.inflate(inflater, container, false)

        when (arguments?.getString("param")) {
            "flight" -> viewModel.getAirline()
            "hotel" -> viewModel.getAllHotels()
            "tour" -> viewModel.getAllTours()
            else -> Log.e("hatalı param", "$arguments?.getString(\"param\"")
        }

        hotelItem = ArrayList()
        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.hotels.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = HotelItemAdapter(requireContext(), it ,this)
                binding.rv.adapter = adapter
            }
        }

        return binding.root
    }

    override fun onItemClick(position: Int) {
        Log.e("onitemclicked", position.toString())
    }
}