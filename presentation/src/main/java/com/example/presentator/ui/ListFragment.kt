package com.example.presentator.ui

import android.content.Context
import android.os.Bundle
import android.os.Parcelable
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.entity.Habit
import com.example.domain.entity.Type
import com.example.presentator.R
import com.example.presentator.di.HabitTrackerApplication
import com.example.presentator.enums.PrioritySort
import com.example.presentator.vm.ListViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import javax.inject.Inject

class ListFragment: Fragment() {
    var callback: ListCallback? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var filterType: Type
    private var filteredHabits = mutableListOf<Habit>()

    @Inject
    lateinit var viewModel: ListViewModel


    companion object {
        fun newInstance(type: Type): ListFragment {
            val fragment = ListFragment()
            val bundle = Bundle()
            bundle.putSerializable("type", type)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        callback = activity as ListCallback
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        checkSavedInstanceState(savedInstanceState)
    }

    private fun initViewModel(){
        (activity?.applicationContext as HabitTrackerApplication).appComponent.inject(this)
        viewModel.loadHabit()
        viewModel.setType(filterType)
    }

    private fun checkSavedInstanceState(savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            filteredHabits = savedInstanceState.getParcelableArrayList<Parcelable>("filteredHabits") as MutableList<Habit>
            filterType = savedInstanceState.getSerializable("type") as Type
        }
        else {
            arguments?.let {
                filterType = it.getSerializable("type") as Type
            }
        }
    }

    private fun initViewAdapter() {
        viewAdapter = HabitAdapter(filteredHabits,viewModel)
        (viewAdapter as HabitAdapter).setOnItemClickListener { v -> onEditHabit(v) }
    }



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_habits, container, false)
        initViewModel()
        initViewAdapter()
        initRecyclerView(view)
        initFab(view)
        initBottomSheet(view)
        observeHabits()
        viewModel.loadHabit()
        return view
    }

    private fun initRecyclerView(view: View) {
        viewManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.recycler_view)
        recyclerView.layoutManager = viewManager
        recyclerView.adapter = viewAdapter
    }

    private fun initFab(view: View) {
        val fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        fab.setOnClickListener {
            callback?.onAddHabit()
        }
    }

    private fun observeHabits() {
        viewModel.habits.observe(viewLifecycleOwner) { habits ->
            filteredHabits.clear()
            filteredHabits.addAll(habits)
            viewAdapter.notifyDataSetChanged()
        }
    }

    private fun initBottomSheet(view: View) {
        val bottomSheet = view.findViewById<LinearLayout>(R.id.bottom_sheet)
        val bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        val fab = view.findViewById<FloatingActionButton>(R.id.floatingActionButton)
        bottomSheetBehavior.addBottomSheetCallback(object: BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {

                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        fab.show()
                    }

                    BottomSheetBehavior.STATE_DRAGGING -> {
                        fab.hide()
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {

            }
        })

        initNameField(bottomSheet)
        initPrioritySpinner(bottomSheet)
    }

    private fun initNameField(bottomSheet: View) {
        val nameField = bottomSheet.findViewById<EditText>(R.id.find_by_name_field)
        nameField.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.setSearchFilter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

        })
    }


    private fun initPrioritySpinner(bottomSheet: View) {
        val prioritySpinner = bottomSheet.findViewById<Spinner>(R.id.sort_by_priority_field)
        prioritySpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedSort = PrioritySort.entries[prioritySpinner.selectedItemPosition]
                println(prioritySpinner.selectedItemPosition)
                println(selectedSort)
                viewModel.setPrioritySort(selectedSort)
            }
        }
    }

    private fun onEditHabit(v: View?) {
        if (v != null) {
            val position = viewManager.getPosition(v)
            val habit = (viewAdapter as HabitAdapter).getHabit(position)
            callback?.onEditHabit(habit.id!!)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putParcelableArrayList("filteredHabits", ArrayList<Parcelable>(filteredHabits))
        outState.putSerializable("type", filterType)
    }
}

interface ListCallback {
    fun onAddHabit()
    fun onEditHabit(habitId: String)
}