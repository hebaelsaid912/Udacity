package com.udacity.shoestore.models

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {
    val _productName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val _productCompanyName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val _productSize: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val _productDescription: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun setProductName(productName: String) {
        _productName.value = productName
    }
    fun setProductCompanyName(productCompanyName: String) {
        _productCompanyName.value = productCompanyName
    }
    fun setProductDescription(productDescription: String) {
        _productDescription.value = productDescription
    }
    fun setProductSize(productSize: String) {
        _productSize.value = productSize
    }
}