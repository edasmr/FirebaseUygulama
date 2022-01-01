package com.example.firebaseuygulama.view.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.bumptech.glide.Glide
import com.example.firebaseuygulama.R
import com.example.firebaseuygulama.model.User
import com.example.firebaseuygulama.util.FirebaseUtil
import de.hdodenhof.circleimageview.CircleImageView

class UserInfoDialog(val mContext: Context, val mUser: User) : Dialog(mContext) {
    private lateinit var imgProfile: CircleImageView
    private lateinit var editNick: EditText
    private lateinit var editName: EditText
    private lateinit var editSurname: EditText
    private lateinit var btnOk: Button

    private lateinit var txtName: String
    private lateinit var txtSurname: String
    private lateinit var txtNick: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.user_info_dialog)
        imgProfile = findViewById(R.id.user_info_dialog_imgProfile)
        editNick = findViewById(R.id.user_info_dialog_editUserNick)
        editName = findViewById(R.id.user_info_dialog_editUserName)
        editSurname = findViewById(R.id.user_info_dialog_editUserSurname)
        btnOk = findViewById(R.id.user_info_dialog_btnOk)

        if (mUser.userProfileUrl.equals("default"))
            imgProfile.setBackgroundResource(R.drawable.ben)
        else
            Glide.with(mContext)
                .load(mUser.userProfileUrl)
                .into(imgProfile)

        editName.setText(mUser.userName)
        editSurname.setText(mUser.userSurname)
        editNick.setText(mUser.userNick)

        btnOk.setOnClickListener {
            txtName = editName.text.toString()
            txtSurname = editSurname.text.toString()
            txtNick = editNick.text.toString()

            FirebaseUtil.mFireStore.collection("Users").document(mUser.userId)
                .update(
                    mapOf(
                        "userNick" to txtNick,
                        "userName" to txtName,
                        "userSurname" to txtSurname
                    )
                )
                .addOnCompleteListener {
                    if (it.isSuccessful){
                        if (this.isShowing)
                            this.dismiss()
                    }
                }
        }
    }
}