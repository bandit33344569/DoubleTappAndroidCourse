package com.example.myapplication

import android.os.Bundle
import android.view.View
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.example.myapplication.habit.Habit
import com.example.myapplication.listFragments.goodhabits.ListFragment.ListCallback
import com.google.android.material.navigation.NavigationView


class MainActivity : FragmentActivity(), ListCallback, EditHabitCallback {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController

    private val EDIT_HABIT_TAG = "EditHabitFragment"
    private val viewPagerFragment = HomeFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        navigationView = findViewById(R.id.navigation_view)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(navigationView, navController)
        drawerLayout = findViewById(R.id.drawer_layout)

        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.menu_home -> {
                    if (navController.currentDestination?.id != R.id.homeFragment) {
                        navController.navigate(R.id.action_aboutFragment2_to_homeFragment)
                        drawerLayout.closeDrawer(GravityCompat.START)
                        true
                    }
                    false
                }

                R.id.menu_about -> {
                    if (navController.currentDestination?.id != R.id.aboutFragment) {
                        navController.navigate(R.id.action_homeFragment_to_aboutFragment)
                        drawerLayout.closeDrawer(GravityCompat.START)
                        true
                    }
                    false
                }

                else -> false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment, tag: String) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.nav_host_fragment, fragment, tag)
            .addToBackStack(tag)
            .commit()
    }


    override fun onSaveHabit(habit: Habit, habitPosition: Int) {
        val bundle = Bundle()
        bundle.putInt("habitPosition", habitPosition)
        bundle.putParcelable("habit", habit)
        viewPagerFragment.arguments = bundle
    }

    override fun onAddHabit() {
        replaceFragment(EditHabitFragment(), EDIT_HABIT_TAG)
    }

    override fun onEditHabit(habit: Habit, habitPosition: Int) {
        val fragment = EditHabitFragment.newInstance(habit, habitPosition)
        replaceFragment(fragment, EDIT_HABIT_TAG)
    }

}
