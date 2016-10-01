package cmpe295.project.com.healthmonitor;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by savani on 8/3/16.
 */
public class TrendsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener,
                                                                    View.OnClickListener,
                                                                     AdapterView.OnItemClickListener
{
    private GraphView graph;
    private  Spinner spinDays;
    private AppCompatSpinner spinResource;
    private static final String TAG = "TrendsActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.graph_activity);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


       //  graph = (GraphView) findViewById(R.id.graph);
         spinDays = (Spinner)findViewById(R.id.spinnerDays);
         spinDays.setOnItemSelectedListener(this);
        //spinDays.
       // spinDays.setOnItemClickListener(this);
        spinResource = (AppCompatSpinner) findViewById(R.id.spinResource);
        spinResource.setOnItemSelectedListener(this);

         graph = (GraphView) findViewById(R.id.graph);


        // viewGraph();
//        FragmentManager fm = getSupportFragmentManager();
//        Fragment f = fm.findFragmentById(R.id.graphContainer); //Graph fragment
//        if(f == null){
//             f = new GraphFragment();
//            FragmentTransaction ft = fm.beginTransaction();
//              ft.replace(R.id.graphContainer, f);
//             ft.commit();
//        }



    }
    void viewGraph(String s){
        Log.d(TAG, "Graph");
        if(s.equals("Past 30 days")){
            LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(0, 1.1),
                    new DataPoint(1, 5),
                    new DataPoint(2, 3),
                    new DataPoint(3, 2),
                    new DataPoint(4, 6),
                    new DataPoint(5, 2),
                    new DataPoint(6, 6),
                    new DataPoint(7, 1),
                    new DataPoint(8, 3)
            });
            graph.addSeries(series);
          //  graph
            Log.d(TAG, "Graph");
            series.setTitle("foo");
        }
       // if(s.equals("Heart Rate")){
            LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(new DataPoint[]{
                    new DataPoint(0, 3),
                    new DataPoint(1, 3),
                    new DataPoint(2, 6),
                    new DataPoint(3, 2),
                    new DataPoint(4, 5)
            });
            graph.addSeries(series2);

            // legend


            series2.setTitle("bar");
      //  }
        if(s.equals("Exercise")) {
            series2.resetData(new DataPoint[]{
                    new DataPoint(5, 9),
                    new DataPoint(7, 6),
                    new DataPoint(8, 9),

            });
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
    public void onClick(View v){
        Log.d(TAG, "on click"+Integer.toString(v.getId()));
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "on item slected"+Integer.toString(view.getId()));
    }
}
