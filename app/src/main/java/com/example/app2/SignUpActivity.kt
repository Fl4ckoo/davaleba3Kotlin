package com.example.app2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpActivity : AppCompatActivity() {

    private lateinit var signupEmailEditText : EditText
    private lateinit var signupPasswordEditText : EditText
    private lateinit var signupButton : Button
    private lateinit var alreadyRegisteredButton : TextView
    private val auth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        init()
        listener()
    }

    private fun listener(){
        signupButton.setOnClickListener {
            val email = signupEmailEditText.text.toString()
            val password = signupPasswordEditText.text.toString()

            if(email.isEmpty() || password.isEmpty() || email.contains(" ") || password.length < 8){
                for (a in password){
                    if (a.isUpperCase()){
                        return@setOnClickListener
                    }
                }
                return@setOnClickListener
            }

            auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }else{
                    Toast.makeText(this, "${task.exception?.message}", Toast.LENGTH_SHORT)
                }
            }



        }
        alreadyRegisteredButton.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }

    private fun init(){
        signupButton = findViewById(R.id.signupBtn)
        signupPasswordEditText = findViewById(R.id.signupPasswordEditText)
        signupEmailEditText = findViewById(R.id.signupEmailEditText)
        alreadyRegisteredButton = findViewById(R.id.alreadyRegisteredButton)
    }
}