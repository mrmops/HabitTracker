package com.example.habittracker.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.Habit
import com.example.habittracker.R

class HabitAdapter(val habits: List<Habit>): RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitViewHolder(inflater.inflate(R.layout.habit_item, parent, false))
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        holder.bind(habits[position])
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.habit_name)
        val descriptionTextView = itemView.findViewById<TextView>(R.id.description)
        val periodTextView = itemView.findViewById<TextView>(R.id.habit_period)
        val priorityTextView = itemView.findViewById<TextView>(R.id.habit_priority)
        val typeTextView = itemView.findViewById<TextView>(R.id.habit_type)
        private val detailsButton = itemView.findViewById<ImageView>(R.id.details_button)
        private val habitDetails = itemView.findViewById<Group>(R.id.habit_details)

        init {
            detailsButton.setOnClickListener {
                if(habitDetails.visibility == View.VISIBLE) {
                    habitDetails.visibility = View.GONE
                    detailsButton.setImageResource(R.drawable.round_keyboard_arrow_down_black_18)
                }
                else {
                    habitDetails.visibility = View.VISIBLE
                    detailsButton.setImageResource(R.drawable.round_keyboard_arrow_up_black_18)
                }
            }
        }

        fun bind(habit:Habit){
            nameTextView.text = habit.name
            descriptionTextView.text = habit.description
            periodTextView.text = habit.periodic
            priorityTextView.text = priorityTextView.context.resources.getString(habit.priority.toResourceId())
            typeTextView.text = habit.type.toString()
        }
    }
}