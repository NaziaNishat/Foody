package com.example.nazia_000.DesPatPro;

import android.app.NotificationManager;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService{

    //opening the application on recieving notification


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        try{
            String notification_title= "New Request for " +remoteMessage.getNotification().getTitle() + " blood group";
            String notification_message = "Needed Amount: " + remoteMessage.getNotification().getBody();

            NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(notification_title)
                    .setContentText(notification_message)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);




            int notificationId = (int)System.currentTimeMillis();
            NotificationManager manager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
            manager.notify(notificationId,builder.build());

            }catch(Exception e){

        }
    }
}
