package com.example.myapplication.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.R
import org.json.JSONArray

class FavoriteAdapter(private val favoriteList: JSONArray) :
    RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    class FavoriteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val carImageView: ImageView = view.findViewById(R.id.carImageView)
        val nameTextView: TextView = view.findViewById(R.id.nameTextView)
        val detailsTextView: TextView = view.findViewById(R.id.detailsTextView)
        val priceTextView: TextView = view.findViewById(R.id.priceTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_favorite, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        val car = favoriteList.getJSONObject(position)
        val carImages = car.getJSONArray("car_images")
        val mainImage = carImages.getJSONObject(0).getString("image")
        val name = car.getString("name")
        val model = car.getString("model")
        val year = car.getString("year")
        val price = car.getString("price")

        Glide.with(holder.itemView.context)
            .load("http://139.162.147.132" + mainImage) // Adjust the base URL if necessary
            .into(holder.carImageView)

        holder.nameTextView.text = "$name $model"
        holder.detailsTextView.text = "Year: $year"
        holder.priceTextView.text = "Price: $price AZN"
    }

    override fun getItemCount(): Int {
        return favoriteList.length()
    }
}
