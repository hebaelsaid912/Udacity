package com.udacity.asteroidradar.presentation.ui.main

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.model.data.Asteroid
import com.udacity.asteroidradar.databinding.MainAsteroidItemListBinding

class MainAsteroidAdapter(var listener: ListenerGoToDetails) :
    RecyclerView.Adapter<MainAsteroidAdapter.MainAsteroidViewHolder>() {
    private lateinit var binding:MainAsteroidItemListBinding
    private var mlist = ArrayList<Asteroid>()
    lateinit var context: Context

    class MainAsteroidViewHolder(itemView: MainAsteroidItemListBinding) : RecyclerView.ViewHolder(itemView.root) {
        var name: TextView = itemView.asteroidNum
        var date: TextView = itemView.asteroidDate
        var status: ImageView = itemView.asteroidImg

        fun bind(asteroidItem: Asteroid) {
            name.text = asteroidItem.codename
            date.text = asteroidItem.closeApproachDate
            if (asteroidItem.isPotentiallyHazardous) {
                status.setImageResource(R.drawable.ic_status_potentially_hazardous)
            } else {
                status.setImageResource(R.drawable.ic_status_normal)
            }
        }
        companion object {
            fun from(parent: ViewGroup): MainAsteroidViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = MainAsteroidItemListBinding.inflate(layoutInflater, parent, false)
                return MainAsteroidViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAsteroidViewHolder {
        return MainAsteroidViewHolder.from(parent)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MainAsteroidViewHolder, position: Int) {
        holder.bind(mlist[position])
    }

    override fun getItemCount(): Int {
        return mlist.size
    }

    fun setList(context: Context, mlist: ArrayList<Asteroid>) {
        this.mlist = mlist
        this.context = context
    }
}


interface ListenerGoToDetails {
    fun onClickedOnLike(position: Int, product_id: Int, likeBtn: ImageView)
    fun onClickToIntent(position: Int, product_id: Int)
}