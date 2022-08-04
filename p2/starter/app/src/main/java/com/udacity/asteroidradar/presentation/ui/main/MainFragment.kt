package com.udacity.asteroidradar.presentation.ui.main

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.model.data.Asteroid
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding


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
        viewModel._imageURL.observe(viewLifecycleOwner){ url ->
            Picasso.with(context).load(url).into(binding.activityMainImageOfTheDay)
        }
        viewModel._title.observe(viewLifecycleOwner){ title ->
            binding.titleTv.text = title
        }
        viewModel._asteroid.observe(viewLifecycleOwner){ asteroidList ->
            setAsteroidDataList(asteroidList)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
    private fun setAsteroidDataList(asteroidList:ArrayList<Asteroid>){
        for(i in asteroidList){
            Log.d(TAG, "setAsteroidDataList: date: ${i.closeApproachDate}")
        }
        var adapter = MainAsteroidAdapter(this)
        adapter.setList(requireContext(),asteroidList)
        binding.asteroidRecycler.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
        binding.asteroidRecycler.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    override fun onClickedOnLike(position: Int, product_id: Int, likeBtn: ImageView) {
        TODO("Not yet implemented")
    }

    override fun onClickToIntent(position: Int, product_id: Int) {
        TODO("Not yet implemented")
    }
}
