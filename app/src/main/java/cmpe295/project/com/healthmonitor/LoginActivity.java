package cmpe295.project.com.healthmonitor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.wearable.CapabilityApi;
import com.google.android.gms.wearable.CapabilityInfo;
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
        executorService = Executors.newCachedThreadPool();
         getNodes1();


    }

    void login(){


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
                    //List<Node> nodesList= Wearable.NodeApi.getConnectedNodes(googleApiClient).await().getNodes(); //Wearable.NodeApi.getConnectedNodes(googleApiClient).setResultCallback(pCallback);
//                CapabilityApi.GetAllCapabilitiesResult r =
//                        Wearable.CapabilityApi.getAllCapabilities(googleApiClient,CapabilityApi.FILTER_ALL).await();
//                // r.getAllCapabilities().get

                    CapabilityApi.GetCapabilityResult r =  Wearable.CapabilityApi.getCapability(
                            googleApiClient, "healthcare",
                            CapabilityApi.FILTER_REACHABLE).await();

                Map<String, CapabilityInfo> x = (HashMap<String, CapabilityInfo>) r.getCapability();
                System.out.println(x.size() +"sizee");
                for(int i = 0 ; i< x.size(); i++){
                    System.out.println(x.get(i)+ "bluetoothii dev");
                }
//                CapabilityApi.GetCapabilityResult result =
//                        Wearable.CapabilityApi.getCapability(
//                                googleApiClient, "voice_transcription",
//                                CapabilityApi.FILTER_REACHABLE).await();
//
//                Set<Node> nodes = result.getCapability().getNodes();
//                for (Node node : nodes) {
//                    System.out.println(node + "nodess");
//                }

                } else {
                    googleApiClient.disconnect();
                    System.out.println("error");
                }
                }
            });

    }


}
