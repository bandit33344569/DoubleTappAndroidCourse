package com.example.myapplication

import android.os.Bundle
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.myapplication.habit.Habit
import com.example.myapplication.ListFragment.ListCallback
import com.google.android.material.navigation.NavigationView


class MainActivity : FragmentActivity(),ListCallback, EditHabitCallback {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        navigationView = findViewById(R.id.navigation_view)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.menu_home -> {
                    if (navController.currentDestination?.id != R.id.homeFragment) {
                        navController.navigate(R.id.action_aboutFragment2_to_homeFragment)
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    false
                }

                R.id.menu_about -> {
                    if (navController.currentDestination?.id != R.id.aboutFragment) {
                        navController.navigate(R.id.action_homeFragment_to_aboutFragment)
                        drawerLayout.closeDrawer(GravityCompat.START)
                    }
                    false
                }

                else -> false
            }
        }
    }

    override fun onAddHabit() {
        navController.navigate(R.id.action_homeFragment_to_editHabitFragment_WithoutArgs)
    }

    override fun onEditHabit(habit: Habit, habitPosition: Int) {
        val action = HomeFragmentDirections.actionHomeFragmentToEditHabitFragment(habit, habitPosition)
        navController.navigate(action)
    }

    override fun onSaveHabit(habit: Habit, habitPosition: Int) {
        val action = EditHabitFragmentDirections.actionEditHabitFragmentToHomeFragment(habit, habitPosition)
        navController.navigate(action)
    }

}

