package com.dualtalk.activity.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dualtalk.R
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
//    private lateinit var databiding : ActivityLoginBinding
//    private lateinit var viewModel: Login_ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signUp.setOnClickListener {
            signUp.background = resources.getDrawable(R.drawable.switch_trcks, null)
            signIn.background = null
            signuplayout.visibility = View.VISIBLE
            loginlayout.visibility = View.GONE
        }
        signIn.setOnClickListener {
            signIn.background = resources.getDrawable(R.drawable.switch_trcks, null)
            signUp.background = null
            loginlayout.visibility = View.VISIBLE
            signuplayout.visibility = View.GONE
        }
//        databiding = DataBindingUtil.setContentView(this ,R.layout.activity_login)
//        viewModel = ViewModelProvider(this).get(Login_ViewModel::class.java)
//
//
//        databiding.signin.setOnClickListener{
//            viewModel.login(databiding.email.text.toString() , databiding.pasword.text.toString())
//        }
//
//        viewModel.UiLoginState.observe(this){
//            if(it == Login_ViewModel.LoginState.Success){
//                Toast.makeText(this, "Login Success", Toast.LENGTH_SHORT).show()
//                var intent : Intent = Intent(this@Login, MainActivity::class.java)
//                startActivity(intent)
//            }
//
//            if(it == Login_ViewModel.LoginState.Fail){
//                Toast.makeText(this, "Danh nhap that bai", Toast.LENGTH_SHORT).show()
//            }
//        }
    }
}