package com.kaano8.androidsamples.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.kaano8.androidsamples.R

class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings_prefs, rootKey)
    }
}