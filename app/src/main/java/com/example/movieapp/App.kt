package com.example.movieapp

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.*
import com.example.movieapp.Utils.Constants.COROUTINE_WORKER
import com.example.movieapp.Screens.category.workManger.RefreshDataWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltAndroidApp
class App:Application() , Configuration.Provider {

    @Inject
    lateinit var workerFactory: HiltWorkerFactory


    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setWorkerFactory(workerFactory)
            .build()


    override fun onCreate() {
        super.onCreate()
        setUpWorkManager()
    }

    private fun setUpWorkManager() {
        val constraints  = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .setRequiresCharging(true)
            .setRequiresBatteryNotLow(true)
            .build()

        val repeatingRequest = PeriodicWorkRequest.Builder(
            RefreshDataWorker::class.java,
            1, TimeUnit.DAYS
        )
           // .setConstraints(constraints)
            .addTag(COROUTINE_WORKER)
            .build()

        val workManager = WorkManager.getInstance(this)

        workManager.enqueueUniquePeriodicWork(
            COROUTINE_WORKER,
            ExistingPeriodicWorkPolicy.REPLACE, repeatingRequest
        )

    }
}