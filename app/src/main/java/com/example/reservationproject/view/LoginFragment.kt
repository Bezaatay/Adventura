package com.example.reservationproject.view

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.reservationproject.viewmodel.LoginViewModel
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentLoginBinding
import com.example.reservationproject.manager.AppPref

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()
    private lateinit var appPref: AppPref

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        appPref = AppPref(requireContext())

        setupObservers()
        setupUI()
        checkAutoLogin()

        return binding.root
    }

    private fun setupObservers() {
        viewModel.passwordVisible.observe(viewLifecycleOwner) { isVisible ->
            setPasswordVisibility(isVisible)
        }

        viewModel.isSuccessLogin.observe(viewLifecycleOwner) { isSuccess ->
            handleLoginResult(isSuccess)
        }

        viewModel.token.observe(viewLifecycleOwner) { token ->
            appPref.saveToken(token)
        }
    }

    private fun setupUI() {
        val bundle: LoginFragmentArgs by navArgs()
        val username = bundle.username
        val password = bundle.password

        if (username != "null") {
            binding.mailTxt.setText(username)
        }
        if (password != "null") {
            binding.PasswTxt.setText(password)
        }

        binding.regBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        binding.passwordLock.setOnClickListener {
            viewModel.togglePasswordVisibility()
        }

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            appPref.setIsChecked(isChecked)
        }

        binding.loginBtn.setOnClickListener {
            performLogin()
        }
    }

    private fun setPasswordVisibility(isVisible: Boolean) {
        if (isVisible) {
            binding.PasswTxt.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            binding.passwordLock.setImageResource(R.drawable.eye)
        } else {
            binding.PasswTxt.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            binding.passwordLock.setImageResource(R.drawable.eye_crossed)
        }
    }

    private fun handleLoginResult(isSuccess: Boolean) {
        if (isSuccess) {
            if (binding.checkBox.isChecked) {
                appPref.userData(
                    binding.mailTxt.text.toString(),
                    binding.PasswTxt.text.toString(),
                    binding.checkBox.isChecked
                )
            } else {
                appPref.clearData()
            }
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        } else {
            Toast.makeText(context, "Login failed!", Toast.LENGTH_SHORT).show()
        }
    }

    private fun performLogin() {
        val username = binding.mailTxt.text.toString()
        val password = binding.PasswTxt.text.toString()

        when {
            username.isEmpty() -> Toast.makeText(context, "Kullanıcı Adı Boş Bırakılamaz!", Toast.LENGTH_SHORT).show()
            password.isEmpty() -> Toast.makeText(context, "Şifre Boş Bırakılamaz!", Toast.LENGTH_SHORT).show()
            else -> viewModel.loginSuccess(username, password)
        }
    }

    private fun checkAutoLogin() {
        if (appPref.getIsChecked()) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }
    }
}
