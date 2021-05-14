package com.example.projectgumi.ui.signInPhone

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.projectgumi.databinding.ActivityPhoneLoginBinding
import com.example.projectgumi.utils.Utils
import com.example.projectgumi.utils.Utils.SNS_RESULT_CODE
import com.example.projectgumi.utils.Utils.SNS_RESULT_DATA
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import java.util.concurrent.TimeUnit

class PhoneLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneLoginBinding
    private lateinit var phoneCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    private lateinit var forceResend: PhoneAuthProvider.ForceResendingToken
    private lateinit var mVerficationId: String
    private lateinit var auth: FirebaseAuth
    private lateinit var dialog: AlertDialog
    private val TAG = "MAIN_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPhoneLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init(){
        auth = FirebaseAuth.getInstance()

        instancePhoneSignIn()

        binding.apply {
            layoutHead.caption.text = "Mobie Number"
            layoutHead.title.text = "Enter your mobie number"
            flGetCode.setOnClickListener {

            }
            buttonGetCode.setOnClickListener {
                val phone = editPhoneNumber.text.toString()
                if(phone.isEmpty() || phone.length != 9){
                    Toast.makeText(this@PhoneLoginActivity, "Please enter phone number without 0", Toast.LENGTH_SHORT).show()
                }else{
                    startPhoneNumberVerification("+84$phone")
                }
            }
            buttonOK.setOnClickListener {
                val code = editCode.text.toString()
                verifyPhoneNumberWithCode(mVerficationId, code)
            }
            buttonResend.setOnClickListener {
                val phone = editPhoneNumber.text.toString()
                resendVerificationCode(phone, forceResend)
            }
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String){
        dialog = Utils.showProgressBar(this, "Verifying Phone Number...")
        dialog.show()

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(phoneCallBack)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    private fun resendVerificationCode(phoneNumber: String, token: PhoneAuthProvider.ForceResendingToken){
        dialog = Utils.showProgressBar(this, "Resend Code...")
        dialog.show()

        val options = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(phoneCallBack)          // OnVerificationStateChangedCallbacks
            .setForceResendingToken(token)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)

    }

    private fun verifyPhoneNumberWithCode(verificationId: String?, code: String){
        dialog = Utils.showProgressBar(this, "Verifying Code...")
        dialog.show()

        val credential = PhoneAuthProvider.getCredential(verificationId, code)
        signInWithPhoneAuthCredential(credential)
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAG", "signInWithCredential:${auth.currentUser!!.phoneNumber}")
                    val intent = Intent()
                    intent.putExtra(SNS_RESULT_DATA, auth.currentUser)
                    setResult(SNS_RESULT_CODE, intent)
                    dialog.dismiss()
                    finish()
                } else {
                    dialog.dismiss()
                    // If sign in fails, display a message to the user.
                    Log.w("TAG", "signInWithCredential:failure", task.exception)
                    Toast.makeText(
                        baseContext, "Authentication failed by code error.",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun instancePhoneSignIn() {
        phoneCallBack = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks(){
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
//                signInWithPhoneAuthCredential(p0)
                Toast.makeText(this@PhoneLoginActivity, p0.smsCode, Toast.LENGTH_SHORT).show()
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                dialog.dismiss()
                Toast.makeText(this@PhoneLoginActivity, p0.message, Toast.LENGTH_SHORT).show()
            }

            @SuppressLint("SetTextI18n")
            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                Log.d(TAG, "onCodeSent: $p0")
                mVerficationId = p0
                forceResend = p1
                dialog.dismiss()
                Toast.makeText(this@PhoneLoginActivity, "Verification code sent", Toast.LENGTH_SHORT).show()
                //hide phone show code
                binding.apply {
                    layoutCode.visibility = View.VISIBLE
                    layoutPhone.visibility = View.GONE
                    layoutHead.caption.text = "Mobie Code"
                    layoutHead.title.text = "Enter your mobie code"
                }
            }
        }
    }
}