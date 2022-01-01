package com.example.firebaseuygulama.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.firebaseuygulama.R
import com.example.firebaseuygulama.model.User
import com.example.firebaseuygulama.util.FirebaseUtil
import com.example.firebaseuygulama.util.NotifyMessage
import com.example.firebaseuygulama.util.Singleton
import de.hdodenhof.circleimageview.CircleImageView


class ProfileFragment(val userId: String) : Fragment()  {

    private lateinit var v: View

    private lateinit var imgProfile: CircleImageView
    private lateinit var txtUserNick: TextView
    private lateinit var txtUserName: TextView
    private lateinit var txtUserSurName: TextView
    private lateinit var txtUserEmail: TextView
    private lateinit var buton : Button

    private lateinit var mUser: User
    private lateinit var userData: User
    private fun init(){
        imgProfile = v.findViewById(R.id.profile_fragment_imgProfile)
        txtUserNick = v.findViewById(R.id.profile_fragment_txtUserNick)
        txtUserEmail = v.findViewById(R.id.profile_fragment_txtUserEmail)
        txtUserName = v.findViewById(R.id.profile_fragment_txtUserName)
        txtUserSurName = v.findViewById(R.id.profile_fragment_txtUserSurname)
        buton = v.findViewById(R.id.profile_fragment_btnEditProfile)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        v = view
        init()




        /* Tek seferlik kullanıcı datasını alma
        FirebaseUtil.getUserDataOneTime(userId, object : NotifyMessage{
            override fun onSuccess(msg: String) {
                println(msg)
            }

            override fun onFailure(msg: String?) {
                msg?.let {
                    println(it)
                }
            }
        }, getUserDataOneTimeOnComplete = {userData ->
            userData?.let {
                mUser = it
                setData(it)
            }
        })
        */

        //Anlık kullanıcı datasını alma
        FirebaseUtil.getUserDataListener(userId, object : NotifyMessage {
            override fun onSuccess(msg: String) {
                println(msg)
            }

            override fun onFailure(msg: String?) {
                msg?.let {
                    println(it)
                }
            }
        }, getUserDataListenerOnComplete = {userData ->
            userData?.let {
                mUser = it
                setData(it)

                buton.setOnClickListener {
                    goToMainPAge()
                }
            }
        })
    }

    private fun setData(mUser: User){
        txtUserName.text = mUser.userName
        txtUserSurName.text = mUser.userSurname
        txtUserNick.text = mUser.userNick
        txtUserEmail.text = mUser.userMail

        if (mUser.userProfileUrl.equals("default"))
            imgProfile.setBackgroundResource(R.drawable.ben)
        else
            Glide.with(v.context)
                .load(mUser.userProfileUrl)
                .into(imgProfile)



    }


    private fun goToMainPAge(){
       Singleton.showUserInfoDialog(v.context, mUser)
    }



}