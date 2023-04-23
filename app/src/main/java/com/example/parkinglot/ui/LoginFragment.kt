package com.example.parkinglot.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.parkinglot.ViewModel.UserViewModel
import com.example.parkinglot.databinding.FragmentLoginBinding
import com.example.parkinglot.model.LoginRequest
import com.example.parkinglot.util.Resource
import com.example.parkinglot.util.SharedPrefManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedPrefManager : SharedPrefManager

    private val userViewModel: UserViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)


        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observe()
        binding.btnLogin.setOnClickListener {
            val username = binding.edtUsername.editableText.toString()
            val password = binding.edtPassword.editableText.toString()

            if(username.isEmpty()){
                binding.txtInputLayout.error = "This field is required"
            }else if(password.isEmpty()){
                binding.txtInputLayout.isErrorEnabled = false
                binding.txtllPassword.error = "This field is required"
            }
            else {
                binding.txtInputLayout.isErrorEnabled = false
                binding.txtllPassword.isErrorEnabled = false
                val loginRequest = LoginRequest(username, password);
                userViewModel.login(loginRequest)
            }
        }
    }

    fun observe() {
        userViewModel.logInLiveData.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Success -> {
                    binding.pBar.visibility = View.GONE

                    sharedPrefManager.saveUser(it.data?.user?.name,it.data?.user?.licensePlate)

                    sharedPrefManager.readUser()?.let { user->
                        Log.d("save", user)
                        val intent = Intent(context, ParkingLotActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }

//                    startActivity(Intent(context, ParkingLotActivity::class.java))

//                    Toast.makeText(requireContext(), it.data?.message, Toast.LENGTH_SHORT).show()
//                    it.data?.let { loginData ->
//                        {
//                            Toast.makeText(requireContext(), loginData.message, Toast.LENGTH_SHORT).show()
//                            Log.d("Error", "Error: $loginData")
//                        }
//
//                    }
                }
                is Resource.Error -> {
                    binding.pBar.visibility = View.GONE
                    it.message?.let { message ->
                        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        Log.e("Error", "Error: $message")
                    }
                }
                is Resource.Loading ->{
                    binding.pBar.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}