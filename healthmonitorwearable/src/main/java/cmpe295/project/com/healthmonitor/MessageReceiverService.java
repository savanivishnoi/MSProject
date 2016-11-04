package cmpe295.project.com.healthmonitor;

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
        System.out.println("Inside wear lisetener "+messageEvent.toString());
        super.onMessageReceived(messageEvent);
        System.out.println(messageEvent.getPath() +"1");
        if (messageEvent.getPath().contains(Paths.START_MEASUREMENT) ){
            //equals(Paths.START_MEASUREMENT)) {
            System.out.println("inside");
            startService(new Intent(this, SensorsService.class));
        }

        if (messageEvent.getPath().equals(Paths.STOP_MEASUREMENT)) {
            stopService(new Intent(this, SensorsService.class));
        }

    }


}
