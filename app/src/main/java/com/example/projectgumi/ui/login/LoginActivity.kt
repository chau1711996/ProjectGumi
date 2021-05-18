package com.example.projectgumi.ui.login

import android.content.Intent
import android.os.Bundle
import com.example.projectgumi.databinding.ActivityLoginBinding
import com.example.projectgumi.sns.SNSLoginActivity
import com.example.projectgumi.ui.signInPhone.PhoneLoginActivity
import com.google.firebase.auth.*


class LoginActivity : SNSLoginActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun updateUI(user: FirebaseUser?) {
        user?.let {
            val intent = Intent(this, PhoneLoginActivity::class.java)
            intent.putExtra(LOGIN_RESULT_DATA, it)
            startActivity(intent)
            finish()
        }
    }

    private fun init() {

        auth.currentUser?.let {
            updateUI(it)
        }

        binding.buttonLoginGg.setOnClickListener {
            signInGoogle()
        }
        binding.buttonLoginFb.setOnClickListener {
            signInFb()
        }

    }

    override fun resultData(user: FirebaseUser?) {
        updateUI(user)
        dialog.dismiss()
    }
    companion object{
        const val LOGIN_RESULT_DATA = "LOGIN_RESULT_DATA"
    }
}