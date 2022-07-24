package com.udacity.asteroidradar.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.Model.PlanetaryApodModel
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.api.NasaApi
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import retrofit2.Call
import retrofit2.Response


private const val TAG = "MainFragment"
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentMainBinding.inflate(inflater)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getMarsRealEstateProperties()
    }
    private fun getMarsRealEstateProperties() {
        NasaApi.retrofitService.getPlanetaryApod().enqueue(object: retrofit2.Callback<PlanetaryApodModel> {
            override fun onResponse(
                call: Call<PlanetaryApodModel>,
                response: Response<PlanetaryApodModel>
            ) {
                Log.d(TAG, "onResponse: ${response.isSuccessful}")
                Log.d(TAG, "onResponse: ${response.body()?.date}")
                Picasso.with(requireContext()).load(response.body()?.url).into(binding.activityMainImageOfTheDay)
            }

            override fun onFailure(call: Call<PlanetaryApodModel>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return true
    }
}
