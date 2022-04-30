package com.dualtalk.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dualtalk.R
import com.dualtalk.databinding.ActivityLoginBinding

class Login : AppCompatActivity() {
    private lateinit var databiding : ActivityLoginBinding
    private lateinit var viewModel: Login_ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        databiding = DataBindingUtil.setContentView(this ,R.layout.activity_login)
        viewModel = ViewModelProvider(this).get(Login_ViewModel::class.java)


        databiding.btndangnhap.setOnClickListener{
            viewModel.login(databiding.editTextEmail.text.toString() , databiding.editTextPassword.text.toString())
        }

        viewModel.UiLoginState.observe(this){
            if(it == Login_ViewModel.LoginState.Success){
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
            }

            if(it == Login_ViewModel.LoginState.Fail){
                Toast.makeText(this, "Danh nhap that bai", Toast.LENGTH_SHORT).show()
            }
        }





    }

}