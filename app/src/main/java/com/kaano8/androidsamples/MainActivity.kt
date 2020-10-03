package com.kaano8.androidsamples

import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.kaano8.androidsamples.ui.backgroundservices.broadcastreceiver.PowerStatusReceiver
import com.kaano8.androidsamples.ui.studentmanagement.unlock.alarm.UnlockAlarmReceiver.Companion.KEY_LAUNCH_STUDENT_LIST
import com.kaano8.androidsamples.ui.studentmanagement.unlock.alarm.UnlockAlarmReceiver.Companion.KEY_STUDENT_ID
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private lateinit var powerStatusReceiver: PowerStatusReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        setupPowerStatusReceiver()

        /*val fab: FloatingActionButton = findViewById(R.id.fab)
        fab.setOnClickListener { view ->
            // Example of snackbar
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action") { LocalBroadcastManager.getInstance(this).sendBroadcast(
                        Intent(CUSTOM_BROADCAST_INTENT)
                    ) }.show()
        }*/
        setupNavDrawer()

        conditionalNavigateToStudentDetails()
    }

    private fun conditionalNavigateToStudentDetails() {
        if (intent.getBooleanExtra(KEY_LAUNCH_STUDENT_LIST, false)) {
            val studentId = intent.getLongExtra(KEY_STUDENT_ID, -1)
            val bundle = Bundle().also {
                it.putLong("selectedStudentId", studentId)
            }
            findNavController(R.id.nav_host_fragment).navigate(R.id.nav_view_student_details, bundle)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (intent?.getBooleanExtra(KEY_LAUNCH_STUDENT_LIST, false) == true) {
            val studentId = intent.getLongExtra(KEY_STUDENT_ID, -1)
            val bundle = Bundle().also {
                it.putLong("selectedStudentId", studentId)
            }
            findNavController(R.id.nav_host_fragment).navigate(R.id.nav_view_student_details, bundle)
        }
    }

    private fun setupNavDrawer() {
        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(setOf(R.id.nav_home, R.id.nav_student_list, R.id.nav_background_services), drawerLayout)
        // Setup action bar
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        bottomNavBar?.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupPowerStatusReceiver() {
        // Starting android 8.0 system broadcast must be received using dynamic broadcast receivers
        val intentFilters = IntentFilter().also {
            it.addAction(Intent.ACTION_POWER_CONNECTED)
            it.addAction(Intent.ACTION_POWER_DISCONNECTED)
            it.addAction(CUSTOM_BROADCAST_INTENT)
        }
        powerStatusReceiver = PowerStatusReceiver()
        registerReceiver(powerStatusReceiver, intentFilters)
        LocalBroadcastManager.getInstance(this).registerReceiver(powerStatusReceiver, intentFilters)
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(powerStatusReceiver)
    }

    companion object {
        private const val CUSTOM_BROADCAST_INTENT = BuildConfig.APPLICATION_ID + "CUSTOM_BROADCAST_INTENT"
    }
}