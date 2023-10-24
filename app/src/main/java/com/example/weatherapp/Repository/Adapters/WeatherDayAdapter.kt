package com.example.weatherapp.Repository.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherapp.R
import com.example.weatherapp.Repository.Retrofit.RecyclerViewModel
import com.example.weatherapp.databinding.ListItemBinding
import com.squareup.picasso.Picasso

class WeatherDayAdapter(val listener: ListenerAdapter):ListAdapter<RecyclerViewModel, WeatherDayAdapter.Holder>(
    Comparator()
) {
    class Holder(view:View):RecyclerView.ViewHolder(view){
        val binding= ListItemBinding.bind(view)
        fun bind(itemWeather: RecyclerViewModel, listener: ListenerAdapter, position: Int){
             if (itemWeather.flagHoursOrDay){
               binding.updateTV.text= itemWeather.date.substringAfter(" ")
            }else{
                binding.updateTV.text = itemWeather.date
            }

            if (itemWeather.temp!=""){
                binding.tempTV.text = itemWeather.temp + "°C"
            }else{
                binding.tempTV.text = "${itemWeather.mintemp}°C/${itemWeather.maxtemp}°C"
            }

            binding.conditionTV.text = itemWeather.condition
            Picasso.get().load("https:"+ itemWeather.icon).into(binding.iconView)
            binding.itemView.setOnClickListener {
                    listener.onClick(position)
            }

        }
    }
    class Comparator:DiffUtil.ItemCallback<RecyclerViewModel>(){
        override fun areItemsTheSame(oldItem: RecyclerViewModel, newItem: RecyclerViewModel): Boolean {
            return oldItem==newItem
        }

        override fun areContentsTheSame(oldItem: RecyclerViewModel, newItem: RecyclerViewModel): Boolean {
            return oldItem==newItem
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
       val view= LayoutInflater.from(parent.context).inflate(R.layout.list_item,parent,false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(getItem(position),listener,position)
    }

}



