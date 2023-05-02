package com.example.managerbookfreelancer.loginAndRegister.ui

import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.example.managerbookfreelancer.R
import com.example.managerbookfreelancer.databinding.FragmentLoginBinding
import com.example.managerbookfreelancer.loginAndRegister.core.Model.LoginModel
import com.example.managerbookfreelancer.loginAndRegister.core.UserResult
import com.example.managerbookfreelancer.loginAndRegister.viewModel.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val mViewModel by viewModels<AuthViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRegisterFragmentLogin.setOnClickListener(
            Navigation.createNavigateOnClickListener(R.id.action_loginFragment_to_registerFragment)
        )

        binding.btnLoginFragmentLogin.setOnClickListener {

            val email = binding.textInputEmailLogin.text.toString().trim()
            val password = binding.textInputPasswordLogin.text.toString().trim()

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                binding.textInputEmailLogin.error
                binding.textInputEmailLogin.requestFocus()
                binding.textInputEmailLogin.setError("Email unreal")
                return@setOnClickListener
            }

            if (password.length <= 6) {
                binding.textInputPasswordLogin.error
                binding.textInputPasswordLogin.requestFocus()
                return@setOnClickListener
            }

            val loginModel = LoginModel(email, password)


           lifecycleScope.launch {
                val result = mViewModel.login(loginModel)
                Log.d("Result", result.toString())
               if (result == UserResult.SUCCESS) {
                   findNavController().navigate(R.id.action_loginFragment_to_nav_host)
               } else {
                   Toast.makeText(requireContext(), "Error", Toast.LENGTH_LONG).show()
                   binding.textInputEmailLogin.setText("")
                   binding.textInputPasswordLogin.setText("")
               }

           }

        }
    }
}
