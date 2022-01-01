package com.example.firebaseuygulama.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.firebaseuygulama.R
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var getIntent: Intent
    private var userId: String? = null
    private lateinit var bottomNav: BottomNavigationView
    private lateinit var transaction: FragmentTransaction

    private lateinit var homeFragment: HomeFragment
    private lateinit var favoriFragment: FavoriFragment
    private lateinit var profileFragment: ProfileFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        bottomNav=findViewById(R.id.main_activity_bottomNav)
        getIntent = intent
        userId = getIntent.getStringExtra("userId")

        userId?.let {

            homeFragment = HomeFragment()
            favoriFragment = FavoriFragment()
            profileFragment = ProfileFragment(it)


            setFragöemt(homeFragment)

            bottomNav.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.bottom_menu_home -> {
                        setFragöemt(homeFragment)
                        true
                    }
                    R.id.bottom_menu_favori -> {
                        setFragöemt(favoriFragment)
                        true
                    }
                    R.id.bottom_menu_profile -> {
                        setFragöemt(profileFragment)
                        true
                    }
                    else -> false
                }
            }
        }
    }
    private fun setFragöemt(fragment: Fragment){
        transaction =supportFragmentManager.beginTransaction()
        transaction.replace(R.id.main_activity_frameLayout,fragment)
        transaction.commit()

    }
}