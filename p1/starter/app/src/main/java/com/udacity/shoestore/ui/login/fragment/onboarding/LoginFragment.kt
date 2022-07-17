package com.udacity.shoestore.ui.login.fragment.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentLoginBinding

/***
 * animation reference " https://developer.android.com/guide/navigation/navigation-animate-transitions "
 */
class LoginFragment : Fragment() {
    private lateinit var binding : FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.loginBtn.setOnClickListener {
            var email = binding.emailTextField.editText!!.text.toString()
            var pass = binding.passTextField.editText!!.text.toString()
            if(email.isNullOrEmpty() && pass.isNullOrEmpty()){
                binding.emailTextField.error = "Email can not be empty"
                binding.passTextField.error = "Password can not be empty"
            }else if(email.isNullOrEmpty() && !pass.isNullOrEmpty()){
                binding.emailTextField.error = "Email can not be empty"
                binding.passTextField.error = null
            }else if(!email.isNullOrEmpty() && pass.isNullOrEmpty()){
                binding.passTextField.error = "Password can not be empty"
                binding.emailTextField.error = null
            }else{
                binding.emailTextField.error = null
                binding.passTextField.error = null
                findNavController().navigate(R.id.action_loginFragment_to_welcomeScreenFragment_with_args,
                    bundleOf(getString(R.string.email) to email , getString(R.string.password) to pass)
                )
            }
        }
        binding.newLoginBtn.setOnClickListener {
            binding.emailTextField.error = null
            binding.passTextField.error = null
            findNavController().navigate(R.id.action_loginFragment_to_welcomeScreenFragment)
        }
    }

}