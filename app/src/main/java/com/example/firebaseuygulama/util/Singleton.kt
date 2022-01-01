package com.example.firebaseuygulama.util

import android.content.Context
import android.view.View
import android.widget.Toast
import androidx.viewpager.widget.ViewPager
import com.example.firebaseuygulama.model.User
import com.example.firebaseuygulama.view.dialog.UserInfoDialog
import com.google.android.material.snackbar.Snackbar

class Singleton {
    companion object{
        var mViewPager: ViewPager? = null
        private var userInfoDialog: UserInfoDialog? = null


        fun showSnack(v: View, msg: String){

            Snackbar.make(v, msg, Snackbar.LENGTH_LONG).show()

        }

        fun showToast(context: Context, msg: String){

            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()



        }

        fun setPage(sIn: Int){
            mViewPager?.let {
                it.currentItem = sIn

            }
        }

        fun showUserInfoDialog(mContext: Context, user: User){
            userInfoDialog = UserInfoDialog(mContext, user)
            userInfoDialog!!.show()
        }
    }
}