package com.udacity.shoestore.ui.login.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentInstructionScreenBinding
import com.udacity.shoestore.ui.home.activity.MainActivity

private const val TAG = "InstructionScreenFragment"
class InstructionScreenFragment : Fragment() {
    private lateinit var binding: FragmentInstructionScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInstructionScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("LongLogTag")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.nextBtn.setOnClickListener {
            var email =  arguments?.get(getString(R.string.email))
            var pass = arguments?.get(getString(R.string.password))
            Log.d(TAG, "onViewCreated: email: $email")
            Log.d(TAG, "onViewCreated: password: $pass")
           activity?.startActivity(Intent(requireContext(),MainActivity::class.java))
            activity?.finish()
        }
    }

}