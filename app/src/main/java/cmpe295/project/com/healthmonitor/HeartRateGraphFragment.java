package cmpe295.project.com.healthmonitor;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;


import com.example.SensorsTypeInfo;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;
import java.util.Random;

import static cmpe295.project.com.healthmonitor.R.color.orange;

/**
 * Created by savani on 9/26/16.
 */

public class HeartRateGraphFragment extends Fragment implements AdapterView.OnItemSelectedListener,
                                                       View.OnClickListener,
                                                       AdapterView.OnItemClickListener {
    private GraphView graph;
    final String TAG = "Graph Fragment";
    private Spinner spinDays;
    private int currCounter = 0;
    LocalDatabase ld;
    private final Handler mHandler = new Handler();
    private Runnable mTimer1;
    Random mRand = new Random();
   // private View detailsContainer;
    private RecyclerView graphDetailsRecyclerView;
    private GraphDetailsAdapter mAdapter;
    private ArrayList<FetchedDataModel> sensorArr;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.heartrate_graph_fragment, container, false);
        spinDays = (Spinner)v.findViewById(R.id.spinnerDays);
        spinDays.setOnItemSelectedListener(this);
        graph = (GraphView)v.findViewById(R.id.graph);
        ld = new LocalDatabase(getContext());
        graphDetailsRecyclerView = (RecyclerView) v.findViewById(R.id.graphDetails);
        sensorArr = FetchedDataSingleton.getInstance().getList();
        mAdapter = new GraphDetailsAdapter(sensorArr);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        graphDetailsRecyclerView.setAdapter(mAdapter);
        graphDetailsRecyclerView.setLayoutManager(layoutManager);
    //    ld.retrieveData(21);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    void viewGraph(String s){
//        ArrayList<SensorDataModel> sensorsData =  SensorDataModelSingleton.getInstance().getList();
//        DataPoint[] dp_arr = new DataPoint[sensorsData.size()];
//        int i = 0;
//        Log.d(TAG, "Size readings "+sensorsData.size());
//        for(SensorDataModel sdm : sensorsData){
//        //   DataPoint dp =
//            dp_arr[i] = new DataPoint(sdm.getTimestamp() , sdm.getValues());
//            i++;
//        }
        if(s.equals(getString(R.string.past30))){
            DataPoint[] sensorVals = generateData(30);
            sensorArr.clear();
            for(int i = 0; i < 30; i++) {
                sensorArr.add(new FetchedDataModel(sensorVals[i].getY(), (int)sensorVals[i].getY()));
            }
            mAdapter.notifyDataSetChanged();
            graph.removeAllSeries();
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(30);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(sensorVals);
            series.setDrawDataPoints(true);
            series.setDrawBackground(true);
            series.setBackgroundColor(Color.parseColor("#7FFFDB9A"));
            graph.addSeries(series);
        }
        else if(s.equals(getString(R.string.week))){
            DataPoint[] sensorVals = generateData(7);
            sensorArr.clear();
            for(int i = 0; i < 7; i++) {
                sensorArr.add(new FetchedDataModel(sensorVals[i].getY(), (int)sensorVals[i].getY()));
            }
            mAdapter.notifyDataSetChanged();
            graph.removeAllSeries();
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(7);
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(sensorVals);
            series.setDrawDataPoints(true);
            series.setDrawBackground(true);
            series.setBackgroundColor(Color.parseColor("#7FFFDB9A"));
            graph.addSeries(series);
        }
        else if(s.equals(getString(R.string.current))){
            Log.d(TAG, "current readings selected");
            sensorArr.clear();
            mAdapter.notifyDataSetChanged();
           // 174371
            graph.removeAllSeries();
            graph.getViewport().setXAxisBoundsManual(true);
            graph.getViewport().setMinX(0);
            graph.getViewport().setMaxX(20);
            graph.getViewport().setScrollable(true);
//            graph.getViewport().setYAxisBoundsManual(true);
//            graph.getViewport().setMinY(50);
//            graph.getViewport().setMaxY(120);
            graph.getGridLabelRenderer().setLabelVerticalWidth(100);
            final LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
            series.setDrawDataPoints(true);
            series.setDrawBackground(true);
            series.setBackgroundColor(Color.parseColor("#FFDB9A"));
            graph.addSeries(series);
            mTimer1 = new Runnable() {
                @Override
                public void run() {
                    SensorDataModel sdm = null;
                    try {
                        sdm = ld.getMRUData(SensorsTypeInfo.TYPE_HEART_RATE);

                    currCounter++;
                    series.appendData(new DataPoint(currCounter,
                                    Double.parseDouble(sdm.getValues())), true, 120);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "parent "+parent.getId());
        if(mTimer1 != null) {
            mHandler.removeCallbacks(mTimer1);
        }
        Log.d(TAG, position+"selected position"+id);
        viewGraph(parent.getItemAtPosition(position).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onResume()
    {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.heartrate));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    //For testing.. random data
    private DataPoint[] generateData(int count) {
        DataPoint[] values = new DataPoint[count];
        for (int i=0; i<count; i++) {
            double x = i;
            double f = mRand.nextDouble();
            double y = Math.sin(i*f+2) + mRand.nextDouble()*0.3;
            DataPoint v = new DataPoint(x, y);
            values[i] = v;
            Log.d(TAG, "x is random "+x + " y "+ y);
        }
        return values;
    }
}
