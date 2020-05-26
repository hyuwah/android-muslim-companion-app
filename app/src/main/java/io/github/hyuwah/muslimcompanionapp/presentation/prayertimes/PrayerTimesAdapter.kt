package io.github.hyuwah.muslimcompanionapp.presentation.prayertimes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.hyuwah.muslimcompanionapp.databinding.ListItemPrayerTimeBinding

class PrayerTimesAdapter : RecyclerView.Adapter<PrayerTimesAdapter.ViewHolder>() {

    private val prayerTimes = mutableListOf<PrayerTimeModel>()

    fun setItem(prayerTimes: List<PrayerTimeModel>) {
        this.prayerTimes.clear()
        this.prayerTimes.addAll(prayerTimes)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListItemPrayerTimeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = prayerTimes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(prayerTimes[position])
    }

    inner class ViewHolder(
            private val binding: ListItemPrayerTimeBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: PrayerTimeModel) {
            binding.ivIcon.setImageResource(item.iconRes)
            binding.tvTitle.text = item.title
            binding.tvTime.text = item.time
        }
    }
}