package com.example.reservationproject.view

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.afinal.viewmodel.RegisterViewModel
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private lateinit var binding : FragmentRegisterBinding
    private val viewModel : RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        // ViewModel'den passwordVisible değerini izle
        viewModel.passwordVisible.observe(viewLifecycleOwner) { isVisible ->
            if (isVisible) {
                // Şifre görünürse
                binding.PasswTxt.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.passLock.setImageResource(R.drawable.eye)
            } else {
                // Şifre gizlenirse
                binding.PasswTxt.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.passLock.setImageResource(R.drawable.eye_crossed)
            }
        }
        viewModel.CpasswordVisible.observe(viewLifecycleOwner) { isVisible ->
            if (isVisible) {
                // Şifre görünürse
                binding.comfirmPasswTxt.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.comfirmLock.setImageResource(R.drawable.eye)
            } else {
                // Şifre gizlenirse
                binding.comfirmPasswTxt.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.comfirmLock.setImageResource(R.drawable.eye_crossed)
            }
        }

        binding.passLock.setOnClickListener {
            viewModel.togglePasswordVisibility()
        }
        binding.comfirmLock.setOnClickListener {
            viewModel.toggleCPasswordVisibility()
        }
        binding.loginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
        }

        return binding.root
    }
}