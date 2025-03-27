package com.karan.expensetracker.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import javax.inject.Inject

@HiltWorker
class ApiWorker @AssistedInject constructor(
    @Assisted context : Context,
    @Assisted workerParams : WorkerParameters
) : CoroutineWorker(context , workerParams) {


    override suspend fun doWork(): Result {
        Thread.sleep(3000)
        val message = inputData.getString("data")

        Log.e("TAG", "doWork: work is done $message", )
        return Result.success()
    }

}