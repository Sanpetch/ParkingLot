package com.example.parkinglot.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.example.parkinglot.R
import com.example.parkinglot.ViewModel.RegisterViewModel
import com.example.parkinglot.ViewModel.UserViewModel
import com.example.parkinglot.databinding.FragmentLoginBinding
import com.example.parkinglot.databinding.FragmentRegisterBinding
import com.example.parkinglot.model.LoginRequest
import com.example.parkinglot.model.RegisterRequest
import com.example.parkinglot.util.Resource
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!

    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observe()
        binding.btnRegister.setOnClickListener {
            val username = binding.edtUsername.editableText.toString()
            val email = binding.edtEmail.editableText.toString()
            val password = binding.edtPassword.editableText.toString()
            val licensePlate = binding.edtLicensePlate.editableText.toString()


            if(username.isEmpty()){
                binding.txtllName.error = "This field is required"
            }else if(email.isEmpty()){
                binding.txtllEmail.error = "This field is required"
            }
            else if(password.isEmpty()){
                binding.txtllPassword.error = "This field is required"
            }else if(licensePlate.isEmpty()){
                binding.txtllLicensePlate.error = "This field is required"
            }
            else {
                binding.txtllName.isErrorEnabled = false
                binding.txtllEmail.isErrorEnabled = false
                binding.txtllPassword.isErrorEnabled = false
                binding.txtllLicensePlate.isErrorEnabled = false


                val registerRequest = RegisterRequest(name = username,
                    email = email,password = password, license_plate = licensePlate);
                registerViewModel.register(registerRequest)
            }
        }
    }

    private fun observe() {

        registerViewModel.registerLiveData.observe(viewLifecycleOwner){
            when (it) {
                is Resource.Success -> {

                    Toast.makeText(requireContext(), it.data?.message, Toast.LENGTH_SHORT).show()
                    it.data?.let { loginData ->
                        {
//                            Toast.makeText(requireContext(), loginData.message, Toast.LENGTH_SHORT).show()
//                            Log.d("Error", "Error: $loginData")

                        }

                    }
                }
                is Resource.Error -> {

                    it.message?.let { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        Log.e("Error", "Error: $message")
                    }
                }
                is Resource.Loading ->{

                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}