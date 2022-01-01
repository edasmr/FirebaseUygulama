package com.example.firebaseuygulama.view

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Patterns
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.firebaseuygulama.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase

class EditProfileActivity : AppCompatActivity() {

    lateinit var FirstName: EditText
    lateinit var LastName:EditText
    lateinit var Email: EditText
    lateinit var NickName:EditText
    lateinit var Password:EditText
    lateinit var Buton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_profile_activity)
        viewInitializations()
        Buton.setOnClickListener {
            val  mainIntent = Intent(applicationContext,ProfileFragment::class.java)
         //  mainIntent.putExtra("userId", userId)
            startActivity(mainIntent)

        }

        }


    fun viewInitializations() {

        FirstName = findViewById(R.id.et_first_name)
        LastName = findViewById(R.id.et_last_name)
        Email  = findViewById(R.id.et_email)
        NickName = findViewById(R.id.et_nickName)
        Buton = findViewById(R.id.bt_register)





        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }


    fun validateInput(): Boolean {
        if (FirstName.text.toString().equals("")) {
            FirstName.setError("Please Enter First Name")
            return false
        }
        if (LastName.text.toString().equals("")) {
            LastName.setError("Please Enter Last Name")
            return false
        }
        if (Email.text.toString().equals("")) {
            Email.setError("Please Enter Email")
            return false
        }

        if (NickName.text.toString().equals("")) {
            NickName.setError("Please Enter Nickname")
            return false
        }

        if (!isEmailValid(Email.text.toString())) {
            Email.setError("Please Enter Valid Email")
            return false
        }

        return true
    }

    fun isEmailValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }



    fun performEditProfile (view: View) {
        if (validateInput()) {



            val firstName = FirstName.text.toString()
            val lastName = LastName.text.toString()
            val email = Email.text.toString()
            val Nickname = NickName.text.toString()




            Toast.makeText(this,"Profile Update Successfully",Toast.LENGTH_SHORT).show()

        }
    }

}