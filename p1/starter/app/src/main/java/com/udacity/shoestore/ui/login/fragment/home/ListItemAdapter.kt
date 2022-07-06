package com.udacity.shoestore.ui.login.fragment.home

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.udacity.shoestore.R
import com.udacity.shoestore.models.Shoe
import kotlinx.android.synthetic.main.product_list_item.view.*

private const val TAG = "ListItemAdapter"
class ListItemAdapter(private var shoesListItems : ArrayList<Shoe>) :  RecyclerView.Adapter<ListItemAdapter.ShoesViewHolder>() {
    private lateinit var context: Context
    private lateinit var imagesAdapter : ImagesAdapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShoesViewHolder {
        context = parent.context
        return ShoesViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.product_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ShoesViewHolder, position: Int) {
        holder.bind(shoesListItems[position])

    }

    override fun getItemCount(): Int {
        return shoesListItems.size
    }
    inner class ShoesViewHolder(view:View): RecyclerView.ViewHolder(view) {
        var productImages = view.product_images_list
        var productName = view.product_name_text_view
        var productCompany = view.product_company_name_text_view
        var productSize = view.product_size_text_view
        var productDescription = view.product_description_text_view
        @SuppressLint("SetTextI18n")
        fun bind(item: Shoe){
            Log.d(TAG, "bind: images list size: ${item.images.size}")
            imagesAdapter = ImagesAdapter(item.images)
            productImages.adapter = imagesAdapter
            productImages.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            productName.text = item.name
            productCompany.text = item.company
            productSize.text = "Size: ${item.size}"
            productDescription.text = item.description

        }
    }
}