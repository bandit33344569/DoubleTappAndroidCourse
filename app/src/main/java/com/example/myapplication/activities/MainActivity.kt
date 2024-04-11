package com.example.myapplication.activities

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import com.example.myapplication.R
import com.example.myapplication.fragments.EditHabitCallback
import com.example.myapplication.fragments.EditHabitFragmentDirections
import com.example.myapplication.fragments.HomeFragmentDirections
import com.example.myapplication.fragments.ListCallback
import com.google.android.material.navigation.NavigationView
import java.util.UUID


class MainActivity : FragmentActivity(), ListCallback, EditHabitCallback {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var navController: NavController

    private lateinit var appBarConfiguration: AppBarConfiguration



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
                    true
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

        appBarConfiguration = AppBarConfiguration(navController.graph)
        val toolbar: Toolbar = findViewById(R.id.toolbar)


        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }


    override fun onAddHabit() {
        val action = HomeFragmentDirections.actionHomeFragmentToEditHabitFragmentWithoutArgs()
        navController.navigate(action)
    }

    override fun onEditHabit(habitId: UUID) {
        val action = HomeFragmentDirections.actionHomeFragmentToEditHabitFragment(habitId)
        navController.navigate(action)
    }

    override fun onSaveHabit() {
        val action = EditHabitFragmentDirections.actionEditHabitFragmentToHomeFragment()
        navController.navigate(action)
    }

}

