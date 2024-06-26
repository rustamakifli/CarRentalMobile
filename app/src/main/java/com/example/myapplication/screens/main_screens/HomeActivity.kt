package com.example.myapplication.screens.main_screens
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.api_services.RetrofitClient
import com.example.myapplication.adapters.BrandAdapter
import com.example.myapplication.adapters.CarAdapter
import com.example.myapplication.adapters.CarListAdapter
import com.example.myapplication.data.Brand
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
class HomeActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var listrecyclerView: RecyclerView

    private lateinit var adapter: CarAdapter
    private lateinit var listAdapter: CarListAdapter
    private val brands = listOf(
        Brand("All", R.drawable.all),
        Brand("Tesla", R.drawable.ic_brand_placeholder),
        Brand("BMW", R.drawable.bmw),
        Brand("Ferrari", R.drawable.ferrari),
        Brand("Audi", R.drawable.audi)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        runBlocking {
            installSplashScreen()
            delay(1500)
        }
        setContentView(R.layout.activity_home)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.background = null
        bottomNavigationView.menu.getItem(2).isEnabled = false
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> {
                    startActivity(Intent(this, FavoriteScreen::class.java))
                    true
                }
                R.id.profile -> {
                    startActivity(Intent(this, ProfileScreen::class.java))
                    true
                }
                else -> false
            }
        }

        recyclerView = findViewById(R.id.recyclerView)
        listrecyclerView = findViewById(R.id.carListRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        listrecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        adapter = CarAdapter()
        listAdapter = CarListAdapter()

        recyclerView.adapter = adapter
        listrecyclerView.adapter = listAdapter

        val brandRecyclerView: RecyclerView = findViewById(R.id.brandRecyclerView)
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val adapter = BrandAdapter(brands)
        brandRecyclerView.layoutManager = layoutManager
        brandRecyclerView.adapter = adapter

        loadCars()
        loadListCars()
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
    private fun loadListCars() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val cars = RetrofitClient.api.getCars()
                withContext(Dispatchers.Main) {
                    listAdapter.setCars(cars)
                }
            } catch (e: Exception) {
                Log.e("HomeActivity", "Failed to load cars: ${e.message}")
            }
        }
    }
}


