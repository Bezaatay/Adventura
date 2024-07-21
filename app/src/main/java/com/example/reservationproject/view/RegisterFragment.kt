package com.example.reservationproject.view

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentRegisterBinding
import com.example.reservationproject.manager.AppPref
import com.example.reservationproject.viewmodel.RegisterViewModel
import java.util.regex.Pattern

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)
        setupObservers()
        setupListeners()
        return binding.root
    }

    private fun setupObservers() {
        viewModel.passwordVisible.observe(viewLifecycleOwner) { isVisible ->
            updatePasswordVisibility(binding.PasswTxt, binding.passLock, isVisible)
        }
        viewModel.CpasswordVisible.observe(viewLifecycleOwner) { isVisible ->
            updatePasswordVisibility(binding.confirmPasswTxt, binding.confirmLock, isVisible)
        }
        viewModel.registrationSuccess.observe(viewLifecycleOwner) { success ->
            if (success) showCreatedUserDialog()
        }
        viewModel.registrationError.observe(viewLifecycleOwner) { errorMessage ->
            errorMessage?.let { showToast(it) }
        }
    }

    private fun setupListeners() {
        binding.passLock.setOnClickListener {
            viewModel.togglePasswordVisibility()
        }
        binding.confirmLock.setOnClickListener {
            viewModel.toggleCPasswordVisibility()
        }
        binding.logBtn.setOnClickListener {
            findNavController().navigate(R.id.regToLog)
        }
        binding.regBtn.setOnClickListener {
            handleRegistration()
        }
        binding.usernameTxt.addTextChangedListener(UsernameTextWatcher(binding.usernameTxt))
        binding.mailTxt.addTextChangedListener(EmailTextWatcher(binding.mailTxt))
    }

    private fun handleRegistration() {
        val email = binding.mailTxt.text.toString()
        val username = binding.usernameTxt.text.toString()
        val password = binding.PasswTxt.text.toString()
        val confirmPassword = binding.confirmPasswTxt.text.toString()
        val name = binding.nameTxt.text.toString()
        val surname = binding.surnameTxt.text.toString()

        viewModel.registerUser(email, username, password, name, surname, confirmPassword)
    }

    private fun updatePasswordVisibility(editText: EditText, lockIcon: ImageView, isVisible: Boolean) {
        if (isVisible) {
            editText.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            lockIcon.setImageResource(R.drawable.eye) // Burada resim kaynağını ayarlıyoruz
        } else {
            editText.inputType = InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            lockIcon.setImageResource(R.drawable.eye_crossed) // Burada resim kaynağını ayarlıyoruz
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showCreatedUserDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle("Log Out")
            setMessage("Kullanıcı Oluşturuldu")
            setPositiveButton("Tamam") { _, _ ->
                navigateToLogin()
            }
            create().show()
        }
    }

    private fun navigateToLogin() {
        val transition = RegisterFragmentDirections.regToLog(
            username = binding.usernameTxt.text.toString(),
            password = binding.PasswTxt.text.toString()
        )
        view?.let { Navigation.findNavController(it).navigate(transition) }
    }

    private class UsernameTextWatcher(private val editText: EditText) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            s?.let {
                val text = it.toString()
                if (!isValidUsername(text)) {
                    editText.error = "Yalnızca rakamlardan veya yalnızca özel karakterlerden oluşamaz."
                } else {
                    editText.error = null
                }
            }
        }

        private fun isValidUsername(username: String): Boolean {
            val usernameLengthMin = 3
            val usernameLengthMax = 32
            val letterCount = username.count { it.isLetter() }
            val nonLetterCount = username.length - letterCount
            val onlyDigitsRegex = "^[0-9]+$"
            val onlySpecialCharsRegex = "^[._-]+$"
            return username.length in usernameLengthMin..usernameLengthMax &&
                    nonLetterCount < letterCount * 2 &&
                    !(Pattern.matches(onlyDigitsRegex, username) || Pattern.matches(
                        onlySpecialCharsRegex,
                        username
                    ))
        }
    }

    private class EmailTextWatcher(private val editText: EditText) : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            s?.let {
                val text = it.toString()
                if (!isValidEmail(text)) {
                    editText.error = "E-postanızı example@example.com biçiminde girin."
                } else {
                    editText.error = null
                }
            }
        }

        private fun isValidEmail(email: String): Boolean {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }
}
