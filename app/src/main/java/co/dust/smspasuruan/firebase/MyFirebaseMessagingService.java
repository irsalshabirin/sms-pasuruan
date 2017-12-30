package co.dust.smspasuruan.firebase;


import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

import co.dust.smspasuruan.BuildConfig;
import co.dust.smspasuruan.DetailIncomingMailActivity;
import co.dust.smspasuruan.IncomingMailActivity2;
import co.dust.smspasuruan.R;
import co.dust.smspasuruan.tools.CommonConstants;
import co.dust.smspasuruan.tools.CredentialStorage;


/**
 * Created by irsal on 7/15/16.
 */

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    public static final String PERIHAL = "perihal";
    public static final String NOMOR = "nomor";

    private CredentialStorage credentialStorage;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // [START_EXCLUDE]
        // There are two types of messages data messages and notification messages. Data messages are handled
        // here in onMessageReceived whether the app is in the foreground or background. Data messages are the type
        // traditionally used with GCM. Notification messages are only received here in onMessageReceived when the app
        // is in the foreground. When the app is in the background an automatically generated notification is displayed.
        // When the user taps on the notification they are returned to the app. Messages containing both notification
        // and data payloads are treated as notification messages. The Firebase console always sends notification
        // messages. For more see: https://firebase.google.com/docs/cloud-messaging/concept-options
        // [END_EXCLUDE]
        credentialStorage = new CredentialStorage(getApplicationContext());
//        roleId = sessionManager.getRoleId();

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "From: " + remoteMessage.getFrom());

            if (remoteMessage.getNotification() != null) {
                Log.e(TAG, "Notification Message Title: " + remoteMessage.getNotification().getTitle());
                Log.e(TAG, "Notification Message Body: " + remoteMessage.getNotification().getBody());
            }
        }


        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated. See sendNotification method below.
        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Map<String, String> data = remoteMessage.getData();

        if (BuildConfig.DEBUG) {
            if (notification != null) {
                Log.e(TAG, "data : " + notification.toString());
            }

            Log.e(TAG, "data : " + data.toString());
        }

        // Calling method to generate notification
//        sendNotification(remoteMessage.getNotification().getBody());
        sendNotification(notification, data);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotification(RemoteMessage.Notification notification, Map<String, String> data) {
//        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);

        Intent resultIntent;

        if (credentialStorage.isAlreadyLogin()) {
            // pastikan usernya sudah login

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());

            resultIntent = new Intent(getBaseContext(), DetailIncomingMailActivity.class);
            resultIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

            Bundle bundle = new Bundle();
            try {
                String str = data.get(CommonConstants.TAG_SURAT_MASUK_ID);
                bundle.putString(CommonConstants.TAG_SURAT_MASUK_ID, str);
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            resultIntent.putExtras(bundle);

            Intent intent = new Intent(this, IncomingMailActivity2.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            stackBuilder.addNextIntent(intent);
            stackBuilder.addNextIntent(resultIntent);

            sendNotif(stackBuilder, notification, data);


        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void sendNotif(TaskStackBuilder stackBuilder, RemoteMessage.Notification notification, Map<String, String> data) {

        if (BuildConfig.DEBUG) {
            Log.e(TAG, "proses send notif - 1");
        }
//            resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        resultIntent.setAction(Long.toString(System.currentTimeMillis()));

        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if (BuildConfig.DEBUG) {
            Log.e(TAG, "proses send notif - 2");
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)

                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())

//                .setContentTitle(data.get(NOMOR))
//                .setContentText(data.get(PERIHAL))

                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setContentIntent(resultPendingIntent);

        if (BuildConfig.DEBUG) {
            Log.e(TAG, "proses send notif - 3");
        }
        notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
        notificationBuilder.setLights(Color.RED, 1000, 300);
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "proses send notif - 4");
        }
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationManager.notify(132 /* ID of notification */, notificationBuilder.build());

        if (BuildConfig.DEBUG) {
            Log.e(TAG, "proses send notif - 5");
        }


    }

//    private void sendNotif(TaskStackBuilder resultIntent, RemoteMessage.Notification notification, Map<String, String> data) {
//
////        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        resultIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//
//        PendingIntent resultPendingIntent = PendingIntent.getActivity(this, 0/* Request code */, resultIntent.get, PendingIntent.FLAG_UPDATE_CURRENT);
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle(notification.getTitle())
//                .setContentText(notification.getBody())
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(resultPendingIntent);
//
//        notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
//        notificationBuilder.setLights(Color.GREEN, 1000, 300);
//
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(Integer.parseInt(data.get(CommonConstants.TAG_SURAT_MASUK_ID)) /* ID of notification */, notificationBuilder.build());
//    }

//    private void sendNotif(Intent[] arrIntent, RemoteMessage.Notification notification, int uniqueId) {
//
//        final PendingIntent resultPendingIntent = PendingIntent.getActivities(
//                this,
//                0,/* Request code */
//                arrIntent,
//                PendingIntent.FLAG_UPDATE_CURRENT
//        );
//
//
//        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//
//        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.mipmap.ic_launcher)
//                .setContentTitle(notification.getTitle())
//                .setContentText(notification.getBody())
//                .setAutoCancel(true)
//                .setSound(defaultSoundUri)
//                .setContentIntent(resultPendingIntent);
//
//        notificationBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
//        notificationBuilder.setLights(Color.GREEN, 1000, 300);
//
//
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(uniqueId /* ID of notification */, notificationBuilder.build());
//    }
}