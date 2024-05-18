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
import androidx.fragment.app.viewModels
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.example.bezalibrary.service.Functions
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentRoomItemBinding
import com.example.reservationproject.manager.AppPref
import com.example.reservationproject.utils.DateFunctions
import com.example.reservationproject.utils.DatePicker
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
        val dateFunc = DateFunctions
        val roomId = requireArguments().getLong("roomId")
        val hotelId = requireArguments().getLong("hotelId")
        viewModel.fetchRoomById(roomId)

        viewModel.room.observe(viewLifecycleOwner) {
            price = it.roomPrice
            binding.priceTxt.text = it.roomPrice.toString() + "₺"
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
            if(token!=null) {
                val checkIn = startDateButton.text.toString()
                val checkOut = endDateButton.text.toString()
                viewModel.checkReservation(roomId, checkIn, checkOut)
            }else{
                showLoginConfirmationDialog()
            }
        }
        viewModel.isTotalPrice.observe(viewLifecycleOwner){
            totalDay = dateFunc.getDaysDifference(
                startDateButton.text.toString(),
                endDateButton.text.toString()
            )
            totalPrice = price?.times(totalDay!!)
            binding.resRoomBtn.text = "Rezerve Et" + "(" + totalPrice.toString() + "TL)"
        }

        viewModel.isRoomAvailable.observe(viewLifecycleOwner) {
            if (it) {
                //stripe
            } else {
                Toast.makeText(
                    requireContext(),
                    "Seçilen tarihlerde oda doludur!",
                    Toast.LENGTH_LONG
                ).show()
            }
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