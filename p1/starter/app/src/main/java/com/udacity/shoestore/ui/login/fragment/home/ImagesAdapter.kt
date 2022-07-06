package com.udacity.shoestore.ui.login.fragment.home

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.udacity.shoestore.R
import kotlinx.android.synthetic.main.product_images_list_item.view.*
import java.net.URL

private const val TAG = "ImagesAdapter"
class ImagesAdapter(private var shoeImagesList : List<String>) :  RecyclerView.Adapter<ImagesAdapter.ShoeImagesViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoeImagesViewHolder {
        context = parent.context
        return ShoeImagesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.product_images_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ShoeImagesViewHolder, position: Int) {
        holder.bind(shoeImagesList[position])

    }

    override fun getItemCount(): Int {
        return shoeImagesList.size
    }
    inner class ShoeImagesViewHolder(view: View): RecyclerView.ViewHolder(view) {
        var productImage = view.product_image_view
        @SuppressLint("SetTextI18n")
        fun bind(item: String){
            Log.d(TAG, "bind: url: $item")
            val link = URL(item)
            val url = GlideUrl(link)
            Glide.with(context)
                .load(url)
                .into(productImage)

        }
    }
}