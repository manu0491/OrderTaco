package com.wizeline.dependencyinjection.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.wizeline.dependencyinjection.R
import com.wizeline.dependencyinjection.navigation.AppNavigator
import com.wizeline.dependencyinjection.navigation.Screens
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class MainActivity : AppCompatActivity() {

   private var appNavigator: AppNavigator? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null && appNavigator != null) {
            appNavigator?.navigateTo(Screens.ORDER)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }
}