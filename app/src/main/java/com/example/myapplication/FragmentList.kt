package com.example.myapplication

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.habit.Habit
import com.example.myapplication.habit.Type
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentList : Fragment() {
    var callback: ListCallback? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var filterType: Type
    private var habitsPositions = mutableMapOf<Habit, Int>()
    private var habits = mutableListOf<Habit>()
    private var filteredHabits = habits

    companion object {
        fun newInstance(type: Type): FragmentList {
            val fragment = FragmentList()
            val bundle = Bundle()
            bundle.putSerializable("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    interface ListCallback {
        fun onAddHabit()
        fun onEditHabit(habit: Habit, habitPosition: Int)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = activity as ListCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState != null) {
            filteredHabits =
                savedInstanceState.getParcelableArrayList<Parcelable>("filteredHabits") as MutableList<Habit>
            filterType = savedInstanceState.getSerializable("type") as Type
        } else {
            arguments?.let {
                filterType = it.getSerializable("type") as Type
            }
        }

        viewAdapter = DataAdapter(filteredHabits)
        (viewAdapter as DataAdapter).setOnItemClickListener { v -> onEditHabit(v) }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        viewManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.habits_list_recycler_view)
        recyclerView.layoutManager = viewManager
        recyclerView.adapter = viewAdapter

        val fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            callback?.onAddHabit()
        }

        var habits: List<Habit>? = null
        arguments?.let {
            habits = it.getParcelableArrayList<Habit>("habits")
        }

        if (habits != null) {
            filteredHabits.clear()
            for (i in habits!!.indices) {
                val h = habits!![i]
                if (h.type == filterType) {
                    filteredHabits.add(h)
                    habitsPositions[h] = i
                }
            }

            viewAdapter.notifyDataSetChanged()
        }

        return view
    }

    fun onEditHabit(v: View?) {
        if (v != null) {
            val position = viewManager.getPosition(v)
            val habit = (viewAdapter as DataAdapter).getHabit(position)
            val realPosition = habitsPositions[habit]
            if (realPosition != null) {
                callback?.onEditHabit(habit, realPosition)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelableArrayList("filteredHabits", ArrayList<Parcelable>(filteredHabits))
        outState.putSerializable("type", filterType)
    }
}