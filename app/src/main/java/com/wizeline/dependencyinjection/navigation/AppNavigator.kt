package com.wizeline.dependencyinjection.navigation

enum class Screens {
    ORDER,
    CHECKOUT
}

interface AppNavigator {
    fun navigateTo(screen: Screens)
}