package com.app.gcp.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.app.NotificationCompat
import androidx.core.net.toUri
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.app.gcp.R
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class FileDownloadWorker(
    private val context: Context,
    private val workerParameters: WorkerParameters
) : Worker(context, workerParameters) {
    private val notificationManager =
        applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun doWork(): Result {
        displayNotification()

        val mimeType = when(workerParameters.inputData.getString(Constants.KEY_FILE_TYPE)){
            "PDF" -> "application/pdf"
            "PNG" -> "image/png"
            "MP4" -> "video/mp4"
            else -> ""
        }
        val filename = workerParameters.inputData.getString(Constants.KEY_FILE_NAME)
        val url = workerParameters.inputData.getString(Constants.KEY_FILE_URL)
        url?.let {
            return try {
                val uri = downloadFileFromUri(url,mimeType,filename )
                uri?.let {

                }
                notificationManager.cancel(Constants.NOTIFICATION_ID)
                Result.success(workDataOf(Constants.KEY_FILE_URI to uri.toString()))
            } catch (e: Exception) {
                e.printStackTrace()
                Result.failure()
            }

        }
        return Result.failure()
    }


    private fun displayNotification() {
        val channel = NotificationChannel(
            Constants.CHANNEL_NAME,
            Constants.CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        channel.enableVibration(false)
        notificationManager.createNotificationChannel(channel)

        val notificationBuilder =
            NotificationCompat.Builder(applicationContext, Constants.CHANNEL_NAME)


        notificationBuilder
            .setContentTitle(Constants.CHANNEL_DESC)
            .setSmallIcon(R.drawable.ic_logo)

        notificationManager.notify(Constants.NOTIFICATION_ID, notificationBuilder.build())
    }


    private fun downloadFileFromUri(url: String,mimeType: String, filename: String?): Uri? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

            val contentValues = ContentValues().apply {
                put(MediaStore.MediaColumns.DISPLAY_NAME, filename)
                put(MediaStore.MediaColumns.MIME_TYPE, mimeType)
                put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            }

            val resolver = context.contentResolver
            val uri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            return if (uri != null) {
                URL(url).openStream().use { input ->
                    resolver.openOutputStream(uri).use { output ->
                        input.copyTo(output!!, DEFAULT_BUFFER_SIZE)
                    }
                }
                uri
            } else {
                null
            }

        } else {

            val target = filename?.let {
                File(
                    Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                    it
                )
            }
            URL(url).openStream().use { input ->
                FileOutputStream(target).use { output ->
                    input.copyTo(output)
                }
            }

            return target?.toUri()
        }
    }

}