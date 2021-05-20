package com.example.projectgumi.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.projectgumi.R
import com.example.projectgumi.data.model.UserModel
import com.example.projectgumi.databinding.ActivityLoginBinding
import com.example.projectgumi.sns.SNSLoginActivity
import com.example.projectgumi.ui.signInPhone.PhoneLoginActivity
import com.google.firebase.auth.*
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
                when(it){
                    "login" -> loginMain()
                    "success" -> loginPhoneNumber()
                    "error" -> Toast.makeText(baseContext, "Connect failed", Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun loginMain(){
        Toast.makeText(baseContext, "loginMain", Toast.LENGTH_SHORT).show()
        loginPhoneNumber()
    }

    private fun loginPhoneNumber(){
        val intent = Intent(this, PhoneLoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    private fun createUser() {
        auth.currentUser?.let {
            Log.i("chaudangAPI", "${it.uid} ${it.displayName}")

            model.createUser(
                UserModel(
                    userId = it.uid,
                    userName = it.displayName ?: ""
                )
            )
        }
    }

    private fun init() {

        auth.currentUser?.let {
            createUser()
        }

        binding.buttonLoginGg.setOnClickListener {
            signInGoogle()
        }
        binding.buttonLoginFb.setOnClickListener {
            signInFb()
        }

    }

    override fun resultData(data: String?) {
        data?.let{
            createUser()
        }
    }

    companion object {
        const val LOGIN_RESULT_DATA = "LOGIN_RESULT_DATA"
    }
}