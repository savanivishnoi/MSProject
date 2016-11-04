package cmpe295.project.com.healthmonitor;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

/**
 * Created by savani on 10/16/16.
 */

public class NotificationsFragment extends Fragment {
    private NotificationsAdapter mAdapter;
    private RecyclerView notificationsRecyclerView;
    private static final String TAG = "Notifications Fragment";
    private ArrayList<NotificationsModel> notificationsList = new ArrayList<>();
    private String patient_id;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences
                (getActivity().getApplicationContext());
        patient_id = sp.getString(getString(R.string.patient_id), "");
        View v = inflater.inflate(R.layout.fragment_notifications, container, false);
        notificationsRecyclerView = (RecyclerView) v.findViewById(R.id.notificationsView);
      //  sensorArr = FetchedDataSingleton.getInstance().getList();
        mAdapter = new NotificationsAdapter(notificationsList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        notificationsRecyclerView.setAdapter(mAdapter);
        notificationsRecyclerView.setLayoutManager(layoutManager);
        fetchNotifications();
        fetchAlerts();
        mAdapter.notifyDataSetChanged();

        return v;
    }
    void fetchNotifications(){
        String url = getString(R.string.url)+"patient/"+patient_id+"/notifications";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for(int i =0; i < jsonArray.length(); i++) {
                        NotificationsModel nm = new NotificationsModel();
                        nm.setNotificationType(1);
                        nm.setDate(jsonArray.getJSONObject(i).getString("created_on"));
                        nm.setDoctorId(jsonArray.getJSONObject(i).getString("doctor_id"));
                        nm.setReadStatus(jsonArray.getJSONObject(i).getString("read_status"));
                        nm.setNotificationId(jsonArray.getJSONObject(i).getString("notification_id"));
                        nm.setMessage(jsonArray.getJSONObject(i).getString("message"));
                        nm.setSubject(jsonArray.getJSONObject(i).getString("subject"));
                        Log.d(TAG, response.toString());
                        notificationsList.add(nm);
                    }
                    mAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }

        );
        VolleyQueueHelper.getInstance(getActivity().getApplicationContext()).
                getQueue().add(stringRequest);
    }

    void fetchAlerts(){
        String url = getString(R.string.url)+"patient/"+patient_id+"/alerts";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {

                    JSONArray jsonArray = new JSONArray(response);
                    for(int i =0; i < jsonArray.length(); i++) {
                        NotificationsModel nm = new NotificationsModel();
                        nm.setNotificationType(2);
                        nm.setDate(jsonArray.getJSONObject(i).getString("created_on"));
                        nm.setPriority(jsonArray.getJSONObject(i).getString("priority"));
                        nm.setMessage(jsonArray.getJSONObject(i).getString("message"));
                        Log.d(TAG, response.toString());
                        notificationsList.add(nm);
                    }
                    mAdapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }

        );
        VolleyQueueHelper.getInstance(getActivity().getApplicationContext()).
                getQueue().add(stringRequest);
    }




}
