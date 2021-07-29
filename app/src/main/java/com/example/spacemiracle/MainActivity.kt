package com.example.spacemiracle

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.transition.Fade
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

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

            R.id.menu_third_activity ->{
                startActivity(Intent(this, ThirdActivity::class.java))
                return true
            }
            R.id.menu_coordinator_activity ->{
                startActivity(Intent(this, CoordinatorLayoutActivity::class.java))
                return true
            }
            R.id.menu_swipe_activity ->{
                startActivity(Intent(this, SwipeBehaviorActivity::class.java))
                return true
            }
            R.id.menu_motion_activity ->{
                startActivity(Intent(this, MotionActivity::class.java))
                return true
            }
            R.id.menu_animation_activity ->{
                startActivity(Intent(this, AnimationActivity::class.java))
                return true
            }
            R.id.menu_change_image_transform_activity ->{
                startActivity(Intent(this, ChangeImageTransformActivity::class.java))
                return true
            }
            R.id.menu_animation_path_activity ->{
                startActivity(Intent(this, AnimationPathActivity::class.java))
                return true
            }
            R.id.menu_mixing_activity ->{
                startActivity(Intent(this, MixingActivity::class.java))
                return true
            }
            R.id.menu_animation_object_activity ->{
                startActivity(Intent(this, AnimationObjectActivity::class.java))
                return true
            }
            R.id.menu_constraint_set_activity ->{
                startActivity(Intent(this, ConstraintSetActivity::class.java))
                return true
            }
            R.id.menu_animated_vector_drawable_activity ->{
                startActivity(Intent(this, AnimatedVectorDrawableActivity::class.java))
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