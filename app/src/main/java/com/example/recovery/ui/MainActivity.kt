package com.example.recovery.ui

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.recovery.R
import com.example.recovery.data.remote.ApiClient
import com.example.recovery.data.remote.RemoteDataSrc
import com.example.recovery.ui.home.repo.HomeRepo
import com.example.recovery.ui.home.viewmodel.HomeViewFactory
import com.example.recovery.ui.home.viewmodel.HomeViewModel
import com.example.recovery.utilites.MyAdapter
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView:NavigationView
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        toolbar=findViewById(R.id.toolbar)
        drawerLayout = findViewById(R.id.main)
        navigationView=findViewById(R.id.nav_view)
        bottomNavigationView=findViewById(R.id.nav_bar)

        setSupportActionBar(toolbar)


    }

    override fun onStart() {
        super.onStart()

        navController=findNavController(R.id.fragmentContainerView)

        appBarConfiguration=AppBarConfiguration(navController.graph,drawerLayout)
        toolbar.setupWithNavController(navController,appBarConfiguration)
        bottomNavigationView.setupWithNavController(navController)
        navigationView.setupWithNavController(navController)



        navController.addOnDestinationChangedListener(){_,des,_ ->
            if (des.id== R.id.popularMoviesFragment || des.id == R.id.topRatedFragment || des.id==R.id.upComingFragment || des.id==R.id.detailsFragment)
                bottomNavigationView.visibility=View.GONE
            else
                bottomNavigationView.visibility=View.VISIBLE

        }


    }
}