package com.example.projectgumi.ui.signInPhone

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.projectgumi.R
import com.example.projectgumi.databinding.ActivityPhoneLoginCodeBinding
import com.example.projectgumi.sns.SNSLoginActivity
import com.example.projectgumi.ui.signInPhone.PhoneLoginActivity.Companion.PHONE_NUMBER_VALUE
import com.google.firebase.auth.FirebaseUser

class PhoneLoginCodeActivity : SNSLoginActivity() {

    private lateinit var binding: ActivityPhoneLoginCodeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneLoginCodeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init(){
        val result = intent.getStringExtra(PHONE_NUMBER_VALUE)?.split(" ")

        var phone = ""
        var id = ""

        result?.let {
            phone = it[0]
            id = it[1]
        }

        binding.apply {
            layoutHead.title.text = phone
            layoutHead.caption.text = "Please enter your CODE"
            flGetCode.setOnClickListener {
                verifyPhoneNumberWithCode(id, phone)
            }
        }
    }

    override fun resultData(data: String?) {
        data?.let{
            Toast.makeText(
                baseContext, "Login phone number success ${it}.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}