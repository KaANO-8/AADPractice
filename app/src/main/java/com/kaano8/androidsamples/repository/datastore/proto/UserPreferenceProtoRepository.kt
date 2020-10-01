package com.codelab.android.datastore.data

import android.content.Context
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import androidx.datastore.migrations.SharedPreferencesMigration
import androidx.datastore.migrations.SharedPreferencesView
import com.codelab.android.datastore.UserPreferencesProto.SortOrder
import com.codelab.android.datastore.UserPreferencesProto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import java.io.IOException

private const val USER_PREFERENCES_NAME = "user_preferences"

/**
 * Class that handles saving and retrieving user preferences
 */
class UserPreferencesProtoRepository(context: Context) {

    private val sharedPrefsMigration = SharedPreferencesMigration(
        context,
        USER_PREFERENCES_NAME
    ) { sharedPrefs: SharedPreferencesView, currentData: UserPreferencesProto ->
        // Define the mapping from SharedPreferences to UserPreferences
        if (currentData.sortOrder == SortOrder.UNSPECIFIED) {
            currentData.toBuilder().setSortOrder(
                SortOrder.valueOf(
                    sharedPrefs.getString(
                        "",SortOrder.NONE.name)!!
                )
            ).build()
        } else {
            currentData
        }
    }

    private val dataStore: DataStore<UserPreferencesProto> =
        context.createDataStore(fileName = "user_prefs.proto", serializer = UserPreferencesSerializer, migrations = listOf(sharedPrefsMigration))

    private val TAG: String = "UserPreferencesRepo"

    val userPreferencesFlow: Flow<UserPreferencesProto> = dataStore.data
        .catch { exception ->
            // dataStore.data throws an IOException when an error is encountered when reading data
            if (exception is IOException) {
                Log.e(TAG, "Error reading sort order preferences.", exception)
                emit(UserPreferencesProto.getDefaultInstance())
            } else {
                throw exception
            }
        }

    suspend fun updateShowCompleted(completed: Boolean) {
        dataStore.updateData { preferences ->
            preferences.toBuilder().setShowCompleted(completed).build()
        }
    }

    suspend fun enableSortByDeadline(enable: Boolean) {
        // updateData handles data transactionally, ensuring that if the sort is updated at the same
        // time from another thread, we won't have conflicts
        dataStore.updateData { preferences ->
            val currentOrder = preferences.sortOrder
            val newSortOrder =
                if (enable) {
                    if (currentOrder == SortOrder.BY_PRIORITY) {
                        SortOrder.BY_DEADLINE_AND_PRIORITY
                    } else {
                        SortOrder.BY_DEADLINE
                    }
                } else {
                    if (currentOrder == SortOrder.BY_DEADLINE_AND_PRIORITY) {
                        SortOrder.BY_PRIORITY
                    } else {
                        SortOrder.NONE
                    }
                }
            preferences.toBuilder().setSortOrder(newSortOrder).build()
        }
    }
}
