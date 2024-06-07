package com.example.reservationproject.view.hotelViews

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.bezalibrary.service.RetrofitClient
import com.example.bezalibrary.service.model.HotelRes
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentRoomItemBinding
import com.example.reservationproject.manager.AppPref
import com.example.reservationproject.utils.DateFunctions
import com.example.reservationproject.utils.DatePicker
import com.example.reservationproject.view.DashboardFragment
import com.example.reservationproject.view.LogRegActivity
import com.example.reservationproject.viewmodel.RoomItemViewModel

class RoomItemFragment : Fragment() {
    private lateinit var binding: FragmentRoomItemBinding
    private val viewModel: RoomItemViewModel by viewModels()
    private var totalPrice: Long? = null
    private var price: Long? = null
    private var totalDay: Long? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRoomItemBinding.inflate(inflater, container, false)
        val token = AppPref(requireContext()).getToken()
        Log.e("token",token.toString())
        if (token != null) {
            RetrofitClient.setAuthToken(token)
        }
        val dateFunc = DateFunctions
        val roomId = requireArguments().getLong("roomId")
        val hotelId = requireArguments().getLong("hotelId")
        viewModel.fetchRoomById(roomId)

        viewModel.room.observe(viewLifecycleOwner) {
            price = it.roomPrice
            binding.priceTxt.text = it.roomPrice.toString() + "$"
            binding.roomTypeTxt.text = it.roomType + "Oda"
            Glide.with(requireContext())
                .load(it.roomImage)
                .into(binding.roomImage)
        }

        viewModel.getHotelNameByHotelId(hotelId)
        viewModel.hotelName.observe(viewLifecycleOwner) {
            binding.hotelNameTxt.text = it
        }

        val startDateButton = binding.startDateButton
        val endDateButton = binding.endDateButton

        viewModel.startDate.observe(viewLifecycleOwner) { startDate ->
            startDateButton.text = startDate
        }

        viewModel.endDate.observe(viewLifecycleOwner) { endDate ->
            endDateButton.text = endDate
        }
        binding.startCons.setOnClickListener {
            DatePicker.DateUtil.showDatePicker(
                requireContext(),
                viewModel.startDate.value,
                viewModel::setStartDate
            )
        }
        binding.endCons.setOnClickListener {
            DatePicker.DateUtil.showDatePicker(
                requireContext(),
                viewModel.endDate.value,
                viewModel::setEndDate
            )
        }

        binding.resRoomBtn.setOnClickListener {
            if (token != null) {
                val newHotelRoomRes = HotelRes(
                    roomId.toInt(),
                    dateFunc.convertDateToISOFormat(startDateButton.text.toString()),
                    dateFunc.convertDateToISOFormat(endDateButton.text.toString())
                )
                viewModel.createRoomReservation(newHotelRoomRes)
            } else {
                showLoginConfirmationDialog()
            }
        }
        viewModel.isTotalPrice.observe(viewLifecycleOwner) {
            totalDay = dateFunc.getDaysDifference(startDateButton.text.toString(), endDateButton.text.toString())

            if(totalDay!!.toInt()>0){
                totalPrice = price?.times(totalDay!!)
                binding.resRoomBtn.text = "Rezerve Et" + "(" + totalPrice.toString() + "$)"

                val checkIn = startDateButton.text.toString()
                val checkOut = endDateButton.text.toString()
                viewModel.checkReservation(roomId, checkIn, checkOut)
            }else {
                Toast.makeText(requireContext(), "Çıkış Tarihi Giriş Tarihinden ileri tarihli olmalıdır!", Toast.LENGTH_LONG).show()
            }
        }

        viewModel.isRoomAvailable.observe(viewLifecycleOwner) {
            if (it) {

            } else {
                Toast.makeText(
                    requireContext(),
                    "Seçilen tarihlerde oda doludur!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        viewModel.isRes.observe(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_roomItemFragment_to_paymentFragment, Bundle().apply {
                putString("url",it)
                putString("typeOfTicket","Hotel")
            })
        }
        return binding.root
    }

    private fun showLoginConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Kulllanıcı Bilgisi Boş")
        builder.setMessage("Rezervasyon yapabilmek için kullanıcı girişi yapmanız gerekmektedir.")

        builder.setPositiveButton("Giriş Yap") { dialog, _ ->
            val intent = Intent(requireActivity(), LogRegActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
            dialog.dismiss()
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }
}