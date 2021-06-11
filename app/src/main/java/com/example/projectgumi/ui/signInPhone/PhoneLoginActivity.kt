package com.example.projectgumi.ui.signInPhone

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doAfterTextChanged
import com.example.gumiproject8.utils.hide
import com.example.gumiproject8.utils.hideKeyboard
import com.example.gumiproject8.utils.show
import com.example.projectgumi.R
import com.example.projectgumi.databinding.ActivityPhoneLoginBinding
import com.example.projectgumi.ui.account.ProfileActivity
import com.example.projectgumi.ui.login.LoginActivity
import com.example.projectgumi.utils.Utils
import com.example.projectgumi.utils.Utils.INSERT_PHONE
import com.example.projectgumi.utils.Utils.SUCCESS_PHONE
import com.example.projectgumi.viewmodel.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class PhoneLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneLoginBinding
    private lateinit var phoneCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var forceResend: PhoneAuthProvider.ForceResendingToken
    private lateinit var verificationId: String
    private lateinit var dialog: AlertDialog
    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val model by viewModel<LoginViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dialog = Utils.showProgressBar(this, "Verifying Phone Number...")
        auth = Firebase.auth
        init()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        model.status.observe(this) {
            it?.let {
                if (it.equals(SUCCESS_PHONE)) {
                    loginProfile()
                } else {
                    Toast.makeText(baseContext, "Error" , Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }

    private fun init() {

        binding.apply {
            editCode.doAfterTextChanged {
                textError.text = ""
            }
            editPhoneNumber.doAfterTextChanged {
                textError.text = ""
            }
        }

        updateUI(false)

        binding.apply {
            layoutHead.imageLeft.setOnClickListener {
                googleSignInClient.signOut()
                Firebase.auth.signOut()
                loginActivity()
            }
            imageLeftCode.setOnClickListener {
                updateUI(false)
            }
            flGetCode.setOnClickListener {
                dialog.show()
                val phone = editPhoneNumber.text.toString()
                checkPhoneAndSendCode(phone)
                hideKeyboard()
            }
            textResend.setOnClickListener {
                dialog.show()
                resendVerificationCode(editPhoneNumber.text.toString())
            }
            flSendCode.setOnClickListener {
                dialog.show()
                verifyPhoneNumberWithCode(editCode.text.toString())
            }
        }
    }

    private fun checkPhoneAndSendCode(phone: String) {
        if (phone.isEmpty() || phone.length != 9) {
            binding.textError.text = "Please enter phone number without 0"
            dialog.dismiss()
        } else {
            signInPhone(phone)
        }
    }

    private fun loginProfile() {
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
        finish()
    }

    @SuppressLint("SetTextI18n")
    private fun updateUI(check: Boolean) {

        binding.apply {
            layoutHead.title.text = "Hello ${auth.currentUser?.displayName}"
            layoutHead.caption.text = "Please enter your mobie phone number"
            textError.text = ""
            if (check) {
                layoutHead.caption.text = "Please enter your CODE"
                flGetCode.hide()
                layoutPhone.hide()
                flSendCode.show()
                textResend.show()
                editCode.show()
                textError.show()
                imageLeftCode.show()
                layoutHead.imageLeft.hide()
            } else {
                imageLeftCode.hide()
                layoutHead.imageLeft.show()
                flGetCode.show()
                layoutPhone.show()
                flSendCode.hide()
                textResend.hide()
                editCode.hide()
            }
        }
    }

    private fun loginActivity() {
        val intent = Intent(this@PhoneLoginActivity, LoginActivity::class.java)
        startActivity(intent)
        overridePendingTransition(
            R.anim.slide_in_right,
            R.anim.slide_out_left
        )
        finish()
    }

    protected fun signInPhone(phone: String) {
        instancePhoneSignIn()
        startPhoneNumberVerification("+84$phone")
    }

    private fun instancePhoneSignIn() {
        phoneCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                Log.i("chauAPI", "codeSMS: ${p0.smsCode}")
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Log.i("chauAPI", p0.message.toString())
                dialog.dismiss()
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                Toast.makeText(
                    baseContext, "Code on send to sms.",
                    Toast.LENGTH_SHORT
                ).show()
                forceResend = p1
                verificationId = p0
                updateUI(true)
                dialog.dismiss()
            }
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(phoneCallBack)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.currentUser?.let {
            it.linkWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            baseContext, "Login phone number success.",
                            Toast.LENGTH_SHORT
                        ).show()
                        val result = task.result?.user
                        Log.i("chauAPI", "${result?.uid}  ${result?.phoneNumber}")
                        result?.let {
                            model.updatePhoneUser(
                                INSERT_PHONE,
                                it.uid,
                                it.displayName ?: "",
                                it.phoneNumber ?: ""
                            )
                        }

                    } else {
                        Log.i("chauAPI", task.exception?.message.toString())
                        task.exception?.let {
                            binding.textError.text = it.message.toString()
                        }
                    }
                    dialog.dismiss()
                }
        }
    }

    protected fun resendVerificationCode(phoneNumber: String) {
        dialog = Utils.showProgressBar(this, "Resend Code...")
        dialog.show()

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(phoneCallBack)          // OnVerificationStateChangedCallbacks
            .setForceResendingToken(forceResend)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    protected fun verifyPhoneNumberWithCode(codeSMS: String) {
        val cer = PhoneAuthProvider.getCredential(verificationId, codeSMS)
        signInWithPhoneAuthCredential(cer)
    }

    companion object{
        const val USER_ID = "USER_ID"
    }
}