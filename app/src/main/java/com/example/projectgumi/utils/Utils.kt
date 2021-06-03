package com.example.projectgumi.utils

import android.app.AlertDialog
import android.content.Context
import android.transition.Transition
import android.view.LayoutInflater
import android.view.SurfaceControl
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.example.projectgumi.R
import com.example.projectgumi.databinding.ProgressBarBinding
import javax.xml.transform.Transformer

object Utils {
    const val API_URL = "http://192.168.1.29/gumi/"

    const val TAG = "TAG"

    const val INSERT_PHONE = "insert_phone"

    const val SUCCESS_PHONE = "success_phone"

    const val INSERT_CITY = "insert_city"

    const val SUCCESS_CITY = "success_city"

    const val SUCCESS = "success"

    const val API_ERROR = "error"

    const val TYPE_SHOP = 0

    const val TYPE_EXPLORE = 1

    const val TYPE_FAVORITE = 2

    const val SNS_REQUEST_CODE_GOOGLE = 887

    fun showProgressBar(context: Context, message: String): AlertDialog{
        val builder = AlertDialog.Builder(context)
        builder.setCancelable(false)
        val binding = ProgressBarBinding.inflate(LayoutInflater.from(context))
        binding.textMessage.text = message
        builder.setView(binding.root)
        return builder.create()
    }

    fun showFragmentById(activity: FragmentActivity?, fragment: Fragment){
        activity?.apply {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.fragment_container,
                    fragment
                )
                .addToBackStack(null)
                .commit()
        }
    }
    fun showReplaceFragment(activity: FragmentActivity?, fragment: Fragment){
        activity?.apply {
            supportFragmentManager.beginTransaction()
                .replace(
                    R.id.fragment_container,
                    fragment
                )
                .commit()
        }
    }
    fun showDialogFragment(activity: FragmentActivity?, fragment: DialogFragment, tag: String){
        activity?.apply {
            supportFragmentManager.beginTransaction()
                .add(
                    fragment,
                    tag
                )
                .commit()
        }
    }
}