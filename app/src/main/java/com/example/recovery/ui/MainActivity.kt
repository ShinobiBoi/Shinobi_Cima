package com.example.recovery.ui

import android.os.Bundle

import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController

import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.recovery.BuildConfig
import com.example.recovery.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        toolbar = findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.main)
        navigationView = findViewById(R.id.nav_view)
        bottomNavigationView = findViewById(R.id.nav_bar)

        setSupportActionBar(toolbar)


        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
        navController = navHostFragment.navController


    }

    override fun onStart() {
        super.onStart()


        appBarConfiguration = AppBarConfiguration(navController.graph, drawerLayout)
        setupActionBarWithNavController(navController, appBarConfiguration)

        //toolbar.setupWithNavController(navController,appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
        navigationView.setupWithNavController(navController)



        navController.addOnDestinationChangedListener { _, des, _ ->

            bottomNavigationView.isVisible =
                setOf(
                    R.id.homeFragment,
                    R.id.searchFragment,
                    R.id.favouriteFragment
                ).contains(des.id)

            // Or
//            bottomNavigationView.isVisible =
//                des.id == R.id.homeFragment  des.id == R.id.searchFragment  des.id == R.id.favouriteFragment
        }


    }

    // Handle the "Up" button press
    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}