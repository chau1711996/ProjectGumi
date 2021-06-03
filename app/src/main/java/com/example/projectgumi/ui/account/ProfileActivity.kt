package com.example.projectgumi.ui.account

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import coil.load
import com.example.projectgumi.MainActivity
import com.example.projectgumi.R
import com.example.projectgumi.databinding.ActivityProfileBinding
import com.example.projectgumi.ui.signInPhone.PhoneLoginActivity.Companion.USER_ID
import com.example.projectgumi.utils.Utils
import com.example.projectgumi.viewmodel.LoginViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val model by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,  R.layout.activity_profile)

        val district = arrayOf("Quận 1","Quận 2","Quận 3","Quận 4","Quận 5","Quận 6","Quận 7","Quận 8","Quận 9","Quận 10","Quận 11","Quận 12","Gò Vấp","Tân Bình")
        val city = arrayOf("Thành Phố Hồ Chí Minh", "Hà Nội")
        val wards = arrayOf("Phường 1","Phường 2","Phường 3","Phường 4","Phường 5","Phường 6","Phường 7","Phường 8","Phường 9")
        val districtAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, district)
        val wardstAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, wards)
        val citytAdapter = ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, city)



        binding.apply {
            lifecycleOwner = this@ProfileActivity
            spinnerCity.adapter = citytAdapter
            spinnerDistrict.adapter = districtAdapter
            spinnerWards.adapter = wardstAdapter
            loginViewModel = model
        }

        model.status.observe(this){
            it?.let {
                if(it.equals(Utils.SUCCESS_CITY)){
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }
        }
    }
}