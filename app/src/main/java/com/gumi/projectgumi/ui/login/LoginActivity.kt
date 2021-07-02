package com.gumi.projectgumi.ui.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.gumi.projectgumi.MainActivity
import com.gumi.projectgumi.R
import com.gumi.projectgumi.databinding.ActivityLoginBinding
import com.gumi.projectgumi.sns.SNSLoginActivity
import com.gumi.projectgumi.ui.account.ProfileActivity
import com.gumi.projectgumi.ui.signInPhone.PhoneLoginActivity
import com.gumi.projectgumi.viewmodel.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : SNSLoginActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val model by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.lifecycleOwner = this
        binding.imageLeft.setOnClickListener {
            loginHome()
        }

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