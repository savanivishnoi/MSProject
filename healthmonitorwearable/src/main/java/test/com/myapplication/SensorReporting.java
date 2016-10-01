package test.com.myapplication;

/**
 * Created by savani on 9/10/16.
 */

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.SensorFieldsKeys;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
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
        mGoogleApiClient = new GoogleApiClient.Builder(mContext).addApi(Wearable.API).build();
    }

    public static SensorReporting getInstance(Context context){
        if(mInstance == null){
            new SensorReporting(context.getApplicationContext());
        }
        return mInstance;
    }

    public void sendReadings(final int sensorType, final int accuracy, final long timestamp, final float[] values) {

        mExecutorService.submit(new Runnable() {
            @Override
            public void run() {
                PutDataMapRequest putDataMapRequest = PutDataMapRequest.create("HealthCare/"+sensorType);
                putDataMapRequest.getDataMap().putInt(SensorFieldsKeys.ACCURACY, accuracy);
                putDataMapRequest.getDataMap().putLong(SensorFieldsKeys.TIMESTAMP, timestamp);
                putDataMapRequest.getDataMap().putFloatArray(SensorFieldsKeys.VALUES, values);
                PutDataRequest pdr = putDataMapRequest.asPutDataRequest();
                send(pdr);
            }
        });


    }
    private boolean validateConnection() {
        if (mGoogleApiClient.isConnected()) {
            return true;
        }

        ConnectionResult result = mGoogleApiClient.blockingConnect(CLIENT_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);

        return result.isSuccess();
    }

    private void send(PutDataRequest putDataRequest){
        if(validateConnection()){
            Wearable.DataApi.putDataItem(mGoogleApiClient, putDataRequest).setResultCallback(new ResultCallback<DataApi.DataItemResult>() {
                @Override
                public void onResult(@NonNull DataApi.DataItemResult dataItemResult) {
                    Log.v(TAG, "data sent  "+dataItemResult.getDataItem()+" " +
                                " ,success "+dataItemResult.getStatus().getStatusMessage());

                }
            });
        }
    }
}
