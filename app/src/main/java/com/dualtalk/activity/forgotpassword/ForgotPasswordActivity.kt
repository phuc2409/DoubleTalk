package com.dualtalk.activity.forgotpassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dualtalk.R
import com.dualtalk.databinding.ActivityForgotPasswordBinding

class ForgotPasswordActivity : AppCompatActivity() {
    private lateinit var dataBinding: ActivityForgotPasswordBinding
    lateinit var viewModel: ForgotPasswordViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, R.layout.activity_forgot_password)
        dataBinding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(ForgotPasswordViewModel::class.java)

        dataBinding.btnSend.setOnClickListener {
            val email = dataBinding.edtEmail.text.toString().trim()
            viewModel.SendLinkToResetPassword(email)
        }

        viewModel.forgotpassState.observe(this) {
            if (it == ForgotPasswordViewModel.forgotPassState.Success) {
                Toast.makeText(
                    this,
                    "Please check your email to reset password!",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
            if (it == ForgotPasswordViewModel.forgotPassState.Fail) {
                Toast.makeText(this, "Fail", Toast.LENGTH_SHORT).show()
            }
        }
    }
}