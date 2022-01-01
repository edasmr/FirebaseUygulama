package com.example.firebaseuygulama.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.example.firebaseuygulama.R
import com.example.firebaseuygulama.util.FirebaseUtil
import com.example.firebaseuygulama.util.NotifyMessage
import com.example.firebaseuygulama.util.Singleton


class SignInFragment : Fragment(), View.OnClickListener {
    private lateinit var v: View

    private lateinit var mainIntent: Intent

    private lateinit var editUserEmail: EditText
    private lateinit var editUserPassword: EditText
    private lateinit var btnSignIn: Button
    private lateinit var btnSignUp: Button

    private lateinit var txtUserEmail: String
    private lateinit var txtUserPassword: String

    private fun init(){
        editUserEmail = v.findViewById(R.id.sign_in_fragmnet_editEmail)
        editUserPassword = v.findViewById(R.id.sign_in_fragmnet_editUserPassword)
        btnSignIn = v.findViewById(R.id.sign_in_fragment_btnSignIn)
        btnSignUp = v.findViewById(R.id.sign_in_fragment_btnSignUp)

        btnSignUp.setOnClickListener(this)
        btnSignIn.setOnClickListener(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view
        init()

        FirebaseUtil.signInControl(object :  NotifyMessage{
            override fun onSuccess(msg: String) {

            }

            override fun onFailure(msg: String?) {
                msg?.let {
                    Singleton.showSnack(v, it)
                }
            }
        }, signInControlOnComplete = {fUser, signState ->
            if (signState){
                fUser?.let{
                    goToMainPAge(it.uid)
                }
            }

        })
    }

    override fun onClick(p0: View?) {
        p0?.let{
            when(it.id){
                R.id.sign_in_fragment_btnSignIn -> signToUser()
                R.id.sign_in_fragment_btnSignUp -> goToSignUpPage()
            }
        }
    }

    private fun signToUser(){
        txtUserEmail = editUserEmail.text.toString()
        txtUserPassword = editUserPassword.text.toString()


        if (!txtUserEmail.isEmpty()){
            if (!txtUserPassword.isEmpty()){
                FirebaseUtil.signInUser(txtUserEmail,txtUserPassword, object : NotifyMessage{

                    override fun onSuccess(msg: String) {
                        Singleton.showSnack(v, msg)

                    }

                    override fun onFailure(msg: String?) {
                        msg?.let {
                            Singleton.showSnack(v, it)
                        }
                    }
                }, signInUserOnComplete = {fUser, signState ->
                    if (signState)
                        fUser?.let {
                            goToMainPAge(it.uid)
                        }
                })
            }else
                Singleton.showSnack(v, "Lütfen şifrenizi girin:")
        }else
            Singleton.showSnack(v, "Lütfen email girin:")

    }

    private fun goToSignUpPage(){
        Singleton.setPage(1)

    }

    private fun goToMainPAge(userId: String){
        mainIntent = Intent(v.context, MainActivity::class.java)
        mainIntent.putExtra("userId", userId)
        startActivity(mainIntent)
        (v.context as Activity).finish()
    }



}