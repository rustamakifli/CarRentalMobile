package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BrandAdapter(private val brands: List<Brand>) : RecyclerView.Adapter<BrandAdapter.BrandViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrandViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_brand, parent, false)
        return BrandViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BrandViewHolder, position: Int) {
        val brand = brands[position]
        holder.bind(brand)
    }

    override fun getItemCount(): Int {
        return brands.size
    }

    inner class BrandViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val brandIconImageView: ImageView = itemView.findViewById(R.id.brandIconImageView)
        private val brandNameTextView: TextView = itemView.findViewById(R.id.brandNameTextView)

        fun bind(brand: Brand) {
            // Bind brand icon and name here
            brandIconImageView.setImageResource(brand.imageResId)
            brandNameTextView.text = brand.name
        }
    }
}

