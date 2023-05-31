package com.example.app2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.d
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {

    private lateinit var toForgotPassword : TextView
    private lateinit var LoginEmail : EditText
    private lateinit var LoginPassword : EditText
    private lateinit var btnLogin : Button
    private lateinit var btnSignUp : Button
    private var auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
        listeners()
        intent()
    }

    private fun listeners() {
        btnLogin.setOnClickListener {
            val email = LoginEmail.text.toString()
            val password = LoginPassword.text.toString()
            if (email.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
                    if (task.isSuccessful) {
                        startActivity(Intent(this, LogeInActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT)
                    }

                }
            } else {
                Toast.makeText(this, "Please Fill All Fields", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun intent(){
        toForgotPassword.setOnClickListener {
            startActivity(Intent(this, ForgotPass::class.java))
            finish()
        }
        btnSignUp.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
            finish()
        }
    }


    private fun init(){
        toForgotPassword = findViewById(R.id.forgotPasswordBtn)
        btnLogin = findViewById(R.id.btnLogIn)
        btnSignUp = findViewById(R.id.BtnSignUp)
        LoginEmail = findViewById(R.id.LoginEmail)
        LoginPassword = findViewById(R.id.LoginPassword)
    }



}