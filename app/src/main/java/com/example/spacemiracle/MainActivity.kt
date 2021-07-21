package com.example.spacemiracle

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationMenu
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        setCurrentTheme()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViews()
        setBadge()
        setupNavController()
        

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menu?.let {
            menuInflater.inflate(R.menu.main_menu, it)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_setting -> {
                val fragment = SettingFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .add(R.id.fragmentContainer, fragment)
                    .addToBackStack("null")
                    .commit()
                return true
            }
            R.id.menu_second_activity -> {
                startActivity(Intent(this, SecondActivity::class.java))
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }


    private fun findViews() {
        bottomNav = findViewById(R.id.bottomNav)
    }

    private fun setupNavController() {
        val navController = findNavController(R.id.fragmentContainer)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.menu_main,
                R.id.menu_second,
                R.id.menu_third
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        bottomNav.setupWithNavController(navController)
    }

    private fun setBadge() {
        val badge = bottomNav.getOrCreateBadge(R.id.menu_second)
        badge.isVisible = true
        badge.number = 7
    }

    private fun setCurrentTheme(){
        val currentThemeId = packageManager.getActivityInfo(componentName, 0).themeResource
        val themeIdFromSetting = getThemeSetting()
        if (currentThemeId != themeIdFromSetting){
            setTheme(themeIdFromSetting)
        }
    }

    private fun getThemeSetting(): Int {
        val sp = getSettingSharedPreference()
        return sp.getInt(SettingFragment.THEME_SETTING, R.style.Theme_SpaceMiracle)
    }

    private fun getSettingSharedPreference(): SharedPreferences {
        return getSharedPreferences(SettingFragment.SETTINGS, Context.MODE_PRIVATE)
    }

    companion object{
        private const val TAG = "MainActivity"
    }
}