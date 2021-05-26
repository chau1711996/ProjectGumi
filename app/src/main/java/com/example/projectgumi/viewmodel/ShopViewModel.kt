package com.example.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.projectgumi.R
import com.example.projectgumi.data.model.Catelory
import com.example.projectgumi.data.model.ImageSlideModel
import com.example.projectgumi.data.model.Product
import com.example.projectgumi.data.reposity.MyReposity

class ShopViewModel(private val reposity: MyReposity) : ViewModel() {

    val imageSlideShow = MutableLiveData<MutableList<ImageSlideModel>>()
    val dataExclusive = MutableLiveData<MutableList<Product>?>()
    val dataBestSelling = MutableLiveData<MutableList<Product>?>()
    val dataCategory = MutableLiveData<MutableList<Catelory>?>()
    val dataProduct = MutableLiveData<MutableList<Product>?>()

    fun loadProductCatelory(cateloryId: String){
        dataProduct.postValue(dataBestSelling.value?.filter {
            it.productCatelory.equals(cateloryId)
        }?.toMutableList())
    }

    fun loadImage() {
        imageSlideShow.postValue(
            mutableListOf(
                ImageSlideModel(
                    "0",
                    "https://i.pinimg.com/originals/9d/60/22/9d6022f153768025ad37f51d89d29ece.jpg"
                ),
                ImageSlideModel(
                    "1",
                    "https://wall.vn/wp-content/uploads/2020/03/hinh-nen-dep-may-tinh-1.jpg"
                ),
                ImageSlideModel(
                    "2",
                    "https://maytinhvui.com/wp-content/uploads/2020/11/hinh-nen-may-tinh-4k-game-min.jpg"
                ),
                ImageSlideModel(
                    "3",
                    "https://pdp.edu.vn/wp-content/uploads/2021/01/hinh-nen-4k-tuyet-dep-cho-may-tinh.jpg"
                ),
                ImageSlideModel(
                    "4",
                    "https://luongsport.com/wp-content/uploads/2020/10/1039991.jpg"
                )
            )
        )
    }

    fun loadExclusive() {
        dataExclusive.postValue(
            mutableListOf(
                Product("0", "Red Apple", "1kg, Priceg", "$4.99", "1", R.drawable.apple),
                Product("1", "Organic Bananas", "7pcs, Priceg", "$4.99", "0", R.drawable.banana),
                Product("2", "Red Apple", "1kg, Priceg", "$4.99", "1", R.drawable.apple),
                Product("3", "Organic Bananas", "7pcs, Priceg", "$4.99", "0", R.drawable.banana),
                Product("4", "Red Apple", "1kg, Priceg", "$4.99", "1", R.drawable.apple)
            )
        )
    }

    fun loadBestSelling() {
        dataBestSelling.postValue(
            mutableListOf(
                Product("1", "Organic Bananas", "7pcs, Priceg", "$4.99", "0", R.drawable.banana),
                Product("0", "Red Apple", "1kg, Priceg", "$4.99", "1", R.drawable.apple),
                Product("2", "Red Apple", "1kg, Priceg", "$4.99", "1", R.drawable.apple),
                Product("4", "Red Apple", "1kg, Priceg", "$4.99", "1", R.drawable.apple),
                Product("3", "Organic Bananas", "7pcs, Priceg", "$4.99", "0", R.drawable.banana),
                Product("3", "Organic Bananas", "7pcs, Priceg", "$4.99", "0", R.drawable.banana)
            )
        )
    }

    fun loadCatelory() {
        dataCategory.postValue(
            mutableListOf(
                Catelory("0", "Pules", R.drawable.pules),
                Catelory("1", "Rice", R.drawable.rice),
                Catelory("1", "Rice", R.drawable.rice),
                Catelory("0", "Pules", R.drawable.pules),
                Catelory("1", "Rice", R.drawable.rice)
            )
        )
    }

}