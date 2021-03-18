package com.example.habittracker.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.Infrastructure.HSVColor
import com.example.habittracker.R
import kotlinx.android.synthetic.main.color_picker_item.view.*

class ColorAdapter(var colors: List<HSVColor>): RecyclerView.Adapter<ColorAdapter.ColorViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ColorViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.color_picker_item, parent, false)
        return ColorViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ColorViewHolder, position: Int) {
        holder.bind(colors[position])
    }

    override fun getItemCount(): Int = colors.size

    class ColorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView:ImageView = itemView.colorImageView

        fun bind(color: HSVColor){
            imageView.setColorFilter(color.toArgbColor())
        }
    }
}