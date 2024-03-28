package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.example.myapplication.habit.Habit
import com.example.myapplication.habit.Priority
import com.example.myapplication.habit.Type

class EditHabitFragment : Fragment() {
    private var habitPosition = -1
    var callback: EditHabitCallback? = null

    private val habitPriorities = mapOf(
        Priority.Low to 0,
        Priority.Medium to 1,
        Priority.High to 2
    )

    private val priorityIndexToEnum = mapOf(
        "Низкий" to Priority.Low,
        "Средний" to Priority.Medium,
        "Высокий" to Priority.High
    )

    private val habitTypeToId = mapOf(
        Type.Good to R.id.habit_type_good,
        Type.Bad to R.id.habit_type_bad
    )

    companion object {
        fun newInstance(habit: Habit, habitPosition: Int): EditHabitFragment {
            val fragment = EditHabitFragment()
            val bundle = Bundle()
            bundle.putInt("habitPosition", habitPosition)
            bundle.putParcelable("habit", habit)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as EditHabitCallback
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.edit_habit_fragment, container, false)
        var habit: Habit? = null
        var habitPosition = -1
        arguments?.let {
            val args = HomeFragmentArgs.fromBundle(it)
            println(args)
            habit = args.habit
            habitPosition = args.habitPosition
        }
        this.habitPosition = habitPosition

        if (habit != null) {
            val habitName = view.findViewById<EditText>(R.id.habit_name_edit)
            habitName.setText(habit!!.name)

            val habitDescription = view.findViewById<EditText>(R.id.habit_description_edit)
            habitDescription.setText(habit!!.description)

            val habitPriority = view.findViewById<Spinner>(R.id.habit_priority_edit)
            val selection = habitPriorities[habit!!.priority]
            if (selection != null) {
                habitPriority.setSelection(selection)
            }

            val habitTypeId = habitTypeToId[habit!!.type] ?: R.id.habit_type_good
            val habitType = view.findViewById<RadioButton>(habitTypeId)
            habitType.isChecked = true

            val habitTimes = view.findViewById<EditText>(R.id.habit_times_edit)
            habitTimes.setText(habit!!.times.toString())

            val habitPeriod = view.findViewById<EditText>(R.id.habit_period_edit)
            habitPeriod.setText(habit!!.period.toString())
        }

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        fun onSave() {
            val name = view.findViewById<EditText>(R.id.habit_name_edit)
            val description = view.findViewById<EditText>(R.id.habit_description_edit)
            val priority = view.findViewById<Spinner>(R.id.habit_priority_edit)
            val type = view.findViewById<RadioGroup>(R.id.habit_type_edit)
            val times = view.findViewById<EditText>(R.id.habit_times_edit)
            val period = view.findViewById<EditText>(R.id.habit_period_edit)
            val priorityValue =
                priorityIndexToEnum[priority.selectedItem.toString()] ?: Priority.High
            val typeValue = if (type.checkedRadioButtonId == R.id.habit_type_good) {
                Type.Good
            } else {
                Type.Bad
            }

            val timesValue = if (times.text.toString() == "") {
                1
            } else {
                times.text.toString().toInt()
            }

            val periodValue = if (period.text.toString() == "") {
                1
            } else {
                period.text.toString().toInt()
            }

            val habit = Habit(
                name.text.toString(),
                description.text.toString(),
                priorityValue,
                typeValue,
                timesValue,
                periodValue,
            )
            callback?.onSaveHabit(habit, habitPosition)
        }

        val saveBtn = view.findViewById<Button>(R.id.save_habit)
        saveBtn.setOnClickListener {
            onSave()
        }
    }
}

interface EditHabitCallback {
    fun onSaveHabit(habit: Habit, habitPosition: Int)
}