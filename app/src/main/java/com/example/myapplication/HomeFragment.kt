package com.example.myapplication

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.myapplication.habit.Habit
import com.example.myapplication.habit.Type
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class HomeFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    private var habits = mutableListOf<Habit>()
    private lateinit var goodListFragment: ListFragment
    private lateinit var badListFragment: ListFragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        if (savedInstanceState == null) {
            goodListFragment = ListFragment.newInstance(Type.Good)
            badListFragment = ListFragment.newInstance(Type.Bad)
        }
        viewPager2 = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tabLayout)

        val adapter = ViewPagerAdapter(this)
        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Хорошие"
                1 -> tab.text = "Плохие"
            }
        }.attach()

        val args = EditHabitFragmentArgs.fromBundle(requireArguments())
        val habit = args.habit
        val habitPosition = args.habitPosition
        if (savedInstanceState!= null){
            habits = savedInstanceState.getParcelableArrayList<Parcelable>("habits") as MutableList<Habit>
        }
        println(habits.toString())
        if (habit != null) {
            if (habitPosition == -1) {
                habits.add(habit)
            } else {
                habits[habitPosition] = habit
            }
            val goodBundle = Bundle()
            goodBundle.putParcelableArrayList("habits", ArrayList<Habit>(habits))
            goodBundle.putSerializable("type", Type.Good)
            goodListFragment.arguments = goodBundle
            val badBundle = Bundle()
            badBundle.putParcelableArrayList("habits", ArrayList<Habit>(habits))
            badBundle.putSerializable("type", Type.Bad)
            badListFragment.arguments = badBundle
        }

        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList("habits", ArrayList<Parcelable>(habits))
    }

    inner class ViewPagerAdapter(
        fragment: Fragment,
    ) : FragmentStateAdapter(fragment) {
        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(
            position: Int
        ): Fragment {
            return when (position) {
                0 -> goodListFragment
                1 -> badListFragment
                else -> throw IllegalArgumentException("Invalid position")
            }
        }
    }
}








