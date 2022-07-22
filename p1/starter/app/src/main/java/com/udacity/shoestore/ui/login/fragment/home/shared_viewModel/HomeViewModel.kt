package com.udacity.shoestore.ui.login.fragment.home.shared_viewModel

import android.annotation.SuppressLint
import android.app.Activity
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.navigation.findNavController
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.ui.login.fragment.home.ProductDetailsFragmentDirections

private const val TAG = "HomeViewModel"
class HomeViewModel : ViewModel() {
    @SuppressLint("StaticFieldLeak")
    lateinit var activity: Activity
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
    val _newShoe: MutableLiveData<Shoe> by lazy {
        MutableLiveData<Shoe>()
    }

    fun onCancel(view: View){
        activity.findNavController(viewId = view.id).navigateUp()
    }

    fun onAddView(view: View) {
        val name = _productName.value
        val company = _productCompanyName.value
        val size = _productSize.value
        val description = _productDescription.value
        if (checkValidation(name.toString(),company.toString(),size.toString(),description.toString())){
            Toast.makeText(activity.baseContext,"Data can't be empty", Toast.LENGTH_LONG).show()
        }else {
            _newShoe.value = Shoe(
                name = name.toString(),
                company = company.toString(),
                size = size!!.toDouble(),
                description = description.toString()
            )
            Log.d(TAG, "onAddView: name: $name")
            Log.d(TAG, "onAddView: company: $company")
            Log.d(TAG, "onAddView: size: $size")
            Log.d(TAG, "onAddView: description: $description")
            activity.findNavController(viewId = view.id).navigate(
                ProductDetailsFragmentDirections.actionProductDetailsFragmentToHomeFragment()
            )
        }

    }
    private fun checkValidation(name:String ,company:String ,size:String ,description:String ):Boolean{
        if(name == "null")
            return true
        if (company == "null")
            return true
        if(size == "null")
            return true
        if(description == "null")
            return true
        return false
    }
}