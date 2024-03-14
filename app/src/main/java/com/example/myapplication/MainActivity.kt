package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import com.example.myapplication.habit.Habit
import androidx.recyclerview.widget.RecyclerView
import android.os.Parcelable
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager


class MainActivity : ComponentActivity() {

    private var habits = mutableListOf<Habit>()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private var addHabitLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val habit = data?.getParcelableExtra<Habit>("habit")
            if (habit != null) {
                habits.add(habit)
            }
        }
    }

    private var editHabitLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val habit = data?.getParcelableExtra<Habit>("habit")
            val habitPosition = data?.getIntExtra("habitPosition", -1)
            if (habit != null) {
                habits[habitPosition!!] = habit
                viewAdapter.notifyItemChanged(habitPosition)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState != null) {
            habits = savedInstanceState.getParcelableArrayList<Parcelable>("habits") as MutableList<Habit>
        }
        viewManager = LinearLayoutManager(this)
        viewAdapter = DataAdapter(habits)

        recyclerView = findViewById(R.id.habits_list_recycler_view)
        recyclerView.layoutManager = viewManager
        recyclerView.adapter = viewAdapter
    }


    fun onAddHabit(view: View) {
        val intent = Intent(this, ActivityAddItem::class.java)
        addHabitLauncher.launch(intent)
    }

    fun onEditHabit(view: View) {
        val intent = Intent(this, ActivityAddItem::class.java)
        val position = viewManager.getPosition(view)
        val habit = (viewAdapter as DataAdapter).getHabit(position)
        intent.putExtra("habit", habit)
        intent.putExtra("habitPosition", position)
        editHabitLauncher.launch(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("habits", ArrayList<Parcelable>(habits))
    }
}
