package com.example.reservationproject.view.tourViews

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.bezalibrary.service.model.TourRes
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentTourItemBinding
import com.example.reservationproject.manager.AppPref
import com.example.reservationproject.view.LogRegActivity
import com.example.reservationproject.viewmodel.TourItemViewModel

class TourItemFragment : Fragment() {

private lateinit var binding : FragmentTourItemBinding
private val viewModel : TourItemViewModel by viewModels()
    private var priceAdult: Int = 0
    private var priceChild: Int = 0
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTourItemBinding.inflate(inflater, container, false)
        val token = AppPref(requireContext()).getToken()
        val tourId = requireArguments().getLong("tourId")
        viewModel.getTourById(tourId)
        viewModel.tour.observe(viewLifecycleOwner){
            priceAdult = it.tourAdultPrice.toInt()
            priceChild = it.tourChildPrice.toInt()
            viewModel.setPrices(priceAdult, priceChild)
            binding.tourNameTxt.text = it.name
            binding.locationTxt.text = it.location
            binding.descriptionTxt.text = it.description
            binding.priceTxt.text = "${it.tourAdultPrice}"+"$"
            binding.babyPriceTxt.text = "${it.tourChildPrice}"+"$"
            binding.ratingBar.rating = it.stars.toFloat()
            Glide.with(requireContext())
                .load(it.image)
                .into(binding.photoImgView)
        }

        binding.add.setOnClickListener {
            viewModel.incrementAdultCount()
        }

        binding.minus.setOnClickListener {
            viewModel.decrementAdultCount()
        }

        binding.addChild.setOnClickListener {
            viewModel.incrementChildCount()
        }

        binding.minusChild.setOnClickListener {
            viewModel.decrementChildCount()
        }

        viewModel.adultCount.observe(viewLifecycleOwner) {
            binding.personNum.text = it.toString()
        }

        viewModel.childCount.observe(viewLifecycleOwner) {
            binding.childNum.text = it.toString()
        }

        viewModel.totalAmount.observe(viewLifecycleOwner) {
            binding.findRoomBtn.text = "Rezervasyon Yap"+"("+it.toString()+"$)"
        }

        binding.findRoomBtn.setOnClickListener {
            if(token==null){
                showLoginConfirmationDialog()
            }
            else{
               val totalPersonNum = binding.personNum.text.toString().toInt()+ binding.childNum.text.toString().toInt()
                val newTourRes = TourRes(tourId,totalPersonNum)
                viewModel.createTourReservation(newTourRes)
            }
        }
        viewModel.isRes.observe(viewLifecycleOwner){
                findNavController().navigate(R.id.action_tourItemFragment_to_paymentFragment, Bundle().apply {
                    putString("url",it)
                    putString("typeOfTicket","Tour")
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