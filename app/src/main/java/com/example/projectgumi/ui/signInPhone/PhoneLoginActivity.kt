package com.example.projectgumi.ui.signInPhone

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.projectgumi.databinding.ActivityPhoneLoginBinding
import com.example.projectgumi.sns.SNSLoginActivity
import com.example.projectgumi.ui.login.LoginActivity
import com.google.firebase.auth.*

class PhoneLoginActivity : SNSLoginActivity() {
    private lateinit var binding: ActivityPhoneLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init() {
        binding.apply {
            layoutHead.title.text = "Hello ${auth.currentUser?.displayName}"
            layoutHead.caption.text = "Please enter your mobie phone number"
            layoutHead.imageLeft.setOnClickListener {
                googleSignInClient.signOut()
                auth.signOut()
                val intent = Intent(this@PhoneLoginActivity, LoginActivity::class.java)
                startActivity(intent)
                overridePendingTransition(com.example.projectgumi.R.anim.slide_in_right, com.example.projectgumi.R.anim.slide_out_left)
                finish()
            }
            flGetCode.setOnClickListener {
                val phone = editPhoneNumber.text.toString()
                if (phone.isEmpty() || phone.length != 9) {
                    Toast.makeText(
                        this@PhoneLoginActivity,
                        "Please enter phone number without 0",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    signInPhone(phone)
                }
            }
        }

    }

    override fun resultData(data: String?) {
        data?.let {
            val intent = Intent(this@PhoneLoginActivity, PhoneLoginCodeActivity::class.java)
            intent.putExtra(PHONE_NUMBER_VALUE, it)
            startActivity(intent)
            overridePendingTransition(com.example.projectgumi.R.anim.slide_out_left, com.example.projectgumi.R.anim.slide_in_right)
            finish()
        }
    }
    companion object{
        const val PHONE_NUMBER_VALUE = "PHONE_NUMBER_VALUE"
    }
}