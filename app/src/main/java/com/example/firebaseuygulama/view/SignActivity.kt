package com.example.firebaseuygulama.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager.widget.ViewPager
import com.example.firebaseuygulama.R
import com.example.firebaseuygulama.util.adapter.CustomViewPager
import com.example.firebaseuygulama.util.Singleton

class SignActivity : AppCompatActivity() {

    private lateinit var mViewPager: ViewPager
    private lateinit var  pagerAdapter: CustomViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign)

        mViewPager = findViewById(R.id.sign_activit_viewPager)
        pagerAdapter = CustomViewPager(supportFragmentManager)
        pagerAdapter.addFragment(SignInFragment())
        pagerAdapter.addFragment(SignUpFragment())

        mViewPager.adapter = pagerAdapter

        Singleton.mViewPager = mViewPager

    }
}