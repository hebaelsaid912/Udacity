package com.udacity

import android.app.*
import android.app.Notification
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

private const val CHANNEL_ID = "channelId"
class Notification(private val context: Context){
    private lateinit var notify: Notification
    private lateinit var notifyManger: NotificationManagerCompat
    init {
        createNotifyChannel(context)
    }

    private fun createNotifyChannel(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "download",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.BLUE
                enableLights(true)
            }
            val manager = context.getSystemService(AppCompatActivity.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
        }
    }

     fun createAcceptedNotification(notificationId:Int,radioName:String,status:Boolean) {
        val intent = Intent(context, DetailActivity::class.java)
         intent.putExtra("file_name",radioName)
         intent.putExtra("status",status)
        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntentWithParentStack(intent)
            getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        }

        notify = NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText("File DownLoad Successfully")
            .setSmallIcon(R.drawable.ic_assistant_black_24dp)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
           // .setContentIntent(pendingIntent)
            .addAction(R.drawable.abc_vector_test, "Check the status",
             pendingIntent)
            .build()


        notifyManger = NotificationManagerCompat.from(context)
        notifyManger.notify(notificationId, notify)
    }

}
