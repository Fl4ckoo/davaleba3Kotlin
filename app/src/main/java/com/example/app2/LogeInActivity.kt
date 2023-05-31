package com.example.app2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class LogeInActivity : AppCompatActivity() {

    private lateinit var imageView : ImageView
    private lateinit var firstNameTextView : TextView
    private lateinit var emailTextview : TextView
    private lateinit var idTextView : TextView
    private lateinit var linkEditText : EditText
    private lateinit var firstNameEditText : EditText
    private lateinit var updateButton : Button
    private lateinit var signOutButton : Button
    private lateinit var uploadBtn : Button
    private lateinit var idEditText: EditText

    private val auth = FirebaseAuth.getInstance()
    private val db = Firebase.database.getReference("Student")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loge_in)
        init()
        listeners()
    }


    private fun listeners() {
        updateButton.setOnClickListener {
            val link = linkEditText.text.toString()
            val name = firstNameEditText.text.toString()
            val id = idEditText.text.toString()

            val userInfo = Student(
                auth.currentUser?.email,
                auth.uid,
                link,
                id,
                name,



            )
            db.child(auth.uid!!).setValue(userInfo)

        }
        signOutButton.setOnClickListener {
            auth.signOut()
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }

        uploadBtn.setOnClickListener {
            val name = firstNameEditText.text.toString()
            val url = linkEditText.text.toString()
            val id = idEditText.text.toString()
            val uid = auth.uid
            val email = auth.currentUser?.email
            val userInfo = Student(name, url, uid, id,email)
            db.child(FirebaseAuth.getInstance().uid!!).setValue(userInfo)

        }
    }

    private fun init(){
        imageView = findViewById(R.id.imageView)
        firstNameTextView = findViewById(R.id.firstNameTextView)
        linkEditText = findViewById(R.id.linkEditText)
        firstNameEditText = findViewById(R.id.firstNameEditText)
        updateButton = findViewById(R.id.updateButton)
        signOutButton = findViewById(R.id.signOutButton)
        uploadBtn = findViewById(R.id.uploadBtn)
        idEditText = findViewById(R.id.idEditText)
        idTextView = findViewById(R.id.idTextView)
        emailTextview = findViewById(R.id.emailTextView)

        db.child(auth.uid!!).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val userInfo = snapshot.getValue(Student::class.java) ?: return
                firstNameTextView.text = "Your Name: " + userInfo.firstName
                emailTextview.text = "Your Email: " + userInfo.email
                idTextView.text = "Your ID: " + userInfo.id
                val link = userInfo.link
                Glide.with(this@LogeInActivity).load(link).into(imageView)
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@LogeInActivity, error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

}