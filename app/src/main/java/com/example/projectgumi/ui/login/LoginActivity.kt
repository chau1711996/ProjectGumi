package com.example.projectgumi.ui.login

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import coil.load
import com.example.projectgumi.R
import com.example.projectgumi.databinding.ActivityLoginBinding
import com.example.projectgumi.sns.SNSLoginActivity
import com.example.projectgumi.ui.signInPhone.PhoneLoginActivity
import com.example.projectgumi.utils.SNSLoginType
import com.example.projectgumi.utils.Utils.SNS_LOGIN_TYPE
import com.example.projectgumi.utils.Utils.SNS_REQUEST_CODE
import com.example.projectgumi.utils.Utils.SNS_RESULT_CODE
import com.example.projectgumi.utils.Utils.SNS_RESULT_DATA
import com.example.projectgumi.utils.Utils.showProgressBar
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var dialog: AlertDialog
    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        currentUser?.let {
            updateUI(it)
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        binding.layoutLogout.visibility = View.GONE
        binding.layoutLogin.visibility = View.VISIBLE
        user?.let {
            binding.layoutLogout.visibility = View.VISIBLE
            binding.layoutLogin.visibility = View.GONE
            binding.imageUser.load(it.photoUrl)
            binding.textEmail.text = it.displayName
            binding.textPhone.text = it.phoneNumber
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    private fun init() {
        //Instance firebase auth
        auth = Firebase.auth

        dialog = showProgressBar(this, "Loading login...")

        instanceGoogleSignIn()

        binding.buttonLoginGg.setOnClickListener {
            signInGg()
        }
        binding.buttonLoginFb.setOnClickListener {
            signInFb()
        }
        binding.buttonLoginPhone.setOnClickListener {
            singInPhone()
        }
        binding.buttonLogout.setOnClickListener {
            signOut()
        }

    }

    private fun instanceGoogleSignIn() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun singInPhone() {
        val intent = Intent(this, PhoneLoginActivity::class.java)
        startActivity(intent)
    }

    private fun signInFb() {
        val intent = Intent(this, SNSLoginActivity::class.java)
        intent.putExtra(SNS_LOGIN_TYPE, SNSLoginType.Facebook)
        startActivityForResult(intent, SNS_REQUEST_CODE)
    }

    private fun signInGg() {
        val intent = Intent(this, SNSLoginActivity::class.java)
        intent.putExtra(SNS_LOGIN_TYPE, SNSLoginType.Google)
        startActivityForResult(intent, SNS_REQUEST_CODE)
    }

    private fun signOut() {
        googleSignInClient.signOut()
        auth.signOut()
        updateUI(null)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == SNS_REQUEST_CODE && resultCode == SNS_RESULT_CODE){
            val user = data?.extras?.get(SNS_RESULT_DATA) as FirebaseUser?
            updateUI(user)
        }
    }
}