package com.example.projectgumi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgumi.R
import com.example.projectgumi.data.model.*
import com.example.projectgumi.data.reposity.MyReposity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.filterList

class AccountViewModel(private val res: MyReposity) : ViewModel() {
    val dataUser = MutableLiveData<UserModel?>()

    val dataAccount = MutableLiveData(
        mutableListOf(
            AccountLayoutModel(R.drawable.ic_order, "Orders"),
            AccountLayoutModel(R.drawable.ic_profile, "Profile"),
            AccountLayoutModel(R.drawable.ic_help, "Help"),
            AccountLayoutModel(R.drawable.ic_about, "About"),
        )
    )

    fun getUserById(userId: String){
        viewModelScope.launch {
            val result = res.getUserById(userId).body()?.data
            dataUser.postValue(result?.get(0))
        }
    }

}