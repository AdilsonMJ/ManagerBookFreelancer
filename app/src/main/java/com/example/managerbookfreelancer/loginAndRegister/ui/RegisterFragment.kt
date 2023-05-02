package com.example.managerbookfreelancer.loginAndRegister.ui

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.managerbookfreelancer.R
import com.example.managerbookfreelancer.databinding.FragmentRegisterBinding
import com.example.managerbookfreelancer.loginAndRegister.core.Model.CreateUserModel
import com.example.managerbookfreelancer.loginAndRegister.core.UserResult
import com.example.managerbookfreelancer.loginAndRegister.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val mViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.btnBackToLogin.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_registerFragment_to_loginFragment)
        )

        binding.btnRegisterFragmentRegister.setOnClickListener {


            var email = binding.textInputEmailLoginFragmentRegister.text.toString().trim()
            var password = binding.textInputPasswordFragmentRegister.text.toString().trim()
            var confirmPassword = binding.textInputConfirmPasswordFragmentRegister.text.toString()
            var name = binding.textInputNameFragmentRegister.text.toString().trim()


            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.textInputEmailLoginFragmentRegister.requestFocus()
                binding.textInputEmailLoginFragmentRegister.error
                return@setOnClickListener
            }

            if (password.length <= 6) {
                binding.textInputPasswordFragmentRegister.requestFocus()
                binding.textInputPasswordFragmentRegister.error
                return@setOnClickListener
            }

            if (name.isEmpty()){
                binding.textInputNameFragmentRegister.error
                binding.textInputNameFragmentRegister.requestFocus()
                return@setOnClickListener
            }

            if (confirmPassword != password){
                binding.textInputConfirmPasswordFragmentRegister.error
                binding.textInputConfirmPasswordFragmentRegister.requestFocus()
                return@setOnClickListener
            }

            val user = CreateUserModel(email, password, name)


            mViewModel.createUser(user)
            mViewModel.userResult.observe(viewLifecycleOwner) { result ->

                if (result == UserResult.USUARIOEXIST){
                    Toast.makeText(requireContext(), "User already exist.", Toast.LENGTH_LONG).show()
                    binding.textInputEmailLoginFragmentRegister.error
                    binding.textInputEmailLoginFragmentRegister.requestFocus()
                }

                if (result == UserResult.SUCCESS){
                    Toast.makeText(requireContext(), "user created successfully.", Toast.LENGTH_LONG).show()
                    findNavController().navigateUp()
                }
            }
        }
    }
}