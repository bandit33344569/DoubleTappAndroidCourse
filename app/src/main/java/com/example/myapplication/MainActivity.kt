package com.example.myapplication

import android.os.Bundle
import com.example.myapplication.habit.Habit
import android.view.MenuItem
import androidx.fragment.app.FragmentActivity

import androidx.fragment.app.Fragment

import com.google.android.material.navigation.NavigationView



class MainActivity : FragmentActivity(), FragmentList.ListCallback, FragmentEditHabit.EditHabitCallback,NavigationView.OnNavigationItemSelectedListener{

    private val LIST_TAG = "ListFragment"
    private val EDIT_HABIT_TAG = "EditHabitFragment"
    private val ABOUT_TAG = "About Fragment"
    //private val viewPagerFragment = ViewPagerFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        val navView = findViewById<NavigationView>(R.id.navigation_drawer)
        navView.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            replaceFragment(viewPagerFragment, LIST_TAG)
        }
    }

    override fun onSaveHabit(habit: Habit, habitPosition: Int) {
        val bundle = Bundle()
        bundle.putInt("habitPosition", habitPosition)
        bundle.putParcelable("habit", habit)
        viewPagerFragment.arguments = bundle
        onBackPressed()
    }

    override fun onAddHabit() {
        replaceFragment(FragmentEditHabit(), EDIT_HABIT_TAG)
    }

    override fun onEditHabit(habit: Habit, habitPosition: Int) {
        val fragment = FragmentEditHabit.newInstance(habit, habitPosition)
        replaceFragment(fragment, EDIT_HABIT_TAG)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {
        TODO("Not yet implemented")
    }

    private fun replaceFragment(fragment: Fragment, tag :String){
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }

}
