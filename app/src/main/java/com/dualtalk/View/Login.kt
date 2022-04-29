package com.dualtalk.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.dualtalk.R
import com.dualtalk.ViewModel.Login_ViewModel
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    lateinit var viewModel:Login_ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        dangnhap.setOnClickListener {
            var intent: Intent = Intent(this@Login,Main::class.java)
            startActivity(intent)
        }

    }

}