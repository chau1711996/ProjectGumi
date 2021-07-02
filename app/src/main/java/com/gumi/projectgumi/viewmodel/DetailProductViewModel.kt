package com.gumi.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumi.projectgumi.data.model.*
import com.gumi.projectgumi.data.reposity.RoomDBReposity
import com.gumi.projectgumi.data.reposity.MyReposity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailProductViewModel(private val res: MyReposity, private val roomDBRes: RoomDBReposity) : ViewModel() {
    val dataImages = MutableLiveData<MutableList<ProductImages>?>()
    val dataProduct = MutableLiveData<ProductDetail?>()

    var amount = MutableLiveData(1)
    var money = MutableLiveData<String>()
    var statusInsert = MutableLiveData(false)
    var statusFavorite = MutableLiveData<String?>()
    var statusCheckFavorite = MutableLiveData<String?>()

    fun loadDataImages(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataImages.postValue(res.getImageByProductId(productId).body()?.data)
        }
    }

    fun loadDataProduct(productId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = res.getProductById(productId).body()?.data?.get(0)
            dataProduct.postValue(result)
            money.postValue(result?.price)
        }
    }

    fun clickTangAmount() {
        amount.value?.let {
            if (it < 10) {
                amount.postValue(it.plus(1))
                sumMoney(it+1)
            }
        }

    }

    fun clickGiamAmount() {
        amount.value?.let {
            if (it > 1) {
                amount.postValue(it.minus(1))
                sumMoney(it-1)
            }
        }
    }

    fun sumMoney(amount: Int) {
        var x = 0.0
        dataProduct.value?.let {
            x = it.price.substring(1, it.price.length).toDouble()
            x*=amount
        }
        money.postValue("$${x}")
    }

    fun insertCart(){
        viewModelScope.launch {
            dataProduct.value?.let { product ->
                amount.value?.let {
                    dataImages.value?.let{ image ->
                        val cartModel = CartModel(0, product.productId, product.name, product.unit, it, product.price, image.get(0).url)
                        roomDBRes.insertCart(cartModel)
                        statusInsert.postValue(true)
                    }
                }
            }
        }
    }

    fun insertFavorite(userId: String){
        viewModelScope.launch {
            dataProduct.value?.let {
                dataImages.value?.let { image ->
                    val product = FavoriteModel(0, it.productId, it.name, it.unit, it.price, image.get(0).url, userId)
                    statusFavorite.postValue(res.insertFavorite(product).body()?.status)
                }
            }
        }
    }

    fun checkFavorite(productId: Int, userId: String){
        viewModelScope.launch {
            statusCheckFavorite.postValue(res.checkFavorite(productId, userId).body()?.status)
        }
    }
}