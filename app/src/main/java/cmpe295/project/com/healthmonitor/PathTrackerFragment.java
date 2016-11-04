package cmpe295.project.com.healthmonitor;

import android.graphics.Camera;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.renderscript.Double2;
import android.support.annotation.IntegerRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.SensorsTypeInfo;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

/**
 * Created by savani on 10/16/16.
 */

public class PathTrackerFragment extends Fragment implements OnMapReadyCallback {
    LocalDatabase mLocalDatabase;
    private final Handler mHandler = new Handler();
    private Runnable mTimer1;
    LatLng lastLocation;
    private int initialSteps = 0;
    private int steps = 0;
    private TextView stepsView;
    private final String TAG = "Path Tracker Fragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_path_tracker, container, false);
        mLocalDatabase = new LocalDatabase(getContext());
        try {
            SensorDataModel sdm = mLocalDatabase.getMRUData(SensorsTypeInfo.TYPE_LOCATION);
             String[] arr = sdm.getValues().split(",");

            lastLocation = new LatLng(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
            Log.d(TAG, "Last location"+arr[0]+arr[1]);
            SupportMapFragment mapFragment =
                    (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            stepsView =(TextView) v.findViewById(R.id.steps);
            double tempSteps = Double.parseDouble((mLocalDatabase.getMRUData
                    (SensorsTypeInfo.TYPE_STEP_COUNTER).getValues()));
            initialSteps = (int)(tempSteps);
          //  mLocalDatabase.retrieveAllData(SensorsTypeInfo.TYPE_STEP_COUNTER);
            stepsView.setText(Integer.toString(steps));
        }
         catch (NoDataException ne) {
            Log.e(TAG, "Exception" + ne);
        }
        return v;
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(15));
        mTimer1 = new Runnable() {
            @Override
            public void run() {
                SensorDataModel sdm = null;
                try {
                    sdm = mLocalDatabase.getMRUData(SensorsTypeInfo.TYPE_LOCATION);

                    String[] arr = sdm.getValues().split(",");
                    LatLng mostRecentLocation = new LatLng(Double.parseDouble(arr[0]), Double.parseDouble(arr[1]));
//                googleMap.addMarker(new MarkerOptions()
//                        .position(mostRecentLocation)
//                        .title("Current"));
                    googleMap.addPolyline((new PolylineOptions()).
                            add(lastLocation, mostRecentLocation).
                            width(5).
                            color(Color.RED));
                    lastLocation = mostRecentLocation;
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(mostRecentLocation));
                    double tempSteps = Double.parseDouble((mLocalDatabase.getMRUData
                            (SensorsTypeInfo.TYPE_STEP_COUNTER).getValues()));
                    steps = (int)tempSteps - initialSteps;
                    stepsView.setText(Integer.toString(steps));
                    // Repeat after 1 sec
                    mHandler.postDelayed(this, 1000);

                } catch (NoDataException e) {
                    e.printStackTrace();
                }
            }
        };
        // Schedule for the first time
        mHandler.postDelayed(mTimer1, 1000);
    }
}
