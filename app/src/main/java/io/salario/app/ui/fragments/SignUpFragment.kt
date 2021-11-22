package io.salario.app.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.salario.app.R
import com.salario.app.databinding.SignUpFragmentBinding
import io.salario.app.data.source.remote.firebase.FirebaseAuthManager
import io.salario.app.ui.viewmodels.SignUpViewModel
import io.salario.app.utils.*

class SignUpFragment : Fragment() {
    private val viewModel: SignUpViewModel by viewModels()
    private lateinit var binding: SignUpFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SignUpFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            titleLayout.apply {
                title.text = getString(R.string.sign_up_title)
                subtitle.text = getString(R.string.sign_up_subtitle)
            }

            emailEt.doAfterTextChanged {
                validateEmail()
            }

            firstNameEt.doAfterTextChanged {
                validateFirstName()
            }

            lastNameEt.doAfterTextChanged {
                validateLastName()
            }

            passwordEt.doAfterTextChanged {
                validatePassword()
            }

            signUpActionBtn.setOnClickListener {
                if (validateForm()) {
                    viewModel.registerUser(
                        binding.firstNameEt.text.toString(),
                        binding.lastNameEt.text.toString(),
                        binding.emailEt.text.toString(),
                        binding.passwordEt.text.toString()
                    ).addOnCompleteListener {
                        if (it.isSuccessful) {
                            // TODO Save names

                            // Verify email
                            viewModel.sendVerificationEmail()
                                ?.addOnCompleteListener { verificationTask ->
                                    if (verificationTask.isSuccessful) {
                                        findNavController().navigate(
                                            SignUpFragmentDirections.actionSignupFragmentToStatusFragment()
                                        )
                                        Log.i("yoss", "Verified")
                                    } else {
                                        FirebaseAuthManager.signOut()
                                        // TODO manage failures
                                    }
                                }
                        }
                    }
                }
            }

            signInLinkBtn.setOnClickListener {
                findNavController().navigate(
                    SignUpFragmentDirections.actionSignupFragmentToSignInFragment()
                )
            }
        }
    }

    private fun validateForm(): Boolean {
        val isEmailValid = validateEmail()
        val isPasswordValid = validatePassword()
        val isFirstNameValid = validateFirstName()
        val isLastNameValid = validateLastName()

        return if (isEmailValid && isPasswordValid && isFirstNameValid && isLastNameValid) {
            true
        } else {
            if (!isEmailValid) {
                shakeView(binding.emailInputLayout)
            }

            if (!isPasswordValid) {
                shakeView(binding.passwordInputLayout)
            }

            if (!isFirstNameValid) {
                shakeView(binding.firstNameInputLayout)
            }

            if (!isLastNameValid) {
                shakeView(binding.lastNameInputLayout)
            }

            false
        }
    }

    private fun validateFirstName(): Boolean {
        return if (binding.firstNameEt.text.isNullOrBlank()) {
            showError(
                "First name should not be empty",
                binding.firstNameInputLayout
            )
            false
        } else {
            binding.firstNameInputLayout.error = null
            true
        }
    }

    private fun validateLastName(): Boolean {
        return if (binding.lastNameEt.text.isNullOrBlank()) {
            showError(
                "Last name should not be empty",
                binding.lastNameInputLayout
            )
            false
        } else {
            binding.lastNameInputLayout.error = null
            true
        }
    }


    private fun validateEmail(): Boolean {
        var isValidEmailAddress = false
        if (binding.emailEt.text.isNullOrBlank()) {
            binding.emailInputLayout.error = "Email cannot be empty"
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
                                "$MIN_PASSWORD_LENGTH characters long.",
                        binding.passwordInputLayout
                    )
                }

                PasswordState.ERROR_PASSWORD_TOO_LONG -> {
                    showError(
                        "Password should be maximum " +
                                "$MAX_PASSWORD_LENGTH characters long.",
                        binding.passwordInputLayout
                    )
                }

                PasswordState.ERROR_MUST_CONTAIN_LETTERS -> {
                    showError(
                        "Password should contain letters.",
                        binding.passwordInputLayout
                    )
                }

                PasswordState.ERROR_MUST_CONTAIN_UPPER_CASE -> {
                    showError(
                        "Password should contain at least one capital letter.",
                        binding.passwordInputLayout
                    )
                }

                PasswordState.ERROR_MUST_CONTAIN_LOWER_CASE -> {
                    showError(
                        "Password should contain at least one small letter.",
                        binding.passwordInputLayout
                    )
                }

                PasswordState.ERROR_MUST_CONTAIN_NUMBER -> {
                    showError(
                        "Password should contain at least one number",
                        binding.passwordInputLayout
                    )
                }

                PasswordState.ERROR_MUST_CONTAIN_SPECIAL_CHARACTER -> {
                    showError(
                        "Password should contain at least one special character",
                        binding.passwordInputLayout
                    )
                }
            }
        }

        return isValidPassword
    }

    private fun showError(errorText: String, layout: TextInputLayout) {
        layout.error = errorText
    }
}