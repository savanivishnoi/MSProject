package cmpe295.project.com.healthmonitor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

/**
 * Created by savani on 9/26/16.
 */

public class GraphFragment extends Fragment implements AdapterView.OnItemSelectedListener,
                                                       View.OnClickListener,
                                                       AdapterView.OnItemClickListener {
    private GraphView graph;
    final String TAG = "Graph Fragment";
    private Spinner spinDays;
    private Spinner spinResource;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.graph_activity, container, false);
        spinDays = (Spinner)v.findViewById(R.id.spinnerDays);
        spinDays.setOnItemSelectedListener(this);
        //spinDays.
        // spinDays.setOnItemClickListener(this);
        spinResource = (AppCompatSpinner) v.findViewById(R.id.spinResource);
        spinResource.setOnItemSelectedListener(this);
        graph = (GraphView) v.findViewById(R.id.graph);
        LocalDatabase ld = new LocalDatabase(getContext());
        ld.retrieveData(21);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    void viewGraph(String s){
        ArrayList<SensorDataModel> sensorsData =  SensorDataModelSingleton.getInstance().getList();
        DataPoint[] dp_arr = new DataPoint[sensorsData.size()];
        int i = 0;
        Log.d(TAG, "Size readings "+sensorsData.size());
        for(SensorDataModel sdm : sensorsData){
        //   DataPoint dp =
            dp_arr[i] = new DataPoint(sdm.getTimestamp() , sdm.getValues());
            i++;
        }
//   new DataPoint[sensorsData.size()];
//        for(int i = 0 ; i < dp.length; i++){
//            dp[i].
//        }
        if(s.equals("Past 30 days")){
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(dp_arr);
            graph.getViewport().setMinY(50);
            graph.getViewport().setMaxY(120);

//                    (new DataPoint[]{
//                    new DataPoint(0, 1.1),
//                    new DataPoint(1, 5),
//                    new DataPoint(2, 3),
//                    new DataPoint(3, 2),
//                    new DataPoint(4, 6),
//                    new DataPoint(5, 2),
//                    new DataPoint(6, 6),
//                    new DataPoint(7, 1),
//                    new DataPoint(8, 3)
//            });
            graph.addSeries(series);
          //  graph
            Log.d(TAG, "Graph");
            series.setTitle("foo");
        }
        if(s.equals("Heart Rate")){
            LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(dp_arr);
            graph.getViewport().setMinY(50);
            graph.getViewport().setMaxY(120);
            graph.getViewport().setYAxisBoundsManual(true);


            //new LineGraphSeries<DataPoint>(new DataPoint[]{
                //    dp_arr

//                    new DataPoint(0, 3),
//                    new DataPoint(1, 3),
//                    new DataPoint(2, 6),
//                    new DataPoint(3, 2),
//                    new DataPoint(4, 5)
       //     });
            graph.addSeries(series2);

            // legend

            series2.setTitle("bar");
        }
        else if(s.equals(R.string.current)){

        }


        graph.getViewport().setScrollable(true);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);

    }
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();
        switch (parent.getId()){

            case R.id.spinnerDays:
                // if(position == 1){
                Log.d(TAG, position+"selected position"+id);
                viewGraph(parent.getItemAtPosition(position).toString());
                //}
                break;
            case R.id.spinResource:
                Log.d(TAG, position+"selected position"+id);
                viewGraph(parent.getItemAtPosition(position).toString());
        }


    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }
}
