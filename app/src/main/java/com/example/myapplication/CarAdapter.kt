package com.example.myapplication
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CarAdapter : RecyclerView.Adapter<CarAdapter.CarViewHolder>() {
    private var cars: List<Car> = listOf()

    inner class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val carImageView: ImageView = itemView.findViewById(R.id.carImageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameTextView)
        private val detailsTextView: TextView = itemView.findViewById(R.id.detailsTextView)

        fun bind(car: Car) {
            println("Imageeeeeeee $car")
            if (car.car_images?.isNotEmpty() == true) {
                Glide.with(itemView)
                    .load(car.car_images?.firstOrNull()?.image)
                    .placeholder(R.drawable.ic_car_placeholder)
                    .error(R.drawable.ic_car_error)
                    .into(carImageView)
            } else {
                carImageView.setImageResource(R.drawable.ic_car_placeholder)
            }

            nameTextView.text = car.name
            detailsTextView.text = "${car.brand}, ${car.model}, ${car.year}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_car_card, parent, false)
        return CarViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        holder.bind(car)
    }

    override fun getItemCount(): Int {
        return cars.size
    }

    fun setCars(cars: List<Car>) {
        this.cars = cars
        notifyDataSetChanged()
    }
}