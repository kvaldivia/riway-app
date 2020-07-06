package me.kennyvaldivia.riway

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.navigation.ActivityNavigator
import androidx.navigation.NavBackStackEntry
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_splash_screen.*
import me.kennyvaldivia.riway.auth.AuthenticationState

class SplashScreenActivity : AppCompatActivity() {
    private var authenticationState = MutableLiveData<AuthenticationState>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val navigator = ActivityNavigator(this)
        navigator.popBackStack()
        val destination = navigator.createDestination()
        destination.intent = Intent(this, MainActivity::class.java)
        navigator.navigate(destination, null, null, null)
    }
}