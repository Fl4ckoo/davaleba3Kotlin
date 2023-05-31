package com.example.app2

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ForgotPass : AppCompatActivity() {

    private lateinit var btnSend : Button
    private lateinit var btnBack : Button
    private lateinit var forgetPassEmail : EditText
    private var auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pass)
        init()
        listeners()
        intent()
    }

    private fun listeners() {
        btnSend.setOnClickListener {
            val email = forgetPassEmail.text.toString()
            auth.setLanguageCode("en")
            if (email.isEmpty()) {
                Toast.makeText(this, "Please put your email", Toast.LENGTH_SHORT).show()
            } else {


                auth.sendPasswordResetEmail(email).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Log.d(TAG, "Email Sent.")
                        Toast.makeText(this, "Email Sent.", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT)
                    }
                }
            }
        }
    }

    private fun intent(){
        btnBack.setOnClickListener { task ->
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


    private fun init(){
        btnSend = findViewById(R.id.btnSend)
        btnBack = findViewById(R.id.btnBack)
        forgetPassEmail = findViewById(R.id.forgetPassEmail)
    }
}