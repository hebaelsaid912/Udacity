package com.udacity.shoestore.ui.login.fragment.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.databinding.FragmentProductDetailsBinding
import com.udacity.shoestore.ui.login.fragment.home.shared_viewModel.HomeViewModel

class ProductDetailsFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        viewModel.activity = requireActivity()
        binding.productDetailsViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productNameTextField.editText?.doOnTextChanged { text, start, before, count ->
            viewModel._productName.value = text.toString()
        }
        binding.productCompanyTextField.editText?.doOnTextChanged { text, start, before, count ->
            viewModel._productCompanyName.value = text.toString()
        }
        binding.productSizeTextField.editText?.doOnTextChanged { text, start, before, count ->
            viewModel._productSize.value = text.toString()
        }
        binding.productDescriptionTextField.editText?.doOnTextChanged { text, start, before, count ->
            viewModel._productDescription.value = text.toString()
        }
        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }

        })
    }

}