package test.com.myapplication;

import android.content.Intent;

import com.example.Paths;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by savani on 9/11/16.
 */
public class MessageReceiverService extends WearableListenerService {

    @Override
    public void onMessageReceived(MessageEvent messageEvent) {
        super.onMessageReceived(messageEvent);
        if (messageEvent.getPath().equals(Paths.START_MEASUREMENT)) {
            startService(new Intent(this, SensorsService.class));
        }

        if (messageEvent.getPath().equals(Paths.STOP_MEASUREMENT)) {
            stopService(new Intent(this, SensorsService.class));
        }

    }


}
