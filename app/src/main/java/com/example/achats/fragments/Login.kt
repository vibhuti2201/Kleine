package com.example.achats.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.achats.R
import com.example.achats.activities.ShoppingActivity
import com.example.achats.databinding.FragmentLoginBinding
import com.example.achats.dialog.setupBottomSheetDialog
import com.example.achats.util.Resource
import com.example.achats.viewmodel.LoginViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class Login : Fragment(R.layout.fragment_login) {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel by viewModels<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentLoginBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tvDontHaveAccount.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_registerFragment)
        }
        binding.apply {
            buttonLoginLogin.setOnClickListener {
                buttonLoginLogin.spinningBarColor = resources.getColor(R.color.white)
                buttonLoginLogin.spinningBarWidth = resources.getDimension(R.dimen._3sdp)
                val email= etEmailLogin.text.toString().trim()
                val password= etPasswordLogin.text.toString()
                viewModel.login(email,password)
            }
        }

        binding.tvForgotPasswordLogin.setOnClickListener {
            setupBottomSheetDialog { email->
viewModel.resetPassword(email)
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.resetPassword.collect{
                when(it){
                    is Resource.Loading->{

                    }
                    is Resource.Success->{
                       Snackbar.make(requireView(),"Reset link was sent to your email",Snackbar.LENGTH_LONG).show()
                    }

                    is Resource.Error->{

                    }
                    else ->Unit
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.login.collect{
                when(it){
                    is Resource.Loading->{
                        binding.buttonLoginLogin.startAnimation()
                    }
                    is Resource.Success->{
                        binding.buttonLoginLogin.revertAnimation()
                        Intent(requireActivity(),ShoppingActivity::class.java).also{intent->
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)
                        }
                    }

                    is Resource.Error->{
                        Toast.makeText(requireContext(),it.message,Toast.LENGTH_LONG).show()
                        binding.buttonLoginLogin.revertAnimation()
                    }
                    else ->Unit
                    //unit
                }
            }
        }
    }

}