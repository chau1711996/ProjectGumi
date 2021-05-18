package com.example.projectgumi.ui.signInPhone

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
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
        val user = intent?.extras?.get(LoginActivity.LOGIN_RESULT_DATA) as FirebaseUser
        binding.apply {
            layoutHead.caption.text = "Hello ${user.displayName}"
            layoutHead.title.text = "Please enter your mobie phone number"
            layoutHead.imageLeft.setOnClickListener {
                googleSignInClient.signOut()
                auth.signOut()
                startActivity(Intent(this@PhoneLoginActivity, LoginActivity::class.java))
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
//            textResend.setOnClickListener {
//                val phone = editPhoneNumber.text.toString()
//                resendVerificationCode(phone)
//            }
        }

    }

    override fun resultData(user: FirebaseUser?) {
        user?.let {

        }
    }
}