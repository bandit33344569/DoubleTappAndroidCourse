package com.example.presentator.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Habit
import com.example.presentator.R

class HabitAdapter(private var habitList: List<Habit>) :
    RecyclerView.Adapter<HabitAdapter.ViewHolder>() {
    private lateinit var clickListener: View.OnClickListener

    fun setOnItemClickListener(clickListener: View.OnClickListener) {
        this.clickListener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_habit, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = habitList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(habitList[position])
    }

    fun getHabit(position: Int): Habit {
        return habitList[position]
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.habit_name)
        private val description = itemView.findViewById<TextView>(R.id.habit_description)
        private val priority = itemView.findViewById<TextView>(R.id.habit_priority)
        private val type = itemView.findViewById<TextView>(R.id.habit_type)
        private val repeat = itemView.findViewById<TextView>(R.id.habit_repeat)

        fun bind(habit: Habit) {
            itemView.setOnClickListener(clickListener)
            val repeatValue = "${habit.times} раз в ${habit.period} дней"
            name.text = habit.name
            description.text = habit.description
            priority.text = habit.priority.value
            type.text = habit.type.value
            repeat.text = repeatValue
        }
    }
}