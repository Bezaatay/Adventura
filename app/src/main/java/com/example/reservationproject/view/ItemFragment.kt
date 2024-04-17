package com.example.reservationproject.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentItemBinding
import com.example.reservationproject.databinding.FragmentLoginBinding

class ItemFragment : Fragment() {
    private lateinit var binding : FragmentItemBinding
    private val viewModel : ItemViewModel by viewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentItemBinding.inflate(inflater, container, false)


        return binding.root
    }
}