package com.example.projectgumi.utils

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.projectgumi.R
import com.example.projectgumi.adapter.CartAdapter
import com.example.projectgumi.adapter.CateloryAdapter
import com.example.projectgumi.adapter.ProductItemAdapter
import com.example.projectgumi.data.model.CartModel
import com.google.android.material.textfield.TextInputEditText

object Binding {
    @BindingAdapter("loadImage")
    @JvmStatic
    fun loadImageView(imageView: ImageView, url: Uri?) {
        imageView.load(url){
            placeholder(R.drawable.ic_launcher_background)
            error(R.drawable.ic_launcher_foreground)
        }
    }
    @BindingAdapter("loadImageProduct")
    @JvmStatic
    fun loadImageProduct(imageView: ImageView, image: Int) {
        imageView.load(image){
            placeholder(R.drawable.ic_launcher_background)
            error(R.drawable.ic_launcher_foreground)
        }
    }
    @BindingAdapter("getItemSpinner")
    @JvmStatic
    fun getItemSpinner(spinner: AppCompatSpinner, textItemChoose: MutableLiveData<String?>) {
        textItemChoose.postValue(spinner.selectedItem.toString())
    }
    @BindingAdapter("getTextChange")
    @JvmStatic
    fun getTextChange(textView: TextInputEditText, textItemChoose: MutableLiveData<String?>) {
        textView.doAfterTextChanged {
            textItemChoose.postValue(it.toString())
        }
    }

    @BindingAdapter("productItemAdapter")
    @JvmStatic
    fun productItemAdapter(rec: RecyclerView, adapterProductItem: ProductItemAdapter) {
        rec.adapter = adapterProductItem
        rec.layoutManager = LinearLayoutManager(rec.context, LinearLayoutManager.HORIZONTAL, false)
    }
    @BindingAdapter("cateloryItemAdapter")
    @JvmStatic
    fun cateloryItemAdapter(rec: RecyclerView, adapterCatelory: CateloryAdapter) {
        rec.adapter = adapterCatelory
        rec.layoutManager = LinearLayoutManager(rec.context, LinearLayoutManager.HORIZONTAL, false)
    }
    @BindingAdapter("exploreItemAdapter")
    @JvmStatic
    fun exploreItemAdapter(rec: RecyclerView, adapterCatelory: CateloryAdapter) {
        rec.adapter = adapterCatelory
        rec.layoutManager = GridLayoutManager(rec.context, 2)
    }

    @BindingAdapter("exploreDetailAdapter")
    @JvmStatic
    fun exploreDetailAdapter(rec: RecyclerView, adapterProduct: ProductItemAdapter) {
        rec.adapter = adapterProduct
        rec.layoutManager = GridLayoutManager(rec.context, 2)
    }
    @BindingAdapter("cartAdapter")
    @JvmStatic
    fun cartAdapter(rec: RecyclerView, adapterCart: CartAdapter) {
        rec.adapter = adapterCart
        rec.layoutManager = LinearLayoutManager(rec.context)
    }

    @BindingAdapter("favoriteAdapter")
    @JvmStatic
    fun favoriteAdapter(rec: RecyclerView, adapterProduct: ProductItemAdapter) {
        rec.adapter = adapterProduct
        rec.layoutManager = LinearLayoutManager(rec.context)
    }

    @BindingAdapter("mySetText")
    @JvmStatic
    fun mySetText(textView: TextView, name: String?) {
        textView.text = name ?: ""
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("sumMoneyCart")
    @JvmStatic
    fun sumMoneyCart(textView: TextView, cart: CartModel) {
        var money = (cart.product.productMoney.substring(1).toDouble()*cart.unit.toDouble()).toString()
        if(money.length>5){
            money = money.substring(0,5)
        }
        textView.text = "$$money"
    }

}