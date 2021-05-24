package com.example.projectgumi.sns

import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.projectgumi.R
import com.facebook.CallbackManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.example.projectgumi.utils.Utils
import com.example.projectgumi.utils.Utils.SNS_REQUEST_CODE_GOOGLE
import com.example.projectgumi.utils.Utils.showProgressBar
import com.facebook.AccessToken
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.common.api.ApiException
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

abstract class SNSLoginActivity : AppCompatActivity() {

    private lateinit var fbCallbackManager: CallbackManager



    protected lateinit var dialog: AlertDialog
    protected lateinit var auth: FirebaseAuth
    protected lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        dialog.show()
        super.onActivityResult(requestCode, resultCode, data)
        fbCallbackManager.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        when (requestCode) {
            SNS_REQUEST_CODE_GOOGLE -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(data)
                try {
                    // Google Sign In was successful, authenticate with Firebase
                    val account = task.getResult(ApiException::class.java)!!
                    Log.d(Utils.TAG, "firebaseAuthWithGoogle:" + account.id)
                    firebaseAuthWithGoogle(account.idToken!!)
                } catch (e: ApiException) {
                    // Google Sign In failed, update UI appropriately
                    Log.w(Utils.TAG, "Google sign in failed", e)
                    Toast.makeText(this, "Google sign in failed", Toast.LENGTH_SHORT).show()
                    handleCallbackLoginActivity(null)
                }
            }
        }
    }

    private fun init() {
        auth = Firebase.auth
        instanceFacebookSignIn()
        instanceGoogleSignIn()
        dialog = showProgressBar(this, "Loading...")
    }

    // handle google
    //begin
    protected fun signInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, SNS_REQUEST_CODE_GOOGLE)
    }

    private fun instanceGoogleSignIn() {
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnSuccessListener { task ->
                handleCallbackLoginActivity(task.user)
            }
            .addOnFailureListener {
                handleCallbackLoginActivity(null)
                Toast.makeText(
                    baseContext,
                    "Login google failed",
                    Toast.LENGTH_SHORT
                ).show()
            }
    }
    //end


    // handle facebook
    //begin
    protected fun signInFb() {
        LoginManager.getInstance()
            .logInWithReadPermissions(this, arrayListOf("public_profile", "user_friends"))
    }

    private fun instanceFacebookSignIn() {
        fbCallbackManager = CallbackManager.Factory.create()
        LoginManager.getInstance().registerCallback(fbCallbackManager, object :
            FacebookCallback<LoginResult> {
            override fun onSuccess(loginResult: LoginResult) {
                Log.d(Utils.TAG, "facebook:onSuccess:$loginResult")
                handleFacebookAccessToken(loginResult.accessToken)
            }

            override fun onCancel() {
                handleCallbackLoginActivity(null)
                Toast.makeText(
                    baseContext, "facebook cancel.",
                    Toast.LENGTH_SHORT
                ).show()
            }

            override fun onError(error: FacebookException) {
                Log.d(Utils.TAG, "facebook:onError", error)
            }
        })
    }

    private fun handleFacebookAccessToken(token: AccessToken) {
        Log.d("TAG", "handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:success")
                    val user = auth.currentUser
                    handleCallbackLoginActivity(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        baseContext, "Login facebook failed.",
                        Toast.LENGTH_SHORT
                    ).show()
                    handleCallbackLoginActivity(null)
                }
            }
    }
    //end facebook


    // handle phone
    //begin



    //end phone

    private fun handleCallbackLoginActivity(user: FirebaseUser?) {
        user?.let {
            resultData(it.uid)
        }
        dialog.dismiss()
    }

    abstract fun resultData(data: String?)
}