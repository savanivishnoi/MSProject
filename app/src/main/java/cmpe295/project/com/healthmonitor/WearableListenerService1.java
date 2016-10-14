package cmpe295.project.com.healthmonitor;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import com.example.SensorFieldsKeys;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageEvent;
import com.google.android.gms.wearable.WearableListenerService;
import com.google.android.gms.wearable.zzd;

import java.util.Arrays;

/**
 * Created by savani on 10/2/16.
 */

public class WearableListenerService1 extends WearableListenerService {
    private LocalDatabase mDBHandler;
    private static final String TAG = "Wearable Listener";
    WearableListenerService1(){
        mDBHandler = new LocalDatabase(this);
    }
//    @Override
//    public void onMessageReceived(MessageEvent messageEvent) {
//        super.onMessageReceived(messageEvent);
//
//        String event = messageEvent.getPath();
//
//        Log.d("Listclicked", event);
//
//        String [] message = event.split("--");
//
////        if (message[0].equals(SERVICE_CALLED_WEAR)) {
//
////            startActivity(new Intent((Intent) Listactivity.getInstance().tutorials.get(message[1]))
////                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
//      //  }
//        Intent i = new Intent(this, MainActivity.class);
//            startActivity(i);
//    }

    @Override
    public void onDataChanged(DataEventBuffer dataEventBuffer) {
      //  super.onDataChanged(dataEventBuffer);

        //Log.d("Listclicked ", dataEventBuffer.);
        //Intent i = new Intent(this, MainActivity.class);
        //startActivity(i);
        for(DataEvent dataEvent: dataEventBuffer){
            DataItem dataItem = dataEvent.getDataItem();
            Uri uri = dataItem.getUri();
            String path = uri.getPath();
            if (path.startsWith("/healthcare/")) {
                retrieveSensorData(
                        Integer.parseInt(uri.getLastPathSegment()),
                        DataMapItem.fromDataItem(dataItem).getDataMap()
                );
            }

        }

    }
    void retrieveSensorData(int sensorType, DataMap dataMap){
        int accuracy = dataMap.getInt(SensorFieldsKeys.ACCURACY);
        long timestamp = dataMap.getLong(SensorFieldsKeys.TIMESTAMP);
        float[] values = dataMap.getFloatArray(SensorFieldsKeys.VALUES);
     //   Toast.makeText(this, sensorType+" "+values[0], Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Received sensor data " + sensorType + " = " + Arrays.toString(values));
        //add to database
        SensorDataModel sm = new SensorDataModel(sensorType, accuracy, timestamp, values[0]);
        mDBHandler.addEntry(sm);


    }

//
//    @Override
//    public void onNotificationReceived(zzd zzd) {
//        super.onNotificationReceived(zzd);
//        Log.d("Listclicked", "notification recvd");
//        Intent i = new Intent(this, MainActivity.class);
//        startActivity(i);
//    }
}
