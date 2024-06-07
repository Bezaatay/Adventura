package com.example.reservationproject.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.navigation.fragment.findNavController
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentPaymentBinding

class PaymentFragment : Fragment() {

    private lateinit var binding: FragmentPaymentBinding

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPaymentBinding.inflate(inflater, container, false)

        val url = requireArguments().getString("url")

        Log.e("url ", url.toString())
        val webView = binding.webView
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true
        webView.webViewClient = WebViewClient()

        if (url != null) {
            webView.loadUrl(url)
        }

        binding.ReturnHomepage.setOnClickListener {
            findNavController().navigate(R.id.navigation_home)
        }

        return binding.root
    }
}
