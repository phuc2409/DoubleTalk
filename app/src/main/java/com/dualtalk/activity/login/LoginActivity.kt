package com.dualtalk.activity.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color.parseColor
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.dualtalk.R
import com.google.android.gms.tasks.OnSuccessListener
import com.dualtalk.activity.forgotpassword.ForgotPasswordActivity
import com.dualtalk.activity.main.MainActivity
import com.dualtalk.common.Constant
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.type.Color
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    private val ref = FirebaseAuth.getInstance()
    private val db = Firebase.firestore
    lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        signUp.setOnClickListener {
            signUp.background = resources.getDrawable(R.drawable.switch_trcks, null)
            signIn.setTextColor(getResources().getColor(R.color.white))
            signUp.setTextColor(getResources().getColor(R.color.purple))
            signIn.background = resources.getDrawable(R.drawable.switch_trcks, null)
            signuplayout.visibility = View.VISIBLE
            loginlayout.visibility = View.GONE
        }
        signIn.setOnClickListener {
            signIn.background = resources.getDrawable(R.drawable.switch_trcks, null)
            signUp.background = resources.getDrawable(R.drawable.switch_trcks, null)
            signIn.setTextColor(getResources().getColor(R.color.purple))
            signUp.setTextColor(getResources().getColor(R.color.white))
            loginlayout.visibility = View.VISIBLE
            signuplayout.visibility = View.GONE
        }
        val email = findViewById<TextInputEditText>(R.id.signupemail)
        val password = findViewById<TextInputEditText>(R.id.signuppasword)
        val confirm = findViewById<TextInputEditText>(R.id.signupretypepasword)
        val btnsignup = findViewById<Button>(R.id.signup)
        val signinbtn = findViewById<Button>(R.id.signin)
        val txtforgotpass = findViewById<TextView>(R.id.txtviewForgotPassword)

        btnsignup.setOnClickListener {
            if (password.text.toString().trim() != confirm.text.toString().trim()) {
                Toast.makeText(
                    //pass = cofirmpass
                    applicationContext, "password and confirm password is not in valid",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                //tạo 1 user mới với id và mật khẩu
                ref.createUserWithEmailAndPassword(
                    email.text.toString().trim(),
                    password.text.toString().trim()
                ).addOnSuccessListener(OnSuccessListener {
//                    add user vào database sau khi đăng kí
                    val user = hashMapOf(
                        "email" to email.text.toString().trim(),
                        "imgUrl" to Constant.defaultImgUrl,
                        "fullName" to email.text.toString().trim(),
                        "id" to ref.currentUser?.uid
                    )

                    db.collection("users").document(ref.currentUser?.uid.toString())
                        .set(user)
                        .addOnSuccessListener {
                            Log.d(
                                "Create user",
                                "DocumentSnapshot successfully written!"
                            )
                        }
                        .addOnFailureListener { e ->
                            Log.w(
                                "Create user",
                                "Error writing document",
                                e
                            )
                        }
                })

                //chuyển intent
                signIn.background = resources.getDrawable(R.drawable.switch_trcks, null)
                signUp.background = null
                loginlayout.visibility = View.VISIBLE
                signuplayout.visibility = View.GONE

            }

        }
        sharedPreferences = getSharedPreferences("share", Context.MODE_PRIVATE)
        var isremember = sharedPreferences.getBoolean("Check", false)
        if (isremember) {
            var intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

        }
        signinbtn.setOnClickListener {
            val emailsignin = findViewById<TextInputEditText>(R.id.email)
            val passwordsignin = findViewById<TextInputEditText>(R.id.pasword)

            if (emailsignin.text.toString().trim() == "" || passwordsignin.text.toString()
                    .trim() == ""
            ) {
                Toast.makeText(this@LoginActivity, "Email or Password is null", Toast.LENGTH_SHORT)
                    .show()
            } else {
                FirebaseAuth.getInstance().signInWithEmailAndPassword(
                    emailsignin.text.toString().trim(),
                    passwordsignin.text.toString().trim()
                ).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val username: String = emailsignin.text.toString()
                        val pass: String = passwordsignin.text.toString()
                        val editor: SharedPreferences.Editor = sharedPreferences.edit()
                        editor.putString("User", username)
                        editor.putString("Pass", pass)
                        editor.commit()
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(intent)
                        GetUserProfile()
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Email or Password is invalid",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }

        //FogotPassword activity
        txtforgotpass.setOnClickListener {
            //Toast.makeText(this@LoginActivity , "Vao giao dien quen mat khau" , Toast.LENGTH_SHORT).show()
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }

    }


    fun GetUserProfile() {
        val user = Firebase.auth.currentUser
        user?.let {
            val name = user.displayName
            val email = user.email
            val Id = user.uid
        }
    }
}
