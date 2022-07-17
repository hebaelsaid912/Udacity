package com.udacity.shoestore.ui.login.fragment.product_details

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
import com.udacity.shoestore.models.SharedViewModel

class ProductDetailsFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding
    private lateinit var viewModel: ProductDetailsViewModel
    private val sharedViewModel: SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        viewModel= ProductDetailsViewModel(requireActivity())
        binding.productDetailsViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.productNameTextField.editText?.doOnTextChanged { text, start, before, count ->
            viewModel.productName.value = text.toString()
        }
        binding.productCompanyTextField.editText?.doOnTextChanged { text, start, before, count ->
            viewModel.productCompanyName.value = text.toString()
        }
        binding.productSizeTextField.editText?.doOnTextChanged { text, start, before, count ->
            viewModel.productSize.value = text.toString()
        }
        binding.productDescriptionTextField.editText?.doOnTextChanged { text, start, before, count ->
            viewModel.productDescription.value = text.toString()
        }
        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
               findNavController().navigateUp()
            }

        })
    }

}