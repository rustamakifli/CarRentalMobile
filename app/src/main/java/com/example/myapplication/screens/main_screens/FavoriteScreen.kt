package com.example.myapplication.screens.main_screens
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.adapters.FavoriteAdapter
import com.example.myapplication.screens.auth.VerificationScreen
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class FavoriteScreen : AppCompatActivity() {
    private lateinit var favoriteList: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var imgButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_screen)

        favoriteList = findViewById(R.id.favoriteList)
        favoriteList.layoutManager = LinearLayoutManager(this)
        supportActionBar?.title = "Favorites"

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzE2NDQ1MDA5LCJpYXQiOjE3MTU4NDAyMDksImp0aSI6ImUzM2Y0NTZmOWFiMjRjNDc4ODYxOTMwYTljYjY0MDRlIiwiZW1haWwiOiJtcmt6Z3NtdkBnbWFpbC5jb20ifQ.6mq267o-_tzIQiE92sd8RAEa3rMe5lzGZLkn6kZNPr0"

        token?.let { fetchUserProfile(it) }
    }

    private fun fetchUserProfile(token: String) {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://139.162.147.132/api/v1/wishlist/")
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
                    val carsArray = jsonObject.getJSONArray("cars")

                    runOnUiThread {
                        favoriteAdapter = FavoriteAdapter(carsArray)
                        favoriteList.adapter = favoriteAdapter
                    }
                }
            }
        })
    }

    fun navigateHomeActivity(view: View) {
        startActivity(Intent(this, HomeActivity::class.java))
    }
}
