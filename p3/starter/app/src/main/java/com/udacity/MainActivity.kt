package com.udacity

import android.app.DownloadManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

private const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    //private var downloadID: MutableLiveData<Long> = MutableLiveData()
    private var requestID: MutableLiveData<Long> = MutableLiveData()
    private lateinit var sendNotification: Notification

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        sendNotification = Notification(context = this)
        registerReceiver(receiver, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))

        custom_button.setOnClickListener {
            if(radio_1.isChecked || radio_2.isChecked || radio_3.isChecked) {
                custom_button.isClickable = true
                download()
            }else{
                Toast.makeText(this,"Please Select The File To Download",Toast.LENGTH_LONG).show()
                custom_button.clearAnimation()
            }
        }
        requestID.observe(this,Observer {
            Log.d(TAG, "onCreate: on requestID observe")
            Log.d(TAG, "onCreate: on requestID observe: requestID.value ${requestID.value}")
            if (radio_1.isChecked)
            sendNotification.createAcceptedNotification(requestID.value!!.toInt(), radioName = radio_1.text.toString(), status = true)
            if (radio_2.isChecked)
            sendNotification.createAcceptedNotification(requestID.value!!.toInt(), radioName = radio_2.text.toString(), status = false)
            if (radio_2.isChecked)
            sendNotification.createAcceptedNotification(requestID.value!!.toInt(), radioName = radio_2.text.toString(), status = true)
        })
    }

    private val receiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            Log.d(TAG, "onReceive: id: $id ")
            requestID.value = id
        }
    }

    private fun download() {
        val request =
            DownloadManager.Request(Uri.parse(URL))
                .setTitle(getString(R.string.app_name))
                .setDescription(getString(R.string.app_description))
                .setRequiresCharging(false)
                .setAllowedOverMetered(true)
                .setAllowedOverRoaming(true)

        val downloadManager = getSystemService(DOWNLOAD_SERVICE) as DownloadManager
        val downloadID =
            downloadManager.enqueue(request)// enqueue puts the download request in the queue.

    }

    companion object {
        private const val URL =
            "https://github.com/udacity/nd940-c3-advanced-android-programming-project-starter/archive/master.zip"

    }

}
