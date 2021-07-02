package com.gumi.projectgumi.utils

import android.annotation.SuppressLint
import android.net.Uri
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.appcompat.widget.AppCompatSpinner
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.gumi.projectgumi.R
import com.gumi.projectgumi.adapter.*
import com.gumi.projectgumi.data.model.CartModel

object Binding {
    @BindingAdapter("loadImage")
    @JvmStatic
    fun loadImageView(imageView: ImageView, url: Uri?) {
        imageView.load(url){
            placeholder(R.drawable.ic_launcher_background)
            error(R.drawable.ic_launcher_foreground)
        }
    }
    @BindingAdapter("setRating")
    @JvmStatic
    fun setRating(view: RatingBar, rating: String?) {
        rating?.let{
            view.rating = it.toFloat()
        }
    }
    @BindingAdapter("loadImageProduct")
    @JvmStatic
    fun loadImageProduct(imageView: ImageView, image: String) {
        imageView.load(image){
            placeholder(R.drawable.ic_launcher_background)
            error(R.drawable.ic_launcher_foreground)
        }
    }
    @BindingAdapter("loadIcon")
    @JvmStatic
    fun loadIcon(imageView: ImageView, image: Int) {
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
    fun getTextChange(editText: EditText, text: MutableLiveData<String?>) {
        editText.doAfterTextChanged {
            text.postValue(it.toString())
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
    @BindingAdapter("myCartAdapter")
    @JvmStatic
    fun myCartAdapter(rec: RecyclerView, adapterCart: CartAdapter) {
        rec.adapter = adapterCart
        rec.layoutManager = LinearLayoutManager(rec.context)
    }

    @BindingAdapter("accountAdapter")
    @JvmStatic
    fun accountAdapter(rec: RecyclerView, adapterAccount: AccountAdapter) {
        rec.adapter = adapterAccount
        rec.layoutManager = LinearLayoutManager(rec.context)
    }

    @BindingAdapter("favoriteAdapter")
    @JvmStatic
    fun favoriteAdapter(rec: RecyclerView, adapterProduct: FavoriteAdapter) {
        rec.adapter = adapterProduct
        rec.layoutManager = LinearLayoutManager(rec.context)
    }

    @BindingAdapter("setMyText")
    @JvmStatic
    fun setMyText(view: TextView, text: MutableLiveData<String>) {
        text.value?.let {
            var money = it
            if(money.length>6){
                money = money.substring(0,6)
            }
            view.text = money
        }
    }

    @SuppressLint("SetTextI18n")
    @BindingAdapter("sumMoneyCart")
    @JvmStatic
    fun sumMoneyCart(textView: TextView, cart: CartModel) {
        var money = (cart.productPrice.substring(1).toDouble()*cart.amount).toString()
        if(money.length>5){
            money = money.substring(0,5)
        }
        textView.text = "$$money"
    }

}