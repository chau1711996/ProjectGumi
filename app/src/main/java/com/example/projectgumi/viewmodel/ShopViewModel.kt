package com.example.projectgumi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgumi.R
import com.example.projectgumi.data.model.Catelory
import com.example.projectgumi.data.model.ImageSlideModel
import com.example.projectgumi.data.model.Product
import com.example.projectgumi.data.model.UserModel
import com.example.projectgumi.data.reposity.MyReposity
import com.example.projectgumi.utils.Utils
import kotlinx.coroutines.launch

class ShopViewModel(private val res: MyReposity) : ViewModel() {

    val imageSlideShow = MutableLiveData<MutableList<ImageSlideModel>>()
    val dataExclusive = MutableLiveData<MutableList<Product>?>()
    val dataBestSelling = MutableLiveData<MutableList<Product>?>()
    val dataCategory = MutableLiveData<MutableList<Catelory>?>()
    val dataProduct = MutableLiveData<MutableList<Product>?>()
    val dataUser = MutableLiveData<UserModel?>()

    fun getUserById(userId: String){
        viewModelScope.launch {
            val result = res.getUserById(userId).body()?.data
            dataUser.postValue(result?.get(0))
        }
    }


    fun loadProductByCateloryId(cateloryId: Int){
        viewModelScope.launch {
            dataProduct.postValue(res.getProductByCateloryId(cateloryId).body()?.data)
        }
    }

    fun loadImageSlideShow() {
        imageSlideShow.postValue(
            mutableListOf(
                ImageSlideModel(
                    3,
                    R.drawable.vegetable,
                    "Fresh Fruits & Vegetable"
                ),
                ImageSlideModel(
                    4,
                    R.drawable.oil,
                    "Cooking Oil & Ghee"
                ),
                ImageSlideModel(
                    5,
                    R.drawable.meat,
                    "Meat & Fish"
                ),
                ImageSlideModel(
                    6,
                    R.drawable.bakery,
                    "Bakery & Snacks"
                ),
                ImageSlideModel(
                    7,
                    R.drawable.dairy,
                    "Dairy & Eggs"
                ),
                ImageSlideModel(
                    8,
                    R.drawable.beverages,
                    "Beverages"
                )
            )
        )
    }

    fun loadExclusive() {
        viewModelScope.launch {
            try{
                val result = res.getExclusiveLimit().body()
                result?.status.let {
                    if (it.equals(Utils.SUCCESS))
                        dataExclusive.postValue(result?.data)
                }
            }catch (e: Exception){
                Log.d("chau", e.message.toString())
            }
        }
    }

    fun loadBestSelling() {
        viewModelScope.launch {
            try{
                val result = res.getBestSellingLimit().body()
                result?.status.let {
                    if (it.equals(Utils.SUCCESS))
                        dataBestSelling.postValue(result?.data)
                }
            }catch (e: Exception){
                Log.d("chau", e.message.toString())
            }
        }
    }

    fun loadCatelory() {
        viewModelScope.launch {
            dataCategory.postValue(res.getAllCatelory().body()?.data)
        }
    }

}