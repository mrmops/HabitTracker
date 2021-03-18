package com.example.habittracker.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.habittracker.Models.Habit
import com.example.habittracker.R
import kotlinx.android.synthetic.main.habit_item.view.*

class HabitAdapter(private val habits: ArrayList<Habit>): RecyclerView.Adapter<HabitAdapter.HabitViewHolder>() {

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(habit: Habit)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return HabitViewHolder(inflater.inflate(R.layout.habit_item, parent, false))
    }

    override fun onBindViewHolder(holder: HabitViewHolder, position: Int) {
        val habit = habits[position]
        holder.itemView.setOnClickListener{ onItemClickListener?.onItemClick(habit)}
        holder.bind(habit)
    }

    override fun getItemCount(): Int {
        return habits.size
    }

    fun addItem(habit: Habit){
        habits.add(habit)
        notifyItemInserted(habits.size - 1)
    }

    fun updateItem(habit: Habit){
        val index = habits.indexOf(habit)
        habits[index] = habit
        notifyItemChanged(index)
    }

    class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.habitName
        private val descriptionTextView = itemView.description
        private val periodTextView = itemView.habitPeriod
        private val priorityTextView = itemView.habitPriority
        private val typeTextView = itemView.habitType
        private val detailsButton = itemView.detailsButton
        private val habitDetailsGroup = itemView.habitDetails
        private val habitColor = itemView.habitColor

        init {
            detailsButton.setOnClickListener {
                if(habitDetailsGroup.visibility == View.VISIBLE) {
                    habitDetailsGroup.visibility = View.GONE
                    detailsButton.setImageResource(R.drawable.round_keyboard_arrow_down_black_18)
                }
                else {
                    habitDetailsGroup.visibility = View.VISIBLE
                    detailsButton.setImageResource(R.drawable.round_keyboard_arrow_up_black_18)
                }
            }
        }

        fun bind(habit: Habit){
            nameTextView.text = habit.name
            descriptionTextView.text = habit.description
            periodTextView.text = habit.periodic
            val context = priorityTextView.context
            priorityTextView.text = habit.priority.toString(context)
            typeTextView.text = habit.type.toString(context)
            habitColor.setColorFilter(habit.color.toArgbColor())
        }
    }
}