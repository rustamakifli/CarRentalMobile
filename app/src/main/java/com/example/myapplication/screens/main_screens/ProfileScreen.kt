package com.example.myapplication.screens.main_screens

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.myapplication.R
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class ProfileScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzE2NDQ1MDA5LCJpYXQiOjE3MTU4NDAyMDksImp0aSI6ImUzM2Y0NTZmOWFiMjRjNDc4ODYxOTMwYTljYjY0MDRlIiwiZW1haWwiOiJtcmt6Z3NtdkBnbWFpbC5jb20ifQ.6mq267o-_tzIQiE92sd8RAEa3rMe5lzGZLkn6kZNPr0"

        token?.let { fetchUserProfile(it) }
    }

    private fun fetchUserProfile(token: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://139.162.147.132/api/v1/profile/")
            .addHeader("Authorization", "Bearer $token")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                if (!response.isSuccessful) {
                    return
                }

                response.body()?.let { responseBody ->
                    val responseData = responseBody.string()
                    val jsonObject = JSONObject(responseData)
                    println(jsonObject)
                    val name = jsonObject.getString("first_name")
                    val pp = jsonObject.getString("profile_picture")
                    val citizenship = jsonObject.getString("citizenship")

                    val gender = jsonObject.getString("gender")
                    val dateOfBirth = jsonObject.getString("birth_date")
                    val mobile = jsonObject.getString("phone")
                    val email = jsonObject.getString("email")

                    runOnUiThread {
                        findViewById<TextView>(R.id.name).text = name
                        findViewById<TextView>(R.id.citizenship).text = citizenship
                        findViewById<TextView>(R.id.genderTextView).text = gender
                        findViewById<TextView>(R.id.dateOfBirthTextView).text = dateOfBirth
                        findViewById<TextView>(R.id.mobileTextView).text = mobile
                        findViewById<TextView>(R.id.emailTextView).text = email
                        Glide.with(this@ProfileScreen)
                            .load(pp)
                            .into(findViewById(R.id.profileImageView))
                    }
                }
            }
        })
    }
}
