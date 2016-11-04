package cmpe295.project.com.healthmonitor;

/**
 * Created by savani on 9/10/16.
 */

import android.content.Context;
import android.location.Criteria;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.SensorFieldsKeys;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.PutDataMapRequest;
import com.google.android.gms.wearable.PutDataRequest;
import com.google.android.gms.wearable.Wearable;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;


public class SensorReporting {
    private static SensorReporting mInstance;
    private Context mContext;
    private GoogleApiClient mGoogleApiClient;
    private ExecutorService mExecutorService;
    private static final int CLIENT_CONNECTION_TIMEOUT = 15000;
    private static final String TAG="Wearable Sensors";

    private SensorReporting(Context context){
        mContext = context;
        mExecutorService = Executors.newCachedThreadPool();
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
        .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
            @Override
            public void onConnected(Bundle connectionHint) {
                Log.d(TAG, "onConnected: " + connectionHint);
                // Now you can use the Data Layer API
            }
            @Override
            public void onConnectionSuspended(int cause) {
                Log.d(TAG, "onConnectionSuspended: " + cause);
            }
        }).addApi(Wearable.API).build();
    }

    public static SensorReporting getInstance(Context context){
        if(mInstance == null){
            Log.d(TAG,"Got instance");
            mInstance = new SensorReporting(context.getApplicationContext());
        }
        return mInstance;
    }

    public void sendReadings(final int sensorType, final int accuracy, final long timestamp,
                             final float[] values) {
            Log.d(TAG, "send readings start");
        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("/healthcare/"+sensorType);
                putDataMapRequest.getDataMap().putInt(SensorFieldsKeys.SENSOR_TYPE, sensorType);
                putDataMapRequest.getDataMap().putInt(SensorFieldsKeys.ACCURACY, accuracy);
               // putDataMapRequest.getDataMap().putLong(SensorFieldsKeys.TIMESTAMP, timestamp);
                putDataMapRequest.getDataMap().putLong(SensorFieldsKeys.TIMESTAMP, System.nanoTime());
                putDataMapRequest.getDataMap().putFloatArray(SensorFieldsKeys.VALUES, values);
                putDataMapRequest.setUrgent();
                PutDataRequest pdr = putDataMapRequest.asPutDataRequest();
                send(pdr);
            }
        });
    }
    private boolean validateConnection() {
        if (mGoogleApiClient.isConnected()) {
            return true;
        }
        ConnectionResult result = mGoogleApiClient.blockingConnect(CLIENT_CONNECTION_TIMEOUT,
                                  TimeUnit.MILLISECONDS);
        return result.isSuccess();
    }

    private void send(PutDataRequest putDataReq) {
        Log.d(TAG, "data send try");
        if (validateConnection()) {
            PendingResult<DataApi.DataItemResult> pendingResult =
                    Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq);
            pendingResult.setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                @Override
                public void onResult(final DataApi.DataItemResult result) {
                    if (result.getStatus().isSuccess()) {
                        Log.d(TAG, "Data item set: " + result.getDataItem().getUri());

                    }
                }
            });

//            Wearable.DataApi.putDataItem(mGoogleApiClient, putDataReq).setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
//                @Override
//                public void onResult(@NonNull DataApi.DataItemResult dataItemResult) {
//                    Log.v(TAG, "data sent  "+dataItemResult.getDataItem()+" " +
//                        " ,success "+dataItemResult.getStatus().getStatusMessage());
//
//                }
//            });
        }
    }
        void fetchLocation(){
//        Criteria criteria = new Criteria();
//        criteria.setAccuracy(Criteria.ACCURACY_FINE);
//        criteria.setAltitudeRequired(false);
//        criteria.setBearingRequired(false);
//        criteria.setCostAllowed(true);
//        criteria.setPowerRequirement(Criteria.POWER_LOW);
//        String provider = locationManager.getBestProvider(criteria, true);
//        if(provider != null) {
//            locationManager.requestLocationUpdates(provider, 2 * 60 * 1000, 10, locationListenerBest);
//        }
   // }
    }
}
