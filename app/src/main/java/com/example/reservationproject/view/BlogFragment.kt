package com.example.reservationproject.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bezalibrary.service.Functions
import com.example.reservationproject.adapter.BlogAdapter
import com.example.reservationproject.databinding.FragmentBlogBinding
import com.example.bezalibrary.service.model.TourElement
import com.example.reservationproject.viewmodel.BlogViewModel

class BlogFragment : Fragment() ,BlogAdapter.OnBlogItemClickListener {

    private var _binding: FragmentBlogBinding? = null
    private val binding get() = _binding!!
    private var tourItem = mutableListOf<TourElement>()
    val functions = Functions()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this)[BlogViewModel::class.java]

        _binding = FragmentBlogBinding.inflate(inflater, container, false)

        tourItem = ArrayList()
        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        functions.getFeaturedTours()

        viewModel.blogPosts.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = BlogAdapter(requireContext(), it, this)
                binding.rv.adapter = adapter
            }
        }

        return binding.root
    }

    override fun onBlogItemClick(position: Int) {
        Log.e("blogitem clicked" , position.toString())

    }
}