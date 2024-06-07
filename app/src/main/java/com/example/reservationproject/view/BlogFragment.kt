package com.example.reservationproject.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bezalibrary.service.Functions
import com.example.reservationproject.adapter.BlogAdapter
import com.example.reservationproject.databinding.FragmentBlogBinding
import com.example.bezalibrary.service.model.TourElement
import com.example.reservationproject.R
import com.example.reservationproject.viewmodel.BlogViewModel

class BlogFragment : Fragment() ,BlogAdapter.OnBlogItemClickListener {

    private var _binding: FragmentBlogBinding? = null
    private val binding get() = _binding!!
    val functions = Functions()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val viewModel =
            ViewModelProvider(this)[BlogViewModel::class.java]

        _binding = FragmentBlogBinding.inflate(inflater, container, false)

        binding.rv.layoutManager = LinearLayoutManager(requireContext())

        viewModel.blogPosts.observe(viewLifecycleOwner) {
            it?.let {
                val adapter = BlogAdapter(requireContext(), it, this)
                binding.rv.adapter = adapter
            }
        }
        return binding.root
    }

    override fun onBlogItemClick(position: Int, blogTitle: String, blogImage: String, blogDesc: String) {
        findNavController().navigate(R.id.action_navigation_blog_to_blogItemFragment, Bundle().apply {
            putString("blogTitle",blogTitle)
            putString("blogImage",blogImage)
            putString("blogDesc",blogDesc)
        })
    }
}