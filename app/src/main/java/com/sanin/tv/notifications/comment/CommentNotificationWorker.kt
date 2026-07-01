package com.sanin.tv.notifications.comment
import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.sanin.tv.util.Logger
import eu.kanade.tachiyomi.data.notification.Notifications
class CommentNotificationWorker(appContext: Context, workerParams: WorkerParameters) :    CoroutineWorker(appContext, workerParams) {
    override suspend 
fun doWork(): Result {        
        L
if (System.currentTimeMillis() - lastCheck < 60000) {            Logger.log("CommentNotificationWorker: doWork skipped")
return Result.success()        }
lastCheck = System.currentTimeMillis()
return if (CommentNotificationTask().execute(applicationContext)) {            Result.success()
} else {            Logger.log("CommentNotificationWorker: doWork failed")
        Result.retry()        }
}

enum class NotificationType(
val id: String) {        
        C

companion object {
    val checkIntervals = arrayOf(0L, 480, 720, 1440)        const val WORK_NAME = "com.sanin.tv.notifications.comment.CommentNotificationWorker"
        
private var lastCheck = 0L    }}