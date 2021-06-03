package com.example.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgumi.data.model.CartModel
import com.example.projectgumi.data.model.ProductDetail
import com.example.projectgumi.data.model.ProductImages
import com.example.projectgumi.data.reposity.CartReposity
import com.example.projectgumi.data.reposity.MyReposity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailProductViewModel(private val res: MyReposity, private val cartRes: CartReposity) : ViewModel() {
    val dataImages = MutableLiveData<MutableList<ProductImages>?>()
    val dataProduct = MutableLiveData<ProductDetail?>()

    var amount = MutableLiveData(1)
    var money = MutableLiveData<String>()
    var statusInsert = MutableLiveData(false)

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

    fun insert(){
        viewModelScope.launch {
            dataProduct.value?.let { product ->
                amount.value?.let {
                    dataImages.value?.let{ image ->
                        val cartModel = CartModel(0, product.productId, product.name, product.unit, it, product.price, image.get(0).url)
                        cartRes.insertCart(cartModel)
                        statusInsert.postValue(true)
                    }
                }
            }
        }
    }
}