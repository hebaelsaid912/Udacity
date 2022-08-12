package com.udacity.asteroidradar.presentation.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.model.data.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import com.udacity.asteroidradar.model.local.entities.AsteroidModel


private const val TAG = "MainFragment"
class MainFragment : Fragment(), ListenerGoToDetails{

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(isOnline(requireContext())){
            viewModel.getPlanetaryApodImageFromAPI(requireContext())
            viewModel.getFeedsByDate(requireContext())
        }else{
            viewModel.getPicOfDayFromDB(requireContext())
            viewModel.getAllAsteroidFromDB(requireContext())
        }
        viewModel._imageURL.observe(viewLifecycleOwner){ url ->
            Picasso.with(context)
                .load(url)
                .placeholder(R.drawable.placeholder_picture_of_day)
                .error(R.drawable.placeholder_picture_of_day).into(binding.activityMainImageOfTheDay)

        }
        viewModel._title.observe(viewLifecycleOwner){ title ->
            binding.titleTv.text = title
        }
        viewModel._asteroid.observe(viewLifecycleOwner){ asteroidList ->
            if(asteroidList.isEmpty()){
                binding.emptyAsteroidList.visibility = View.VISIBLE
            }else{
                setAsteroidDataList(asteroidList)
            }

        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected: selected item: ${item.itemId}")
        when(item.itemId){
            R.id.show_all_menu -> {
                viewModel.getAllAsteroidFromDB(requireContext())
            }
            R.id.show_buy_menu -> {
                viewModel.getAllAsteroidFromDB(requireContext())
            }
            R.id.show_rent_menu -> {
                viewModel.getTodayAsteroidFromDB(requireContext())
            }
        }
        return true
    }
    private fun setAsteroidDataList(asteroidList:ArrayList<AsteroidModel>){
        var adapter = MainAsteroidAdapter(this)
        adapter.setList(requireContext(),asteroidList)
        binding.asteroidRecycler.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.asteroidRecycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onClick(asteroidItem: Asteroid) {
        findNavController().navigate(MainFragmentDirections.actionShowDetail(asteroidItem))
    }

    /**
     * this function to check if device connect to the internet
     * https://stackoverflow.com/questions/51141970/check-internet-connectivity-android-in-kotlin
     */
    fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }
}
