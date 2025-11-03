package com.decembercapital.furdichdeutsch.app

import android.app.Application
import android.util.Log
import com.google.firebase.FirebaseApp
import com.google.firebase.messaging.FirebaseMessaging

class FourDDApplication : Application() {

    companion object {
        private const val TAG = "FourDDApplication"
    }

    override fun onCreate() {
        super.onCreate()

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        // Create notification channel
        NotificationHelper.createNotificationChannel(this)

        // Get FCM token
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result
            Log.d(TAG, "FCM Token: $token")

            // TODO: Send token to your backend server
            // This token should be sent to your server so it can send push notifications
        }
    }
}
