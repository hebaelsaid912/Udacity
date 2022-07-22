package com.udacity.shoestore.ui.login.fragment.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.annotation.MenuRes
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentHomeBinding
import com.udacity.shoestore.models.Shoe
import com.udacity.shoestore.ui.login.fragment.home.shared_viewModel.HomeViewModel

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(requireActivity())[HomeViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        viewModel.activity = requireActivity()
        binding.homeViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel._newShoe.observe(requireActivity()) { newShoe ->
            Log.d(TAG, "onViewCreated: ")
            addView(newShoe)
        }
        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }
        })
        binding.menuImageView.setOnClickListener { v ->
            showMenu(v, R.menu.menu)
        }
        binding.addNewProduct.setOnClickListener {
            findNavController().navigate(R.id.action_homeFragment_to_productDetailsFragment)
        }
    }
    private fun showMenu(v: View, @MenuRes menuRes: Int) {
        val popup = PopupMenu(requireContext(), v)
        popup.menuInflater.inflate(menuRes, popup.menu)

        popup.setOnMenuItemClickListener { menuItem: MenuItem ->
            when(menuItem.itemId){
                R.id.option_logout -> {
                    findNavController().navigate(R.id.action_homeFragment_to_loginFragment)
                }
            }
            return@setOnMenuItemClickListener true
        }
        popup.setOnDismissListener {

        }
        // Show the popup menu.
        popup.show()
    }
    private fun addView(shoe: Shoe){
        Log.d(TAG, "addView: name: ${shoe.name}")
        Log.d(TAG, "addView: company: ${shoe.company}")
        Log.d(TAG, "addView: size: ${shoe.size}")
        Log.d(TAG, "addView: description: ${shoe.description}")
       val inflater =
           activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
       val rowView: View = inflater.inflate(R.layout.product_list_item, binding.mainLayout,false)
        binding.mainLayout.addView(rowView,binding.mainLayout.childCount-1)
        rowView.findViewById<TextView>(R.id.product_name_text_view).text = shoe.name
        rowView.findViewById<TextView>(R.id.product_company_name_text_view).text = shoe.company
        rowView.findViewById<TextView>(R.id.product_size_text_view).text = shoe.size.toString()
        rowView.findViewById<TextView>(R.id.product_description_text_view).text = shoe.description
    }

    override fun onResume() {
        super.onResume()
    }
}