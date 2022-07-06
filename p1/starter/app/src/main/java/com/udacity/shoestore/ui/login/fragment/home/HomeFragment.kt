package com.udacity.shoestore.ui.login.fragment.home

import android.annotation.SuppressLint
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
import androidx.recyclerview.widget.GridLayoutManager
import com.udacity.shoestore.R
import com.udacity.shoestore.databinding.FragmentHomeBinding
import com.udacity.shoestore.databinding.FragmentLoginBinding
import com.udacity.shoestore.models.Shoe

private const val TAG = "HomeFragment"

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private var itemsList = ArrayList<Shoe>()
    private lateinit var adapter: ListItemAdapter
    private val viewModel: HomeViewModel by lazy {
        ViewModelProvider(this)[HomeViewModel::class.java]
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        itemsList = viewModel.getProductsList()
        setListView()
        activity?.onBackPressedDispatcher?.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                activity?.finish()
            }

        })
        binding.menuImageView.setOnClickListener { v ->
            showMenu(v, R.menu.menu)
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
    @SuppressLint("NotifyDataSetChanged")
    private fun setListView(){
        Log.d(TAG, "setListView: ")
        adapter = ListItemAdapter(itemsList)
        binding.itemsRecyclerView.layoutManager = GridLayoutManager(requireContext(),2)
        binding.itemsRecyclerView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

}