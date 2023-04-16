package com.wizeline.dependencyinjection.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wizeline.dependencyinjection.R
import com.wizeline.dependencyinjection.navigation.AppNavigator
import com.wizeline.dependencyinjection.navigation.Screens

class MainActivity : AppCompatActivity() {

   lateinit var appNavigator: AppNavigator
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            appNavigator.navigateTo(Screens.ORDER)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }
}