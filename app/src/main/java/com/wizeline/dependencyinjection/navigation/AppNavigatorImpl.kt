package com.wizeline.dependencyinjection.navigation

import androidx.fragment.app.FragmentActivity
import com.wizeline.dependencyinjection.R
import com.wizeline.dependencyinjection.ui.CheckoutFragment
import com.wizeline.dependencyinjection.ui.OrderFragment

class AppNavigatorImpl(private val activity: FragmentActivity): AppNavigator {

    override fun navigateTo(screen: Screens) {
        val fragment = when(screen) {
            Screens.CHECKOUT -> CheckoutFragment.newInstance()
            Screens.ORDER -> OrderFragment.newInstance()

        }

        activity.supportFragmentManager.beginTransaction()
            .replace(R.id.main_container,fragment)
            .addToBackStack(fragment::class.java.canonicalName)
            .commit()
    }
}