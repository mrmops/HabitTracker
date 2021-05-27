package com.example.habittracker.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.Infrastructure.HSVColor
import com.example.domain.Models.Habit
import com.example.androidhelper.Infostructure.toAndroidColor
import com.example.habittracker.Infostructure.toString
import com.example.habittracker.R
import kotlinx.android.synthetic.main.habit_item.view.*
import java.text.SimpleDateFormat

class HabitAdapter(private var habits: ArrayList<Habit>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object{
        private const val ELEMENT_VIEW_TYPE = 0
        private const val FOOTER_VIEW_TYPE = 1
    }

    var onItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if(viewType == ELEMENT_VIEW_TYPE)
            return HabitViewHolder(inflater.inflate(R.layout.habit_item, parent, false))
        return FooterViewHolder(inflater.inflate(R.layout.empty100dp, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(getItemViewType(position) == ELEMENT_VIEW_TYPE) {
            val habit = habits[position]
            val habitViewHolder = holder as HabitViewHolder
            habitViewHolder.itemView.setOnClickListener { onItemClickListener?.onItemClick(habit) }
            habitViewHolder.bind(habit)
            habitViewHolder.doneButton.setOnClickListener { onItemClickListener?.onDoneButtonClick(habit) }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == habits.size) FOOTER_VIEW_TYPE else ELEMENT_VIEW_TYPE
    }

    override fun getItemCount(): Int {
        return habits.size + 1
    }

    fun updateItems(habits: List<Habit>){
        this.habits = ArrayList(habits)
        notifyDataSetChanged()
    }

    class HabitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTextView: TextView = itemView.habitName
        private val descriptionTextView = itemView.description
        private val dateOfUpdateTextView = itemView.habitDateOfUpdate
        private val priorityTextView = itemView.habitPriority
        private val typeTextView = itemView.habitType
        private val detailsButton = itemView.detailsButton
        private val habitDetailsGroup = itemView.habitDetails
        private val habitColor = itemView.habitColor
        private val repeatTextView = itemView.numberRepetitionsTextView
        val doneButton = itemView.doneHabitButton

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
            val dateFormat = SimpleDateFormat("HH:mm dd-MM-yyyy")
            dateOfUpdateTextView.text = dateFormat.format(habit.dateOfUpdate)
            val context = priorityTextView.context
            priorityTextView.text = habit.priority.toString(context)
            typeTextView.text = habit.type.toString(context)
            habitColor.setColorFilter(habit.color?.toAndroidColor() ?: HSVColor().toAndroidColor())
            repeatTextView.text = habit.numberRepeating.toString()
        }
    }

    class FooterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    interface OnItemClickListener {
        fun onItemClick(habit: Habit)

        fun onDoneButtonClick(doneTarget: Habit)
    }
}