package com.example.myapplication.screens.main_screens

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.myapplication.R
import com.squareup.picasso.Picasso
import org.json.JSONException
import org.json.JSONObject

class SingleCarActivity : AppCompatActivity() {

    private lateinit var carImageView: ImageView
    private lateinit var carNameTextView: TextView
    private lateinit var carPriceTextView: TextView
    private lateinit var carCompanyTextView: TextView
    private lateinit var requestToBookButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Enable the back button
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        carImageView = findViewById(R.id.carSingleImageView)
        carNameTextView = findViewById(R.id.carNameTextView)
        carPriceTextView = findViewById(R.id.carPriceTextView)
        requestToBookButton = findViewById<Button>(R.id.bookNowButton)



        val carId = intent.getIntExtra("carId", -1)
        println("caridd: $carId")
        fetchDataFromApi(carId.toString())

        requestToBookButton.setOnClickListener {
            val intent = Intent(this, RequestBookScreen::class.java)
            startActivity(intent)
        }
    }

    private fun fetchDataFromApi(carId: String?) {
        val apiUrl = "http://139.162.147.132/api/v1/cars/$carId"
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, apiUrl, null,
            { response ->
                println("API Response: $response")
                try {

                    val carName = response.getString("name")
                    val carPrice = response.getString("price")
                    val carImages = response.getJSONArray("car_images")
                    println(carPrice)
                    println(carImages)
                    var imageUrl: String? = null
                    for (i in 0 until carImages.length()) {
                        val imageObject = carImages.getJSONObject(i)
                        if (imageObject.getBoolean("is_main")) {
                            imageUrl = imageObject.getString("image")
                            break
                        }
                    }
                    carNameTextView.text = carName
                    carPriceTextView.text = "${carPrice} $/day"
                    imageUrl?.let {
                        Picasso.get().load(it).into(carImageView)
                    }                } catch (e: JSONException) {
                    e.printStackTrace()                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            },
            { error ->
                println("API Error: $error")
                Toast.makeText(this, "Error fetching car details", Toast.LENGTH_SHORT).show()
            }
        )
        println(jsonObjectRequest)
        val queue: RequestQueue = Volley.newRequestQueue(this)
        queue.add(jsonObjectRequest)
    }
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
    // needs to update wishlist add
//    private fun addCarToFavorites(carId: String) {
//        val accessToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ0b2tlbl90eXBlIjoiYWNjZXNzIiwiZXhwIjoxNzE2NDQ1MDA5LCJpYXQiOjE3MTU4NDAyMDksImp0aSI6ImUzM2Y0NTZmOWFiMjRjNDc4ODYxOTMwYTljYjY0MDRlIiwiZW1haWwiOiJtcmt6Z3NtdkBnbWFpbC5jb20ifQ.6mq267o-_tzIQiE92sd8RAEa3rMe5lzGZLkn6kZNPr0"
//        val apiUrl = "http://139.162.147.132/api/v1/wishlist/"
//
//        val stringRequest = object : StringRequest(Method.POST, apiUrl,
//            { response ->
//                Toast.makeText(this, "Car added to favorites", Toast.LENGTH_SHORT).show()
//            },
//            { error ->
//                println("Error: $error")
//                Toast.makeText(this, "Error adding car to favorites", Toast.LENGTH_SHORT).show()
//            }) {
//            override fun getHeaders(): MutableMap<String, String> {
//                val headers = HashMap<String, String>()
//                headers["Authorization"] = "Bearer $accessToken"
//                return headers
//            }
//
//            override fun getParams(): MutableMap<String, String> {
//                val params = HashMap<String, String>()
//                params["car_id"] = carId
//                return params
//            }
//        }
//
//        val queue: RequestQueue = Volley.newRequestQueue(this)
//        queue.add(stringRequest)
//    }


}
