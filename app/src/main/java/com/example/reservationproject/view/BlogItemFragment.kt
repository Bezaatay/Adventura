package com.example.reservationproject.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentBlogItemBinding
import com.example.reservationproject.viewmodel.BlogItemViewModel

class BlogItemFragment : Fragment() {
    private lateinit var binding: FragmentBlogItemBinding
    private val viewModel: BlogItemViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBlogItemBinding.inflate(inflater, container, false)

        Glide.with(requireContext()).load(requireArguments().getString("blogImage"))
            .into(binding.imagePhoto)
        binding.location.text = requireArguments().getString("blogTitle")
        binding.descriptionTxt.text = requireArguments().getString("blogDesc")
        return binding.root
    }
}