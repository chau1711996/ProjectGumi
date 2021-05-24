package com.example.projectgumi.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.example.projectgumi.MainActivity
import com.example.projectgumi.R
import com.example.projectgumi.databinding.ActivityLoginBinding
import com.example.projectgumi.sns.SNSLoginActivity
import com.example.projectgumi.ui.account.ProfileActivity
import com.example.projectgumi.ui.signInPhone.PhoneLoginActivity
import com.example.projectgumi.ui.signInPhone.PhoneLoginActivity.Companion.USER_ID
import com.example.projectgumi.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : SNSLoginActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val model by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        googleSignInClient.signOut()
        auth.signOut()

        init()

        model.status.observe(this) {
            it?.let {
                when (it) {
                    "phone" -> loginPhoneNumber()
                    "profile" -> loginProfile()
                    "login" -> loginHome()
                    else -> Toast.makeText(baseContext, "Connect failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun loginProfile() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    private fun loginHome() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    private fun loginPhoneNumber() {
        val intent = Intent(this, PhoneLoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    private fun checkUser(userId: String) {
        model.checkUser(userId)
    }

    private fun init() {

        binding.buttonLoginGg.setOnClickListener {
            signInGoogle()
        }
        binding.buttonLoginFb.setOnClickListener {
            signInFb()
        }

    }

    override fun resultData(data: String?) {
        data?.let {
            checkUser(it)
        }
    }
}