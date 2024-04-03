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
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CarAdapter
    private val  brands = listOf(
        Brand("All", R.drawable.all),
        Brand("Tesla", R.drawable.ic_brand_placeholder),
        Brand("BMW", R.drawable.bmw),
        Brand("Ferrari", R.drawable.ferrari) ,
        Brand("Audo", R.drawable.audi)

    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        adapter = CarAdapter()
        recyclerView.adapter = adapter
        val brandRecyclerView: RecyclerView = findViewById(R.id.brandRecyclerView)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = BrandAdapter(brands)
        brandRecyclerView.layoutManager = layoutManager
        brandRecyclerView.adapter = adapter
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

