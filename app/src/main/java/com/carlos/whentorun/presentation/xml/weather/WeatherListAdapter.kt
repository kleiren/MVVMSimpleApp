package com.carlos.whentorun.presentation.xml.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.carlos.whentorun.R
import com.carlos.whentorun.common.toFormattedString
import com.carlos.whentorun.databinding.ItemWeatherBinding
import com.carlos.whentorun.domain.model.WeatherAtLocation.HourlyWeather

class WeatherListAdapter : RecyclerView.Adapter<WeatherListAdapter.WeatherListViewHolder>() {

    class WeatherListViewHolder(val binding: ItemWeatherBinding) :
        RecyclerView.ViewHolder(binding.root)

    var weatherList: List<HourlyWeather>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    private val diffCallback = object : DiffUtil.ItemCallback<HourlyWeather>() {
        override fun areItemsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
            return oldItem.date == newItem.date
        }

        override fun areContentsTheSame(oldItem: HourlyWeather, newItem: HourlyWeather): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherListViewHolder {
        val binding = ItemWeatherBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return WeatherListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WeatherListViewHolder, position: Int) {
        val weather = weatherList[position]
        holder.binding.apply {
            tvWeatherItemTime.text =
                root.resources.getString(
                    R.string.date_template,
                    weather.date?.toFormattedString()
                )
            tvWeatherItemTemperature.text =
                root.resources.getString(
                    R.string.temperature_template,
                    weather.temperature.toString()
                )
        }
    }

    override fun getItemCount(): Int {
        return weatherList.size
    }

}