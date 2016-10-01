package cmpe295.project.com.healthmonitor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

/**
 * Created by savani on 9/26/16.
 */

public class GraphFragment extends Fragment {
    private GraphView graph;
    final String TAG = "Graph Fragment";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    void viewGraph(String s){
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
        if(s.equals("Heart Rate")){
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
        }


        graph.getViewport().setScrollable(true);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.BOTTOM);

    }
}
