package com.udacity.shoestore.ui.login.fragment.onboarding

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentWelcomeScreenBinding

private const val TAG = "WelcomeScreenFragment"
class WelcomeScreenFragment : Fragment() {
    private lateinit var binding : FragmentWelcomeScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWelcomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextBtn.setOnClickListener {
           var email =  arguments?.get(getString(R.string.email))
           var pass = arguments?.get(getString(R.string.password))
            Log.d(TAG, "onViewCreated: email: $email")
            Log.d(TAG, "onViewCreated: password: $pass")
            findNavController().navigate(R.id.action_welcomeScreenFragment_to_instructionScreenFragment,
                bundleOf(getString(R.string.email) to email , getString(R.string.password) to pass)
            )
        }
        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }

        })
    }

}