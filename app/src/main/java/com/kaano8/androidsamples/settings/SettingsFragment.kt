package com.kaano8.androidsamples.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_NO
import androidx.appcompat.app.AppCompatDelegate.MODE_NIGHT_YES
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.kaano8.androidsamples.R

class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_prefs, rootKey)
        val darkModeSwitch = findPreference<SwitchPreferenceCompat>("darkMode")
        darkModeSwitch?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                if (newValue == true)
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_YES)
                else
                    AppCompatDelegate.setDefaultNightMode(MODE_NIGHT_NO)
                true
            }
    }
}