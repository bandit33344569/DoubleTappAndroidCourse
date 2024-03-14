package com.example.myapplication


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.habit.Habit

class DataAdapter(private var items: MutableList<Habit>) : RecyclerView.Adapter<DataAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.list_item, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    fun getHabit(position: Int): Habit {
        return items[position]
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name = itemView.findViewById<TextView>(R.id.habit_name)
        private val description = itemView.findViewById<TextView>(R.id.habit_description)
        private val priority = itemView.findViewById<TextView>(R.id.habit_priority)
        private val type = itemView.findViewById<TextView>(R.id.habit_type)
        private val repeat = itemView.findViewById<TextView>(R.id.habit_repeat)


        fun bind(habit: Habit) {
            val timesStr =
                if (habit.Times in 11..19 || habit.Times % 10 in 0..1 || habit.Times % 10 in 5..9) {
                    "раз"
                } else {
                    "раза"
                }
            val periodStr = if (habit.Period in 11..19) {
                "дней"
            } else if (habit.Period % 10 == 1) {
                "день"
            } else if (habit.Period % 10 in 2..4) {
                "дня"
            } else {
                "дней"
            }
            val repeatValue = "${habit.Times} $timesStr, раз в ${habit.Period} $periodStr"

            name.text = habit.Name
            description.text = habit.Description
            priority.text = habit.Priority.value
            type.text = habit.Type.value
            repeat.text = repeatValue
        }
    }
}