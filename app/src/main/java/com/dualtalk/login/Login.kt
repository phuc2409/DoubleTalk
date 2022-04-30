package com.dualtalk.login

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.dualtalk.MainActivity.MainActivity
import com.dualtalk.R
import com.dualtalk.databinding.ActivityLoginBinding
import com.dualtalk.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_login.*

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

        databiding.SignUp.setOnClickListener{
            val intent = Intent(this@Login , SignupActivity::class.java)
            startActivity(intent)
        }

        viewModel.UiLoginState.observe(this){
            if(it == Login_ViewModel.LoginState.Success){
                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
                var intent : Intent = Intent(this@Login, MainActivity::class.java)
                startActivity(intent)
            }

            if(it == Login_ViewModel.LoginState.Fail){
                Toast.makeText(this, "Danh nhap that bai", Toast.LENGTH_SHORT).show()
            }
        }





    }

}