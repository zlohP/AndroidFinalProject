package com.example.androidfinalproject

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessageService : FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        if(message.data.isNotEmpty()){
            Log.d("25android", "${message.data}")


            val helper = MyNotificationHelper(this)
            helper.showNotification(message.data.get("title"), message.data.get("value"))
        }
    }

}