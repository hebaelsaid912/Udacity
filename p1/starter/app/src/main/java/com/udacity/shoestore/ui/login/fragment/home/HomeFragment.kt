package com.udacity.shoestore.ui.login.fragment.home

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.activity.OnBackPressedCallback
import androidx.annotation.MenuRes
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentHomeBinding
import com.udacity.shoestore.databinding.FragmentLoginBinding
import com.udacity.shoestore.models.SharedViewModel
import com.udacity.shoestore.models.Shoe

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    //private lateinit var adapter: ListItemAdapter
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }
    private val sharedViewModel: SharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        binding.homeViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedViewModel._productName.observe(viewLifecycleOwner) { name ->
            Log.d(TAG, "onViewCreated: shoe name: $name")
            viewModel.setProductName(name)
        }
        sharedViewModel._productCompanyName.observe(viewLifecycleOwner) { company ->
            Log.d(TAG, "onViewCreated: shoe name: $company")
            viewModel.setProductCompanyName(company)
        }
        sharedViewModel._productSize.observe(viewLifecycleOwner) { size ->
            Log.d(TAG, "onViewCreated: shoe name: $size")
            viewModel.setProductSize(size)
        }
        sharedViewModel._productDescription.observe(viewLifecycleOwner) { description ->
            Log.d(TAG, "onViewCreated: shoe name: $description")
            viewModel.setProductDescription(description)
        }

        /*viewModel.itemsList.observe(viewLifecycleOwner, Observer { value ->
            setListView(value)
        })*/
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
   /* @SuppressLint("NotifyDataSetChanged")
    private fun setListView(itemsList:ArrayList<Shoe>){
        Log.d(TAG, "setListView: size: ${itemsList.size}")
        adapter = ListItemAdapter(itemsList)
        binding.itemsRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.itemsRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }*/
    private fun addView(){
       val inflater =
           activity?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
       val rowView: View = inflater.inflate(R.layout.product_list_item, null)
        binding.scrollView.addView(rowView,binding.scrollView.childCount-1)

    }
}