package com.example.reservationproject.view

import android.os.Bundle
import android.text.InputType
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.reservationproject.viewmodel.LoginViewModel
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentLoginBinding
import com.works.solutionchallange2024.manager.AppPref

class LoginFragment : Fragment() {

    private lateinit var binding : FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        val appPref = AppPref(requireContext())

        // ViewModel'den passwordVisible değerini izle
        viewModel.passwordVisible.observe(viewLifecycleOwner) { isVisible ->
            if (isVisible) {
                // Şifre görünürse
                binding.PasswTxt.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.passwordLock.setImageResource(R.drawable.eye)
            } else {
                // Şifre gizlenirse
                binding.PasswTxt.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.passwordLock.setImageResource(R.drawable.eye_crossed)
            }
        }


        var rememberMe = binding.checkBox.isChecked

        /*   viewModel.setLoginListener(object : LoginViewModel.LoginListener {
               override fun onLoginSuccess(token: String) {
                   if (rememberMe) {
                       appPref.saveToken(token)
                       appPref.userData(email, password, rememberMe)
                       appPref.setIsChecked(true)
                       val intent = Intent(requireContext(), HomeActivity::class.java)
                       startActivity(intent)
                       requireActivity().finish()
                   } else {
                       appPref.saveToken(token)
                       appPref.clearData()
                       appPref.setIsChecked(false)
                       val intent = Intent(requireContext(), HomeActivity::class.java)
                       startActivity(intent)
                       requireActivity().finish()
                   }
               }

               override fun onLoginFailure(error: String) {

               }
           }*/

        binding.regBtn.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
        binding.passwordLock.setOnClickListener {
            viewModel.togglePasswordVisibility()
        }
        return binding.root
    }
}
