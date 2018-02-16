package com.example.stanislavcavajda.memoryblitz

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.widget.Toast

/**
 * Created by stanislavcavajda on 10/11/2017.
 */
class NotificationReceiver() : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {

        var notificationManager:NotificationManager = context?.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        var repeatingActivity = Intent(context,StartActivity::class.java)
        repeatingActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        var pendingIntent = PendingIntent.getActivity(context,100,repeatingActivity,PendingIntent.FLAG_UPDATE_CURRENT)


        var builder = NotificationCompat.Builder(context).setContentIntent(pendingIntent)
        builder.setContentTitle("Memory Blitz")
        builder.setContentText("Lets play Memory Blitz Again!!")
        builder.setSmallIcon(R.mipmap.ic_launcher)
        builder.setAutoCancel(true)

        notificationManager.notify(100,builder.build())

    }

}