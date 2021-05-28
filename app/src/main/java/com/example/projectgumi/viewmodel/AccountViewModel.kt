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

class AccountViewModel(private val reposity: MyReposity) : ViewModel() {

    val dataAccount = MutableLiveData(
        mutableListOf(
            AccountLayoutModel(R.drawable.ic_order, "Orders"),
            AccountLayoutModel(R.drawable.ic_profile, "Profile"),
            AccountLayoutModel(R.drawable.ic_help, "Help"),
            AccountLayoutModel(R.drawable.ic_about, "About"),
        )
    )

}