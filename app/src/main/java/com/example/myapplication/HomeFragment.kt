package com.example.myapplication

import android.os.Bundle
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
import com.example.myapplication.listFragments.goodhabits.ListFragment

class HomeFragment : Fragment() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2

    private val habits = mutableListOf<Habit>()
    private val goodListFragment = ListFragment.newInstance(Type.Good)
    private val badListFragment = ListFragment.newInstance(Type.Bad)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        viewPager2 = view.findViewById(R.id.view_pager)
        tabLayout = view.findViewById(R.id.tabLayout)

        val adapter = ViewPagerAdapter(this, goodListFragment, badListFragment)
        viewPager2.adapter = adapter

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            when (position) {
                0 -> tab.text = "Хорошие"
                1 -> tab.text = "Плохие"
            }
        }.attach()

        var habit: Habit? = null
        var habitPosition = -1
        arguments?.let {
            habit = it.getParcelable("habit")
            habitPosition = it.getInt("habitPosition")
        }
        if (habit != null) {
            if (habitPosition == -1) {
                habits.add(habit!!)
            } else {
                habits[habitPosition] = habit!!
            }

            val bundle = Bundle()
            bundle.putParcelableArrayList("habits", ArrayList<Habit>(habits))

            goodListFragment.arguments = bundle
            badListFragment.arguments = bundle
        }

        return view
    }
}


class ViewPagerAdapter(
    fragment: Fragment,
    private val goodListFragment: ListFragment,
    private val badListFragment: ListFragment
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







