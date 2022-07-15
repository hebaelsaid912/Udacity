package com.udacity.shoestore.ui.login.fragment.product_details

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

private const val TAG = "ProductDetailsViewModel"
class ProductDetailsViewModel : ViewModel() {
    val productName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val productCompanyName: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val productSize: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
    val productDescription: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun onAddView(){
        val name = productName.value
        val company = productCompanyName.value
        val size = productSize.value
        val description = productDescription.value

        Log.d(TAG, "onAddView: name: $name")
        Log.d(TAG, "onAddView: company: $company")
        Log.d(TAG, "onAddView: size: $size")
        Log.d(TAG, "onAddView: description: $description")
        

    }
}