package cmpe295.project.com.healthmonitor;


import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
//import android.support.v4.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;


/**
 * Created by savani on 8/30/16.
 */
public class FMService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("Message recieved", "Message recieved");
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("Notification",remoteMessage );
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity
                     (this, 0, i, PendingIntent.FLAG_ONE_SHOT);
        NotificationCompat.Builder notification = new NotificationCompat.Builder(this)
                               .setSmallIcon(R.mipmap.ic_launcher).
                                setContentTitle("title").
                                setContentIntent(pendingIntent)
                                 .setContentText("message")
                                .setAutoCancel(true);
        NotificationManager notificationManager = (NotificationManager)
                                            getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(1,notification.build());





    }
}
