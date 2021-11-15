package io.salario.app.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.salario.app.databinding.IntroductionFragmentBinding
import io.salario.app.ui.viewmodels.IntroductionViewModel

class IntroductionFragment : Fragment() {

    companion object {
        fun newInstance() = IntroductionFragment()
    }

    private lateinit var binding: IntroductionFragmentBinding
    private val viewModel: IntroductionViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = IntroductionFragmentBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO if user is already signed in navigate to Status screen and pop everything
//        findNavController().navigate(
//            IntroductionFragmentDirections.actionIntroductionFragmentToStatusFragment()
//        )

        binding.signInBtn.setOnClickListener {
            findNavController().navigate(
                IntroductionFragmentDirections.actionIntroductionFragmentToIdentificationFragment()
            )
        }

        binding.signUpBtn.setOnClickListener {
            findNavController().navigate(
                IntroductionFragmentDirections.actionIntroductionFragmentToSignupFragment2()
            )
        }
    }
}