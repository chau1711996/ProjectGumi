package com.example.projectgumi.model

import com.example.projectgumi.utils.SNSLoginType
import com.google.firebase.auth.FirebaseUser
import java.io.Serializable

data class SNSLoginModelModel(val loginType: SNSLoginType, val user: FirebaseUser?) : Serializable