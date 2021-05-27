package com.example.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectgumi.R
import com.example.projectgumi.data.model.Catelory
import com.example.projectgumi.data.model.ImageSlideModel
import com.example.projectgumi.data.model.Product
import com.example.projectgumi.data.reposity.MyReposity
import okhttp3.internal.filterList

class ExploreViewModel(private val reposity: MyReposity) : ViewModel() {

    val dataCategory = MutableLiveData(
        mutableListOf(
            Catelory("0", "Pules", R.drawable.pules),
            Catelory("1", "Rice", R.drawable.rice),
            Catelory("2", "Frash Fruits & Vegetable", R.drawable.vegetable),
            Catelory("3", "Cooking Oil & Ghee", R.drawable.oil),
            Catelory("4", "Meat & Fish", R.drawable.meat),
            Catelory("5", "Bakery & Snacks", R.drawable.bakery),
            Catelory("6", "Dairy & Eggs", R.drawable.dairy),
            Catelory("7", "Bevergaes", R.drawable.beverages)
        )
    )

    val dataProduct = MutableLiveData(
        mutableListOf(
            Product("0", "Organic Bananas", "7pcs, Priceg", "$4.99", "0", R.drawable.banana),
            Product("1", "Red Apple", "1kg, Priceg", "$4.99", "0", R.drawable.apple),
            Product("2", "Red Apple", "1kg, Priceg", "$4.99", "1", R.drawable.apple),
            Product("3", "Red Apple", "1kg, Priceg", "$4.99", "1", R.drawable.apple),
            Product("4", "Organic Bananas", "7pcs, Priceg", "$4.99", "2", R.drawable.banana),
            Product("5", "Red Apple", "1kg, Priceg", "$4.99", "2", R.drawable.apple),
            Product("6", "Organic Bananas", "7pcs, Priceg", "$4.99", "3", R.drawable.banana),
            Product("7", "Red Apple", "1kg, Priceg", "$4.99", "3", R.drawable.apple),
            Product("8", "Organic Bananas", "7pcs, Priceg", "$4.99", "4", R.drawable.banana),
            Product("9", "Red Apple", "1kg, Priceg", "$4.99", "4", R.drawable.apple),
            Product("10", "Organic Bananas", "7pcs, Priceg", "$4.99", "5", R.drawable.banana),
            Product("11", "Red Apple", "1kg, Priceg", "$4.99", "5", R.drawable.apple),
            Product("12", "Organic Bananas", "7pcs, Priceg", "$4.99", "6", R.drawable.banana),
            Product("13", "Red Apple", "1kg, Priceg", "$4.99", "6", R.drawable.apple),
            Product("14", "Organic Bananas", "7pcs, Priceg", "$4.99", "7", R.drawable.banana),
            Product("15", "Organic Bananas", "7pcs, Priceg", "$4.99", "7", R.drawable.banana)
        )
    )

    val resultCatelory = MutableLiveData<Catelory?>()
    val resultProduct = MutableLiveData<MutableList<Product>?>()

    fun loadCateloryById(idCatelory: String) {
        resultCatelory.postValue(dataCategory.value?.let {
            it.filter {
                it.cateloryId == idCatelory
            }[0]
        })
    }

    fun loadProductById(idCatelory: String) {
        resultProduct.postValue(dataProduct.value?.let {
            it.filter {
                it.productCatelory == idCatelory
            }.toMutableList()
        })
    }

}