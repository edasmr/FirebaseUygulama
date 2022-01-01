package com.example.firebaseuygulama.view

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.example.firebaseuygulama.R
import com.example.firebaseuygulama.model.User
import com.example.firebaseuygulama.util.FirebaseUtil
import com.example.firebaseuygulama.util.NotifyMessage
import com.example.firebaseuygulama.util.Singleton


class SignUpFragment : Fragment(), View.OnClickListener  {

    private lateinit var v: View

    private lateinit var editUserEmail: EditText
    private lateinit var editUserNick: EditText
    private lateinit var editUserName : EditText
    private lateinit var editSurname: EditText
    private lateinit var editUserPassword: EditText

    private lateinit var btnSignUp: Button
    private lateinit var imgBack: ImageView

    private lateinit var txtUserEmail: String
    private lateinit var txtUserNick:String
    private lateinit var txtName: String
    private lateinit var txtSurname: String
    private lateinit var txtPassword: String




    private fun init(){
        editUserEmail = v.findViewById(R.id.sign_up_fragmnet_editEmail)
        editUserName = v.findViewById(R.id.sign_up_fragmnet_editName)
        editSurname = v.findViewById(R.id.sign_up_fragmnet_editSurname)
        editUserNick = v.findViewById(R.id.sign_up_fragmnet_editUserNick)
        editUserPassword = v.findViewById(R.id.sign_up_fragmnet_editUserPassword)
        btnSignUp = v.findViewById(R.id.sign_up_fragment_btnSignUp)
        imgBack = v.findViewById(R.id.sig_up_fragment_imgBack)


        btnSignUp.setOnClickListener(this)
        imgBack.setOnClickListener(this)

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view
        init()
    }

    override fun onClick(v: View?) {
        v?.let {
            when(it.id){
                R.id.sign_up_fragment_btnSignUp -> signUpToUser()
                R.id.sig_up_fragment_imgBack -> backToPage()
            }
        }


    }
    private fun signUpToUser(){

        txtUserEmail = editUserEmail.text.toString()
        txtUserNick = editUserNick.text.toString()
        txtName = editUserName.text.toString()
        txtSurname = editSurname.text.toString()
        txtPassword = editUserPassword.text.toString()

        if(!txtUserEmail.isEmpty()){
            if(!txtUserNick.isEmpty()){
                if(!txtName.isEmpty()){
                    if(!txtSurname.isEmpty()){
                        if(!txtPassword.isEmpty()){
                            FirebaseUtil.signUpUser(txtUserEmail, txtPassword, object : NotifyMessage {
                                override fun onSuccess(msg: String) {
                                    Singleton.showSnack(v, msg)
                                }

                                override fun onFailure(msg: String?) {
                                    msg?.let {
                                        Singleton.showSnack(v, it)
                                    }
                                }
                            }, signUpUserOnComplete = {fUser, signState ->
                                if(signState){
                                    fUser?.let {

                                        FirebaseUtil.mUser = User(
                                            it.uid,
                                            txtUserNick,
                                            txtName,
                                            txtSurname,
                                            txtUserEmail
                                        )

                                        FirebaseUtil.saveUserData(FirebaseUtil.mUser, object : NotifyMessage {
                                            override fun onSuccess(msg: String) {
                                                Singleton.showSnack(v, msg)
                                            }

                                            override fun onFailure(msg: String?) {
                                                msg?.let {
                                                    Singleton.showSnack(v, msg)
                                                }
                                            }
                                        }, saveUserDataOnComplete = {saveState ->
                                            if (saveState){
                                                clearAllEditText()
                                                backToPage()
                                            }


                                    })
                                    }
                                }
                            })
                        }else
                            Singleton.showSnack(v, "Lütfen geçerli bir şifre giriniz")
                    }else
                        Singleton.showSnack(v,"Lütfen geçerli bir soyisim giriniz")
                }else
                    Singleton.showSnack(v,"Lütfen geçerli bir isim giriniz")
            }else
                Singleton.showSnack(v,"Lütfen geçerli bir kullanıcı adı giriniz")
        }else
            Singleton.showSnack(v, "Lütfen geçerli bir email giriniz")

    }

    private fun backToPage(){

        Singleton.setPage(0)

    }

    private fun clearAllEditText(){
        editUserEmail.setText("")
        editUserNick.setText("")
        editUserName.setText("")
        editSurname.setText("")
        editUserPassword.setText("")
    }}