package com.goriant.app

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.goriant.app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // FIXED: Use binding.root instead of R.layout.activity_main

        setSupportActionBar(binding.appBarMain.toolbar)

        binding.appBarMain?.fab?.setOnClickListener { view -> // FIXED: Null safety check
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }

        // Safe check for NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as? NavHostFragment
        val navController = navHostFragment?.navController

        if (navController != null) {
            binding.navView?.let {
                appBarConfiguration = AppBarConfiguration(
                    setOf(
                        R.id.nav_transform, R.id.nav_reflow, R.id.nav_slideshow, R.id.nav_settings
                    ),
                    binding.drawerLayout
                )
                setupActionBarWithNavController(navController, appBarConfiguration)
                it.setupWithNavController(navController)
            }

            binding.appBarMain?.contentMain?.bottomNavView?.let {
                appBarConfiguration = AppBarConfiguration(
                    setOf(
                        R.id.nav_transform, R.id.nav_reflow, R.id.nav_slideshow
                    )
                )
                setupActionBarWithNavController(navController, appBarConfiguration)
                it.setupWithNavController(navController)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val result = super.onCreateOptionsMenu(menu)
        val navView: NavigationView? = findViewById(R.id.nav_view)
        if (navView == null) {
            menuInflater.inflate(R.menu.overflow, menu)
        }
        return result
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val rootView = window.decorView.findViewById<android.view.View>(android.R.id.content)

        when (item.itemId) {
            R.id.menu_new_file -> Snackbar.make(rootView, "New File", Snackbar.LENGTH_SHORT).show()
            R.id.menu_open_file -> Snackbar.make(rootView, "Open File", Snackbar.LENGTH_SHORT).show()
            R.id.menu_save_file -> Snackbar.make(rootView, "Save", Snackbar.LENGTH_SHORT).show()
            R.id.menu_save_as_file -> Snackbar.make(rootView, "Save As", Snackbar.LENGTH_SHORT).show()
            R.id.menu_close_file -> Snackbar.make(rootView, "Close", Snackbar.LENGTH_SHORT).show()
            R.id.menu_undo -> Snackbar.make(rootView, "Undo", Snackbar.LENGTH_SHORT).show()
            R.id.menu_redo -> Snackbar.make(rootView, "Redo", Snackbar.LENGTH_SHORT).show()
            R.id.menu_cut -> Snackbar.make(rootView, "Cut", Snackbar.LENGTH_SHORT).show()
            R.id.menu_copy -> Snackbar.make(rootView, "Copy", Snackbar.LENGTH_SHORT).show()
            R.id.menu_paste -> Snackbar.make(rootView, "Paste", Snackbar.LENGTH_SHORT).show()
            R.id.menu_select_all -> Snackbar.make(rootView, "Select All", Snackbar.LENGTH_SHORT).show()
            R.id.menu_deselect_all -> Snackbar.make(rootView, "Deselect All", Snackbar.LENGTH_SHORT).show()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}