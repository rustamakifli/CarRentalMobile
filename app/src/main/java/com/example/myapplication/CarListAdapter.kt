package com.example.myapplication
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myapplication.CarListAdapter.*

class CarListAdapter : RecyclerView.Adapter<CarViewHolder>() {
    private var cars: List<Car> = listOf()

    inner class CarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val carImageView: ImageView = itemView.findViewById(R.id.carListImageView)
        private val nameTextView: TextView = itemView.findViewById(R.id.nameListTextView)
        private val detailsTextView: TextView = itemView.findViewById(R.id.detailsListTextView)
        private val nameSeatView: TextView = itemView.findViewById(R.id.nameListSeatView)
        private val namePriceText: TextView = itemView.findViewById(R.id.nameListPriceText)


        fun bind(car: Car) {
            println("LIST Imageeeeeeee $car")
            println("test")
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
            nameSeatView.text = "${car.transmission} seats"
            namePriceText.text = "${car.price} $/day"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_cars, parent, false)
        return CarViewHolder(itemView)
        println("something")
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = cars[position]
        println("onbind")
        holder.bind(car)
    }

    override fun getItemCount(): Int {
        return cars.size

    }

    fun setCars(cars: List<Car>) {
        this.cars = cars
        println("carsss:${cars}")
        notifyDataSetChanged()
    }
}