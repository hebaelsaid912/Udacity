package com.udacity.shoestore.ui.login.fragment.product_details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import com.udacity.shoestore.databinding.FragmentProductDetailsBinding

class ProductDetailsFragment : Fragment() {
    private lateinit var binding: FragmentProductDetailsBinding
    /*private val viewModel: ProductDetailsViewModel by lazy {
        ViewModelProvider(this)[ProductDetailsViewModel::class.java]
    }*/
    private lateinit var viewModel: ProductDetailsViewModel
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
    }

}