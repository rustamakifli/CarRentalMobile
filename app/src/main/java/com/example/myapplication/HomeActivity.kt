package com.example.myapplication
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CarAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CarAdapter()
        recyclerView.adapter = adapter

        loadCars()
    }

    private fun loadCars() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cars = RetrofitClient.api.getCars()
                withContext(Dispatchers.Main) {
                    adapter.setCars(cars)
                }
            } catch (e: Exception) {
                Log.e("HomeActivity", "Failed to load cars: ${e.message}")
            }
        }
    }
}

