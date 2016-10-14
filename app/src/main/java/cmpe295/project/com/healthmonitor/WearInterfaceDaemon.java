package cmpe295.project.com.healthmonitor;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.Wearable;
import com.google.android.gms.wearable.WearableListenerService;

/**
 * Created by savani on 10/1/16.
 */

public class WearInterfaceDaemon extends  WearableListenerService {
    private GoogleApiClient mGoogleApiClient;
    private static final String TAG = "WearInterfaceDaemon";
//    private RecievingDevice deviceClient;

    @Override
    public void onCreate() {
        super.onCreate();

     //   deviceClient = RecievingDevice.getInstance(this);
    }


//    @Override
//    public void onCreate() {
//        super.onCreate();
//        mGoogleApiClient = new GoogleApiClient.Builder(this).addApi(Wearable.API)
//                                            .addConnectionCallbacks(this)
//                                            .addOnConnectionFailedListener(this)
//                                            .build();
//        Wearable.DataApi.addListener(mGoogleApiClient, this);
//        Log.d(TAG, "on create.. client connect" );
//
//    }
//
//    @Nullable
//    @Override
//    public IBinder onBind(Intent intent) {
//        return null;
//    }
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        Wearable.DataApi.addListener(mGoogleApiClient, this);
//        Log.d(TAG, "listneer attached");
//    }
//
//    @Override
//    public int onStartCommand(Intent intent, int flags, int startId) {
//        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
//        return super.onStartCommand(intent,flags,startId);
//    }
//
//
//    @Override
//    public void onConnectionSuspended(int i) {
//
//
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//
//    }
//
//    @Override
//    public void onDataChanged(DataEventBuffer dataEvents) {
//        Log.d(TAG, "data changed event");
//        for (DataEvent event : dataEvents) {
//            if (event.getType() == DataEvent.TYPE_CHANGED) {
//                // DataItem changed
//                DataItem item = event.getDataItem();
//                if (item.getUri().getPath().compareTo("healthcare") == 0) {
//                    Log.d(TAG, "dataa");
//                    DataMap dataMap = DataMapItem.fromDataItem(item).getDataMap();
//                    //updateCount(dataMap.getInt(COUNT_KEY));
//                    Log.d(TAG, dataMap.getDataMap("").toString());
//                }
//            } else if (event.getType() == DataEvent.TYPE_DELETED) {
//                // DataItem deleted
//            }
//            Intent i = new Intent(this, MainActivity.class);
//            startActivity(i);
//
//        }
//    }
}
