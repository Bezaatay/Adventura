package com.example.reservationproject.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.reservationproject.R
import com.example.reservationproject.viewmodel.ResTourViewModel

class ResTour : Fragment() {

    companion object {
        fun newInstance() = ResTour()
    }

    private lateinit var viewModel: ResTourViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_res_tour, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ResTourViewModel::class.java)
        // TODO: Use the ViewModel
    }

}