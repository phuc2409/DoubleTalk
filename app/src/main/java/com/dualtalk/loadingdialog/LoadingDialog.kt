package com.dualtalk.loadingdialog

import android.app.Activity
import android.app.AlertDialog
import com.dualtalk.R

class LoadingDialog(activity: Activity) {
     private lateinit var dialog : AlertDialog
     private lateinit var activity: Activity
     init {
         this.activity = activity
     }

    fun startLoadingDialog(){
        val builder = AlertDialog.Builder(this.activity)

        val layoutInflater = this.activity.layoutInflater

        builder .setView(layoutInflater.inflate(R.layout.custom_dialoglayout, null))
        builder.setCancelable(false)

        dialog = builder.create()
        dialog.show()
    }

    fun dismissDialog(){
        dialog.dismiss()
    }
}