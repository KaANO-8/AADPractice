package com.kaano8.androidsamples.workmanager

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.kaano8.androidsamples.database.gift.Gift
import com.kaano8.androidsamples.database.AppDatabase
import kotlinx.coroutines.coroutineScope

class SeedDatabaseWorker(context: Context, workerParameters: WorkerParameters) :
    CoroutineWorker(context, workerParameters) {
    override suspend fun doWork(): Result = coroutineScope {
        try {
            applicationContext.assets.open("gifts.json").use { inputStream ->
                JsonReader(inputStream.reader()).use { jsonReader ->
                    val giftType = object : TypeToken<List<Gift>>() {}.type
                    val giftList: List<Gift> = Gson().fromJson(jsonReader, giftType)

                    val database = AppDatabase.getInstance(applicationContext)
                    database.giftDao.insertAll(giftList)
                    Result.success()
                }
            }
        } catch (exception: Exception) {
            Log.e("Exception", "Error seeding database")
            Result.failure()
        }
    }
}