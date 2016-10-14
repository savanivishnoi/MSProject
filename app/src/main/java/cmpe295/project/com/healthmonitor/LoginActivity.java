package cmpe295.project.com.healthmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.Paths;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;
import com.google.android.gms.wearable.DataApi;
import com.google.android.gms.wearable.DataEvent;
import com.google.android.gms.wearable.DataEventBuffer;
import com.google.android.gms.wearable.DataItem;
import com.google.android.gms.wearable.DataMap;
import com.google.android.gms.wearable.DataMapItem;
import com.google.android.gms.wearable.MessageApi;
import com.google.android.gms.wearable.Node;
import com.google.android.gms.wearable.NodeApi;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.android.gms.wearable.Wearable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private static final int CLIENT_CONNECTION_TIMEOUT = 15000;
    Button loginBtn;
    private static final String TAG= "Login Activity";
    private GoogleApiClient googleApiClient;
    private GoogleApiClient mGoogleApiClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "token: "+token);
        FirebaseMessaging.getInstance().subscribeToTopic("health");


        loginBtn = (Button)findViewById(R.id.btn_login);
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        this.googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Wearable.API).build();



       // startService(new Intent(this, WearInterfaceDaemon.class));
        executorService = Executors.newCachedThreadPool();
         getNodes1();
    }

    void login() {
        Intent i = new Intent(this, MainActivity.class);
        //i.putExtra("USERID", userid);
        startActivity(i);
    }

    private boolean validateConnection() {
        if (googleApiClient.isConnected()) {
            return true;
        }

        ConnectionResult result = googleApiClient.blockingConnect(CLIENT_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);

        return result.isSuccess();
       // return true;
    }

    private Executors executors;
    private ExecutorService executorService;
    public void getNodes1() {
        System.out.println("get nodes1");
        executorService.submit(new Runnable() {

            @Override
            public void run() {
                System.out.println("get nodes");
                if (validateConnection()) {
                    System.out.println("connection validated");
                    NodeApi.GetConnectedNodesResult nodes = Wearable.NodeApi.getConnectedNodes(googleApiClient).await();
                    System.out.println( "nodesss sizee"+nodes.toString());
                    for(Node node : nodes.getNodes()) {
                        MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(googleApiClient, node.getId(), "healthcare"+Paths.START_MEASUREMENT, "Hello World".getBytes()).await();
                        if(!result.getStatus().isSuccess()){
                            Log.e("test", "error");
                        } else {
                            Log.i("test", "success!! sent to: " + node.getDisplayName());
                        }
                    }
                }

//                    //List<Node> nodesList= Wearable.NodeApi.getConnectedNodes(googleApiClient).await().getNodes(); //Wearable.NodeApi.getConnectedNodes(googleApiClient).setResultCallback(pCallback);
////                CapabilityApi.GetAllCapabilitiesResult r =
////                        Wearable.CapabilityApi.getAllCapabilities(googleApiClient,CapabilityApi.FILTER_ALL).await();
////                // r.getAllCapabilities().get
//                   CapabilityApi.GetAllCapabilitiesResult  r = Wearable.CapabilityApi.getAllCapabilities(googleApiClient,CapabilityApi.FILTER_ALL).await();
//
////                    CapabilityApi.GetCapabilityResult r =  Wearable.CapabilityApi.getCapability(
////                            googleApiClient, "healthcare",
////                            CapabilityApi.FILTER_REACHABLE).await();
//                    System.out.println( "before sizee"+r.getStatus()+r.toString());
//
//                    CapabilityApi.CapabilityListener capabilityListener =
//                        new CapabilityApi.CapabilityListener() {
//                            @Override
//                            public void onCapabilityChanged(CapabilityInfo capabilityInfo) {
//                                Set<Node> nodes = capabilityInfo.getNodes();
//                                for (Node node : nodes) {
//                                    System.out.println(node + "nodess");
//                                }
//                            }
//                        };
//                    Wearable.CapabilityApi.addCapabilityListener(
//                            googleApiClient,
//                            capabilityListener,
//                            "healthcare");
//                  //  Set<Node> nodes
//                    System.out.println( "aftere sizee"+  r.getAllCapabilities().size()); //.getNodes()
//
////                for (Node node : nodes) {
////                    System.out.println(node + "nodess");
////                    Wearable.MessageApi.sendMessage(googleApiClient, node.getId(),
////                            Paths.START_MEASUREMENT, null).setResultCallback(new ResultCallback<MessageApi.SendMessageResult>() {
////                        @Override
////                        public void onResult(@NonNull MessageApi.SendMessageResult sendMessageResult) {
////                            Log.d(TAG, "result from wearable");
////                        }
////                    });
////                }

//                } else {
//                    googleApiClient.disconnect();
//                    System.out.println("error");
//                }
                }
            });

    }

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
//
//    @Override
//    public void onConnected(@Nullable Bundle bundle) {
//        Log.d(TAG, "on Connected");
//        Wearable.DataApi.addListener(mGoogleApiClient, this);
//    }
//
//    @Override
//    public void onConnectionSuspended(int i) {
//        Log.d(TAG, "connection suspended");
//    }
//
//    @Override
//    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Log.d(TAG,"connection failed");
//    }
}
