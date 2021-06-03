package com.example.projectgumi.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgumi.data.model.Catelory
import com.example.projectgumi.data.model.ImageSlideModel
import com.example.projectgumi.data.model.Product
import com.example.projectgumi.data.reposity.MyReposity
import com.example.projectgumi.utils.Utils
import kotlinx.coroutines.launch

class ShopViewModel(private val res: MyReposity) : ViewModel() {

    val imageSlideShow = MutableLiveData<MutableList<ImageSlideModel>>()
    val dataExclusive = MutableLiveData<MutableList<Product>?>()
    val dataBestSelling = MutableLiveData<MutableList<Product>?>()
    val dataCategory = MutableLiveData<MutableList<Catelory>?>()
    val dataProduct = MutableLiveData<MutableList<Product>?>()


    fun loadProductByCateloryId(cateloryId: Int){
        viewModelScope.launch {
            dataProduct.postValue(res.getProductByCateloryId(cateloryId).body()?.data)
        }
    }

    fun loadImageSlideShow() {
        imageSlideShow.postValue(
            mutableListOf(
                ImageSlideModel(
                    0,
                    "https://i.pinimg.com/originals/9d/60/22/9d6022f153768025ad37f51d89d29ece.jpg"
                ),
                ImageSlideModel(
                    1,
                    "https://wall.vn/wp-content/uploads/2020/03/hinh-nen-dep-may-tinh-1.jpg"
                ),
                ImageSlideModel(
                    2,
                    "https://maytinhvui.com/wp-content/uploads/2020/11/hinh-nen-may-tinh-4k-game-min.jpg"
                ),
                ImageSlideModel(
                    3,
                    "https://pdp.edu.vn/wp-content/uploads/2021/01/hinh-nen-4k-tuyet-dep-cho-may-tinh.jpg"
                ),
                ImageSlideModel(
                    4,
                    "https://luongsport.com/wp-content/uploads/2020/10/1039991.jpg"
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