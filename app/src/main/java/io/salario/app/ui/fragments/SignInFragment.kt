package io.salario.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.salario.app.databinding.SignInFragmentBinding
import io.salario.app.ui.viewmodels.IdentificationViewModel
import io.salario.app.utils.*

class SignInFragment : Fragment() {
    private lateinit var binding: SignInFragmentBinding
    private val viewModel: IdentificationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignInFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            emailEt.doAfterTextChanged {
                validateEmail()
            }

            passwordEt.doAfterTextChanged {
                validatePassword()
            }

            signInActionBtn.setOnClickListener {
                if (validateCredentials()) {
                    findNavController().navigate(
                        SignInFragmentDirections.actionIdentificationFragmentToStatusFragment()
                    )
                }
            }

            forgotPasswordLink.setOnClickListener {
                Toast.makeText(context, "Fuck... sorry for you", Toast.LENGTH_SHORT).show()
            }

            signUpLinkBtn.setOnClickListener {
                findNavController().navigate(
                    SignInFragmentDirections.actionSignInFragmentToSignupFragment()
                )
            }
        }
    }

    private fun validateCredentials(): Boolean {
        val isEmailValid = validateEmail()
        val isPasswordValid = validatePassword()

        return if (isEmailValid && isPasswordValid) {
            true
        } else {
            if (!isEmailValid) {
                shakeView(binding.emailInputLayout)
            }

            if (!isPasswordValid) {
                shakeView(binding.passwordInputLayout)
            }

            false
        }
    }

    private fun validateEmail(): Boolean {
        var isValidEmailAddress = false
        if (binding.emailEt.text.isNullOrBlank()) {
            binding.emailInputLayout.error = "Email field cannot be empty"
        } else if (!isValidEmail(binding.emailEt.text.toString())) {
            binding.emailInputLayout.error = "Please enter a correct Email address"
        } else {
            binding.emailInputLayout.error = null
            isValidEmailAddress = true
        }

        return isValidEmailAddress
    }

    private fun validatePassword(): Boolean {
        var isValidPassword = false

        if (binding.passwordEt.text.isNullOrBlank()) {
            binding.passwordInputLayout.error = "Password cannot be empty"
        } else {
            when (isValidPassword(binding.passwordEt.text.toString())) {
                PasswordState.PASSWORD_OK -> {
                    binding.passwordInputLayout.error = null
                    isValidPassword = true
                }

                PasswordState.ERROR_PASSWORD_TOO_SHORT -> {
                    showError(
                        "Password should be minimum " +
                                "$MIN_PASSWORD_LENGTH characters long."
                    )
                }

                PasswordState.ERROR_PASSWORD_TOO_LONG -> {
                    showError(
                        "Password should be maximum " +
                                "$MAX_PASSWORD_LENGTH characters long."
                    )
                }

                PasswordState.ERROR_MUST_CONTAIN_LETTERS -> {
                    showError("Password should contain letters.")
                }

                PasswordState.ERROR_MUST_CONTAIN_UPPER_CASE -> {
                    showError("Password should contain at least one capital letter.")
                }

                PasswordState.ERROR_MUST_CONTAIN_LOWER_CASE -> {
                    showError("Password should contain at least one small letter.")
                }

                PasswordState.ERROR_MUST_CONTAIN_NUMBER -> {
                    showError("Password should contain at least one number")
                }

                PasswordState.ERROR_MUST_CONTAIN_SPECIAL_CHARACTER -> {
                    showError("Password should contain at least one special character")
                }
            }
        }

        return isValidPassword
    }

    private fun showError(errorText: String) {
        binding.passwordInputLayout.error = errorText
    }
}