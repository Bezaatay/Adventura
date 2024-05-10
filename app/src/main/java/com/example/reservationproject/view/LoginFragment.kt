package com.example.reservationproject.view

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.bezalibrary.service.Functions
import com.example.reservationproject.viewmodel.LoginViewModel
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentLoginBinding
import com.example.reservationproject.manager.AppPref

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        val functions = Functions()

        val appPref = AppPref(requireContext())
        val spChecked = appPref.getIsChecked()

        if (spChecked) {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }

        val bundle: LoginFragmentArgs by navArgs()
        val username = bundle.username
        val password = bundle.password

        if (username != "null") {
            binding.mailTxt.setText(username)
        }
        if (password != "null") {
            binding.PasswTxt.setText(password)
        }

        viewModel.passwordVisible.observe(viewLifecycleOwner) { isVisible ->
            if (isVisible) {
                // Şifre görünürse
                binding.PasswTxt.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.passwordLock.setImageResource(R.drawable.eye)
            } else {
                // Şifre gizlenirse
                binding.PasswTxt.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.passwordLock.setImageResource(R.drawable.eye_crossed)
            }
        }

        binding.regBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.passwordLock.setOnClickListener {
            viewModel.togglePasswordVisibility()
        }

        var rememberMe = binding.checkBox.isChecked

        binding.checkBox.setOnCheckedChangeListener { _, isChecked ->
            rememberMe = isChecked
        }

        viewModel.isSuccessLogin.observe(viewLifecycleOwner) {
            if (it) {
                if (rememberMe) {
                    // appPref.saveToken(token)
                    appPref.userData(
                        binding.mailTxt.text.toString(),
                        binding.PasswTxt.text.toString(),
                        rememberMe
                    )
                    appPref.setIsChecked(true)
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    startActivity(intent)
                    requireActivity().finish()
                } else {
                    //  appPref.saveToken(token)
                    appPref.clearData()
                    appPref.setIsChecked(false)
                    val intent = Intent(requireContext(), MainActivity::class.java)
                    intent.putExtra("username", username)
                    startActivity(intent)
                    requireActivity().finish()
                }
            }
        }
        viewModel.token.observe(viewLifecycleOwner){
            appPref.saveToken(it)
            Log.e("token is",it)
        }
        binding.loginBtn.setOnClickListener {

            if (binding.mailTxt.text.isEmpty()) {
                Toast.makeText(context, "Kullanıcı Adı Boş Bırakılamaz!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            } else if (binding.PasswTxt.text.isEmpty()) {
                Toast.makeText(context, "Şifre Boş Bırakılamaz!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            } else {
                functions.login(
                    binding.mailTxt.text.toString(),
                    binding.PasswTxt.text.toString()
                )
                viewModel.loginSuccess(
                    binding.mailTxt.text.toString(),
                    binding.PasswTxt.text.toString()
                )
            }
        }
        return binding.root
    }
}
