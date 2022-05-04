package com.dualtalk.activity.forgotpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dualtalk.R
import com.dualtalk.databinding.ActivityForgotPassword2Binding
import com.google.android.material.textfield.TextInputEditText

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var databiding : ActivityForgotPassword2Binding
    lateinit var viewmodel : ForgotPasswordViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databiding = DataBindingUtil.setContentView(this ,R.layout.activity_forgot_password2)
        databiding.lifecycleOwner = this
        viewmodel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)

        val btnSend = findViewById<Button>(R.id.btnSendLinkToResetPassword)
        val edtEmail = findViewById<TextInputEditText>(R.id.ForgotPassword_email)
        btnSend.setOnClickListener{
            var emailadress = edtEmail.text.toString().trim()
            viewmodel.SendLinkToResetPassword(emailadress)
        }

        viewmodel.forgotpassState.observe(this){
            if(it == ForgotPasswordViewModel.forgotPassState.Success){
                Toast.makeText(this, "Please check your email to reset password!", Toast.LENGTH_SHORT).show()
                finish()
            }
            if(it == ForgotPasswordViewModel.forgotPassState.Fail){
                Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
            }
        }




    }
}