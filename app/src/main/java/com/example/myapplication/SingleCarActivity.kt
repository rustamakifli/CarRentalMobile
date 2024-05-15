package com.example.myapplication

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso
import org.json.JSONException

class SingleCarActivity : AppCompatActivity() {

    private lateinit var carImageView: ImageView
    private lateinit var carNameTextView: TextView
    private lateinit var carPriceTextView: TextView
    private lateinit var carCompanyTextView: TextView

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

        val carId = intent.getIntExtra("carId", -1)
        println("caridd: $carId")
        fetchDataFromApi(carId.toString())
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

}
