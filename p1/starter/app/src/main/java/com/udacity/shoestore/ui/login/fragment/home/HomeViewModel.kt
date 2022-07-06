package com.udacity.shoestore.ui.login.fragment.home

import androidx.lifecycle.ViewModel
import com.udacity.shoestore.models.Shoe

class HomeViewModel : ViewModel() {

    private var itemsList = ArrayList<Shoe>()
    init {
        setShoeItems()
    }

    private fun setShoeItems() {
        itemsList.add(
            Shoe(
                "Shoe 1 ",
                41.5,
                "Brand 1",
                "this is description",
                listOf("https://cdn.shopwesternoutfitters.com/6815-large_default/canvas-slip-on-shoe-tooled-leather-print.jpg")
            )
        )
        itemsList.add(
            Shoe(
                "Shoe 2 ",
                40.0,
                "Brand 1",
                "this is description this is description this is description",
                listOf("https://s7d4.scene7.com/is/image/WolverineWorldWide/WF66451_B?wid=1600&hei=1320&fmt=jpeg&qlt=80,0&op_sharpen=0&resMode=bilin&op_usm=0.5,1.0,8,0&iccEmbed=0&printRes=72",
                    "https://s7d4.scene7.com/is/image/WolverineWorldWide/WF66451_D?wid=1600&hei=1320&fmt=jpeg&qlt=80,0&op_sharpen=0&resMode=bilin&op_usm=0.5,1.0,8,0&iccEmbed=0&printRes=72",
                    "https://s7d4.scene7.com/is/image/WolverineWorldWide/WF66451_C?wid=1600&hei=1320&fmt=jpeg&qlt=80,0&op_sharpen=0&resMode=bilin&op_usm=0.5,1.0,8,0&iccEmbed=0&printRes=72",
                    "https://s7d4.scene7.com/is/image/WolverineWorldWide/WF66451_E?wid=1600&hei=1320&fmt=jpeg&qlt=80,0&op_sharpen=0&resMode=bilin&op_usm=0.5,1.0,8,0&iccEmbed=0&printRes=72")
            )
        )
        itemsList.add(
            Shoe(
                "Shoe 3 ",
                40.0,
                "Brand 1",
                "this is description this is description this is description this is description this is description ",
                listOf("https://s7d4.scene7.com/is/image/WolverineWorldWide/WF34000_B?wid=1600&hei=1320&fmt=jpeg&qlt=80,0&op_sharpen=0&resMode=bilin&op_usm=0.5,1.0,8,0&iccEmbed=0&printRes=72",
                    "https://s7d4.scene7.com/is/image/WolverineWorldWide/AW20_WF34000_01?wid=1600&hei=1320&fmt=jpeg&qlt=80,0&op_sharpen=0&resMode=bilin&op_usm=0.5,1.0,8,0&iccEmbed=0&printRes=72",
                    "https://s7d4.scene7.com/is/image/WolverineWorldWide/WF34000_C?wid=1600&hei=1320&fmt=jpeg&qlt=80,0&op_sharpen=0&resMode=bilin&op_usm=0.5,1.0,8,0&iccEmbed=0&printRes=72",
                    "https://s7d4.scene7.com/is/image/WolverineWorldWide/AW20_WF34000_02?wid=1600&hei=1320&fmt=jpeg&qlt=80,0&op_sharpen=0&resMode=bilin&op_usm=0.5,1.0,8,0&iccEmbed=0&printRes=72")
            )
        )
        itemsList.add(
            Shoe(
                "Shoe 4 ",
                39.0,
                "Brand 1",
                "this is description this is description ",
                listOf("https://m.media-amazon.com/images/I/61gOVVrtDxL._UY695_.jpg")
            )
        )
        itemsList.add(
            Shoe(
                "Shoe 5 ",
                39.0,
                "Brand 1",
                "this is description this is description ",
                listOf("https://m.media-amazon.com/images/I/91Og1QdmtsL._UY695_.jpg",
                    "https://m.media-amazon.com/images/I/81zOXWdPd8L._UY695_.jpg",
                    "https://m.media-amazon.com/images/I/81T7PHHH4FL._UY695_.jpg")
            )
        )
    }
    fun getProductsList():ArrayList<Shoe>{
        return itemsList
    }
}