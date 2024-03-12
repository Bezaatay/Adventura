package com.example.reservationproject.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentHomeBinding
import com.example.reservationproject.viewmodel.HomeViewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel = ViewModelProvider(this)[HomeViewModel::class.java]

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        viewModel.paramLiveData.observe(viewLifecycleOwner) { param ->
            param?.let {
                sendArgument(param)
            }
        }

        val param = arguments?.getString("param")
        viewModel.paramLiveData.value = param

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun sendArgument(arg: String) {
        findNavController().navigate(
            R.id.action_navigation_home_to_productFragment,
            Bundle().apply {
                putString("param", arg)
            })
    }
}
