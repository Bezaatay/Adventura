package com.example.reservationproject.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentProfileBinding
import com.example.reservationproject.manager.AppPref
import com.example.reservationproject.viewmodel.ProfileViewModel

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val appPref = AppPref(requireContext())
        val token = AppPref(requireContext()).getToken()
        Log.e("tken",token.toString())
        if (token != null) {
            binding.logBtn.visibility = View.INVISIBLE
            binding.signOutBtn.visibility = View.VISIBLE
            binding.fullNameTxt.text = appPref.getNameAndSurname()
        }
        if (token == null) {
            binding.fullNameTxt.text = "Misafir Kullanıcı"
            binding.textView11.visibility = View.INVISIBLE
            binding.signOutBtn.visibility = View.INVISIBLE
        }
        binding.constraintLayout9.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_navigation_dashboard, Bundle().apply {
                putString("typeOfTicket","Tour")
            })
        }
        binding.constraintLayout10.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_navigation_dashboard, Bundle().apply {
                putString("typeOfTicket","Flight")
            })
        }
        binding.constraintLayout11.setOnClickListener {
            findNavController().navigate(R.id.action_navigation_profile_to_navigation_dashboard, Bundle().apply {
                putString("typeOfTicket","Hotel")
            })
        }

        binding.logBtn.setOnClickListener {
            val intent = Intent(requireContext(), LogRegActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        binding.signOutBtn.setOnClickListener {
            showLogoutConfirmationDialog()
        }

        return binding.root
    }

    private fun showLogoutConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Log Out")
        builder.setMessage("Are you sure want to log out?")

        builder.setPositiveButton("Yes") { dialog, which ->
            val appPref = AppPref(requireContext())
            appPref.clearData()
            appPref.clearToken()
            val intent = Intent(requireActivity(), LogRegActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            dialog.dismiss()
        }

        builder.setNegativeButton("No") { dialog, which ->
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}