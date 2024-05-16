package com.example.myapplication.screens.auth

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication.R

@SuppressLint("CustomSplashScreen")
class SplashScreen : AppCompatActivity() {

    private val timeout: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler().postDelayed({
            val intent = Intent(this, LoginScreen::class.java)
            startActivity(intent)

            finish()
        }, timeout)
    }
}