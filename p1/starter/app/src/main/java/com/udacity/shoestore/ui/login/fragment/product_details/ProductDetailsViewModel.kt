package com.udacity.shoestore.ui.login.fragment.product_details

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import androidx.core.os.bundleOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.models.Shoe

private const val TAG = "ProductDetailsViewModel"

class ProductDetailsViewModel(val activity: Activity) : ViewModel() {
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

    fun onAddView(view: View) {
        val name = productName.value
        val company = productCompanyName.value
        val size = productSize.value
        val description = productDescription.value

        Log.d(TAG, "onAddView: name: $name")
        Log.d(TAG, "onAddView: company: $company")
        Log.d(TAG, "onAddView: size: $size")
        Log.d(TAG, "onAddView: description: $description")

        activity.findNavController(viewId = view.id).navigate(
            R.id.action_productDetailsFragment_to_homeFragment,
            bundleOf(
                "new_shoe" to Shoe(
                    name = name.toString(),
                    company = company.toString(),
                    size = size!!.toDouble(),
                    description = description.toString()
                )
            )
        )

    }
}