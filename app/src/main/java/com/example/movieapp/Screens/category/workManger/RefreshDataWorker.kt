package com.example.movieapp.Screens.category.workManger

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.movieapp.data.repository.categories.CategoryIRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
open class RefreshDataWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted params: WorkerParameters,
    val repo: CategoryIRepository
) : CoroutineWorker(context, params) {

    @SuppressLint("RestrictedApi")
    override suspend fun doWork(): Result {

        return try {
            repo.getGenresFromApi()
            Log.e(TAG, "doWork: ", )
            Result.Success()
        } catch (ex: Exception) {
            Log.e(TAG, "doWork: ", )

            Result.failure()
        }
    }
}