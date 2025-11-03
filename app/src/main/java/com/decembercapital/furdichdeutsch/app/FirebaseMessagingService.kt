package com.decembercapital.furdichdeutsch.app

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CustomFirebaseMessagingService : FirebaseMessagingService() {

    companion object {
        private const val TAG = "FCMService"
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "Refreshed token: $token")

        // Send token to your backend server
        sendTokenToServer(token)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        Log.d(TAG, "From: ${remoteMessage.from}")

        // Check if message contains a notification payload
        remoteMessage.notification?.let { notification ->
            Log.d(TAG, "Notification Title: ${notification.title}")
            Log.d(TAG, "Notification Body: ${notification.body}")

            val title = notification.title ?: getString(R.string.default_notification_title)
            val body = notification.body ?: ""
            val url = remoteMessage.data["url"] ?: ""

            // Show notification
            NotificationHelper.showNotification(
                context = this,
                title = title,
                message = body,
                url = url
            )
        }

        // Check if message contains a data payload
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")

            // If you want to handle data-only messages without notification
            // You can process them here
            val title = remoteMessage.data["title"]
            val body = remoteMessage.data["body"]
            val url = remoteMessage.data["url"]

            if (title != null && body != null) {
                NotificationHelper.showNotification(
                    context = this,
                    title = title,
                    message = body,
                    url = url ?: ""
                )
            }
        }
    }

    private fun sendTokenToServer(token: String) {
        // TODO: Implement sending token to your backend server
        // Example:
        // val apiService = RetrofitClient.getApiService()
        // apiService.registerToken(token)

        Log.d(TAG, "Token should be sent to server: $token")

        // For now, just log it. You'll need to implement your backend integration
        // The token can be retrieved anytime using:
        // FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
        //     if (task.isSuccessful) {
        //         val token = task.result
        //     }
        // }
    }
}
