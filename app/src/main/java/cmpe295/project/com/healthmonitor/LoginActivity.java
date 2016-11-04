package cmpe295.project.com.healthmonitor;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.provider.SyncStateContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {
    private static final int CLIENT_CONNECTION_TIMEOUT = 15000;
    private Button loginBtn;
    private TextInputEditText patientIdTxt;
    private TextInputEditText passCodeTxt;
    private static final String TAG = "Login Activity";
    private GoogleApiClient googleApiClient;
    private static final String REQUIRED = "Required";
    private String patientId;
    private String passcode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        String token = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "token: " + token);

        FirebaseMessaging.getInstance().subscribeToTopic("health");
        patientIdTxt = (TextInputEditText) findViewById(R.id.patientid);
        passCodeTxt = (TextInputEditText) findViewById(R.id.passcode);
        loginBtn = (Button) findViewById(R.id.btn_login);
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
        Intent serviceIntent = new Intent(this, DataManagementService.class);
        this.startService(serviceIntent);
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        if (sp.getString(getString(R.string.patient_id), "").length() > 0) {
            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    }

    void login() {
//        Intent i = new Intent(getApplicationContext(), MainActivity.class);
//        startActivity(i);
        if (validate(patientIdTxt) && validate(passCodeTxt)) {
            patientId = patientIdTxt.getText().toString();
            passcode = passCodeTxt.getText().toString();
            authenticate();


        }

    }

    private boolean validateConnection() {
        if (googleApiClient.isConnected()) {
            return true;
        }

        ConnectionResult result = googleApiClient.blockingConnect(CLIENT_CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS);

        return result.isSuccess();
        // return true;
    }

    boolean validate(EditText etext) {
        if (etext.getText().toString().length() == 0) {
            etext.setError(REQUIRED);
            return false;
        }
        return true;
    }

    void authenticate() {
        RequestQueue rq = VolleyQueueHelper.getInstance(getApplicationContext()).getQueue();
        String url = getString(R.string.url) + "authenticate/patient";
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("user_id", patientId);
            jsonBody.put("access_key", passcode);
        } catch (JSONException e) {
            Log.d(TAG, "Exception in JSON" + e);
        }
        final String mRequestBody = jsonBody.toString();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                                      new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("VOLLEY", response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                  //  if(jsonResponse.getString("success").equals("true")) {
                        SharedPreferences sp = PreferenceManager.
                                            getDefaultSharedPreferences(getApplicationContext());
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString(getString(R.string.patient_id), patientId);
                        editor.putString(getString(R.string.patient_name),
                                    jsonResponse.getString("first_name") );
                        editor.commit();
                        showRelativeAlert();
//                        Intent i = new Intent(LoginActivity.this, MainActivity.class);
//                        startActivity(i);
                //    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VOLLEY", error.toString());
                passCodeTxt.setError("Incorrect Passcode/Patient ID");
            }
        }) {
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return mRequestBody == null ? null : mRequestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", mRequestBody, "utf-8");
                    return null;
                }
            }


        };
        rq.add(stringRequest);
//        internetCheck = new ConnectivityCheck(this);
//
//        Snackbar snackBar = Snackbar.make(this.vi, "No Internet connection", Snackbar.LENGTH_INDEFINITE)
//                .setAction("RETRY", new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
//                    }
//                });
//        snackBar.show();
    }

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
                    System.out.println("nodes sizee" + nodes.toString());
                    for (Node node : nodes.getNodes()) {
                        MessageApi.SendMessageResult result = Wearable.MessageApi.sendMessage(googleApiClient, node.getId(), "healthcare" + Paths.START_MEASUREMENT, "Hello World".getBytes()).await();
                        if (!result.getStatus().isSuccess()) {
                            Log.e("test", "error");
                        } else {
                            Log.i("test", "success!! sent to: " + node.getDisplayName());
                        }
                    }
                }
            }
        });
    }

    void showRelativeAlert() {
        DialogFragment dialog = new RelativesDialog();
        dialog.show(getSupportFragmentManager(), "RelativesDialog");
    }
}

