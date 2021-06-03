package com.example.projectgumi.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgumi.data.reposity.MyReposity
import com.example.projectgumi.utils.Utils.INSERT_CITY
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val reposity: MyReposity) : ViewModel() {
    val auth = Firebase.auth
    val status = MutableLiveData<String?>()
    var uriImage: Uri? = auth.currentUser?.photoUrl
    val city = MutableLiveData<String?>()
    val wards = MutableLiveData<String?>()
    val district = MutableLiveData<String?>()
    val userName = MutableLiveData<String?>(auth.currentUser?.displayName)
    val house = MutableLiveData<String?>()

    fun checkUser(userId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = reposity.checkUser(userId).body()
                status.postValue(result?.status)
            } catch (e: Exception) {
                Log.i("chauAPI", e.message.toString())
            }
        }
    }

    fun onClick(){
        updateAddress()
    }

    fun updateAddress(){
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val result = reposity.updateAddress(INSERT_CITY, auth.uid?:"", city.value?:"",district.value?:"",wards.value?:"",house.value?:"")
                status.postValue(result.body()?.status)
            }catch (e: Exception) {
                Log.i("chaudangAPI", e.message.toString())
            }
        }
    }


    fun updatePhoneUser(key: String, userId: String, userName: String, phoneNumber: String) {
        viewModelScope.launch(Dispatchers.IO){
            try {
                status.postValue(reposity.updatePhoneUser(key, userId, userName, phoneNumber).body()?.status)
            } catch (e: Exception) {
                Log.i("chauAPI", e.message.toString())
            }
        }
    }

}