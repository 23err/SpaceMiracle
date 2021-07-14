package com.example.spacemiracle

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.collection.arrayMapOf
import com.example.spacemiracle.databinding.FragmentSettingBinding

class SettingFragment : Fragment() {

    private var _binding: FragmentSettingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val currentThemeId = getThemeSetting()
        val currentThemeName = listItemsTheme[currentThemeId] ?: listItemsTheme[R.style.Theme_SpaceMiracle]
        binding.tvThemeName.text = currentThemeName

        binding.themeChoise.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setIcon(R.drawable.ic_baseline_bookmarks_24)
                setTitle("Смена темы")
                var checkItem = listItemsTheme.values.indexOf(currentThemeName)
                if (checkItem==-1) {
                    checkItem = 0
                }
                setSingleChoiceItems(
                    listItemsTheme.values.toTypedArray(),
                    checkItem
                ) { dialogInterface: DialogInterface, i: Int ->
                    Toast.makeText(requireContext(), listItemsTheme[i], Toast.LENGTH_SHORT).show()
                    val selectedId = listItemsTheme.keys.elementAt(i)
                    val selectedName = listItemsTheme.values.elementAt(i)
                    binding.tvThemeName.text = selectedName
                    saveThemeSetting(selectedId)
                    dialogInterface.dismiss()
                    requireActivity().apply {
                        supportFragmentManager.beginTransaction().remove(this@SettingFragment).commit()
                        recreate()
                    }
                }
            }.create().show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun saveThemeSetting(value: Int) {
        val sp = getSettingsSharedPreference()
        val editor = sp.edit()
        editor.apply {
            putInt(THEME_SETTING, value)
            commit()
        }
    }

    private fun getThemeSetting():Int{
        val sp = getSettingsSharedPreference()
        return sp.getInt(THEME_SETTING, listItemsTheme.keyAt(0))
    }

    private fun getSettingsSharedPreference(): SharedPreferences {
        return requireActivity().getSharedPreferences(SETTINGS, Context.MODE_PRIVATE)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SettingFragment()

        const val SETTINGS = "settings"
        const val THEME_SETTING = "theme_setting"
        private val listItemsTheme = arrayMapOf<Int, String>(
            R.style.Theme_SpaceMiracle to "Сиреневая",
            R.style.ThemeGreen to "Зеленая",
            R.style.ThemeRed to "Красная"
        )

        private const val TAG = "SettingFragment"
    }
}