package com.example.reservationproject.view

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.reservationproject.R
import com.example.reservationproject.databinding.FragmentRegisterBinding
import com.example.reservationproject.manager.AppPref
import com.example.reservationproject.model.NewUser
import com.example.reservationproject.viewmodel.RegisterViewModel
import java.util.regex.Pattern

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private val viewModel: RegisterViewModel by viewModels()

    val nameEdttxt: EditText by lazy { binding.nameTxt }
    val surnameEdttxt: EditText by lazy { binding.surnameTxt }
    val mailEdttxt: EditText by lazy { binding.mailTxt }
    val usernameEdttxt: EditText by lazy { binding.usernameTxt }
    val passwordEdttxt: EditText by lazy { binding.PasswTxt }
    val cPasswordEdttxt: EditText by lazy { binding.confirmPasswTxt }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false)

        val appPref = AppPref(requireContext())

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
                binding.confirmPasswTxt.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.confirmLock.setImageResource(R.drawable.eye)
            } else {
                // Şifre gizlenirse
                binding.confirmPasswTxt.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.confirmLock.setImageResource(R.drawable.eye_crossed)
            }
        }

        binding.passLock.setOnClickListener {
            viewModel.togglePasswordVisibility()
        }
        binding.confirmLock.setOnClickListener {
            viewModel.toggleCPasswordVisibility()
        }
        binding.logBtn.setOnClickListener {
            findNavController().navigate(R.id.regToLog)
        }

        usernameEdttxt.addTextChangedListener(UsernameTextWatcher())
        mailEdttxt.addTextChangedListener(EmailTextWatcher())

        binding.regBtn.setOnClickListener {
            val password = passwordEdttxt.text.toString()
            val confirmPassword = cPasswordEdttxt.text.toString()

            when {
                password.isBlank() -> {
                    Toast.makeText(
                        requireContext(),
                        "Şifre boş bırakılamaz!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                confirmPassword.isBlank() -> {
                    Toast.makeText(
                        requireContext(),
                        "Şifre tekrarı boş bırakılamaz!",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }

                confirmPassword != password -> {
                    Toast.makeText(requireContext(), "Şifreler Uyuşmuyor!", Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }

                else -> {
                    val user = NewUser(
                        binding.mailTxt.text.toString(),
                        binding.usernameTxt.text.toString(),
                        binding.PasswTxt.text.toString(),
                        binding.nameTxt.text.toString(),
                        binding.surnameTxt.text.toString()
                    )
                    viewModel.createUser(user)

                    val transition = RegisterFragmentDirections.regToLog(
                        username = binding.usernameTxt.text.toString(),
                        password = binding.PasswTxt.text.toString(),
                    )
                    Navigation.findNavController(it).navigate(transition)
                }

            }
        }

        return binding.root
    }

    private inner class UsernameTextWatcher : SimpleTextWatcher() {
        override val editText: EditText
            get() = usernameEdttxt

        override fun validate(text: String): Boolean {
            return isValidUsername(text)
        }

        override fun getErrorMessage(): String {
            return "Yalnızca rakamlardan veya yalnızca özel karakterlerden oluşamaz."
        }
    }

    private inner class EmailTextWatcher : SimpleTextWatcher() {
        override val editText: EditText
            get() = mailEdttxt

        override fun validate(text: String): Boolean {
            return isValidEmail(text)
        }

        override fun getErrorMessage(): String {
            return "E-postanızı example@example.com biçiminde girin."
        }
    }

    private abstract class SimpleTextWatcher : TextWatcher {
        abstract val editText: EditText

        abstract fun validate(text: String): Boolean

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

        override fun afterTextChanged(s: Editable?) {
            s?.let { editable ->
                val text = editable.toString()
                val isValid = validate(text)
                if (!isValid) {
                    editText.error = getErrorMessage()
                } else {
                    editText.error = null
                }
            }
        }

        open fun getErrorMessage(): String = "invalid entry"
    }

    private fun isValidName(name: String): Boolean {
        val onlyLettersRegex = "^[a-zA-ZğüşıöçĞÜŞİÖÇ]+$"
        val validNameRegex = "^[A-ZĞÜŞİÖÇa-zğüşıöçĞÜŞİÖÇ][a-zA-ZğüşıöçĞÜŞİÖÇ]*$"
        return if (Pattern.matches(onlyLettersRegex, name) && Pattern.matches(
                validNameRegex,
                name
            )
        ) {
            name[0].isUpperCase()
        } else {
            false
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show()
    }

    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
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
