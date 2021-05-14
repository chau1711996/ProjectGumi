package com.example.projectgumi.ui.login

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
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
import com.example.projectgumi.utils.Utils.TAG
import com.example.projectgumi.utils.Utils.showProgressBar
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var callbackManager: CallbackManager

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
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

    override fun onResume() {
        super.onResume()
        Firebase.auth.currentUser.let {
            it?.let {
                updateUI(it)
            }
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

        instanceFacebookSignIn()

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

    private fun instanceFacebookSignIn() {
        LoginManager.getInstance().registerCallback(callbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                Log.d(TAG, "facebook:onCancel")
            }

            override fun onError(error: FacebookException) {
                Log.d(TAG, "facebook:onError", error)
            }
        })
    }

    private fun singInPhone() {
        val intent = Intent(this, PhoneLoginActivity::class.java)
        startActivity(intent)
    }

    private fun signInFb() {
        LoginManager.getInstance()
            .logInWithReadPermissions(this, arrayListOf("public_profile", "user_friends"))
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

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        dialog.show()
//        callbackManager.onActivityResult(requestCode, resultCode, data)
//
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
//        if (requestCode == RC_SIGN_IN) {
//            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                val account = task.getResult(ApiException::class.java)!!
//                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
//                firebaseAuthWithGoogle(account.idToken!!)
//            } catch (e: ApiException) {
//                // Google Sign In failed, update UI appropriately
//                Log.w(TAG, "Google sign in failed", e)
//            }
//        }
//    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("TAG", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = auth.currentUser
                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

}