package com.example.projectgumi.ui.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.projectgumi.data.model.Product
import com.example.projectgumi.data.model.UserModel
import com.example.projectgumi.data.reposity.MyReposity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginViewModel(private val reposity: MyReposity) : ViewModel() {
    val status = MutableLiveData<String?>()

    fun createUser(userModel: UserModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                status.postValue(reposity.createUser(userModel).body()?.status)
            }catch (e: Exception){
                Log.i("chaudangAPI", e.message.toString())
            }
        }
    }

}