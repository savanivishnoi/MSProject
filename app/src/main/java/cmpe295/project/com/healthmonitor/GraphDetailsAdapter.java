package cmpe295.project.com.healthmonitor;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by savani on 10/15/16.
 */

public class GraphDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    ArrayList<FetchedDataModel> fetchedData;
    private static final String TAG="Details Adapter";
    GraphDetailsAdapter(ArrayList<FetchedDataModel> fdm){
        fetchedData = fdm;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return new DetailsViewHolder(LayoutInflater.from(parent.getContext()).inflate
                                        (R.layout.graph_details_row, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DetailsViewHolder dvh = (DetailsViewHolder) holder;
        Log.d(TAG, "fetched data "+fetchedData.size());
        dvh.sensorValueTxt.setText(Double.toString(fetchedData.get(position).getValue()));
        dvh.timeTxt.setText(Integer.toString(fetchedData.get(position).getDate()));

    }

    @Override
    public int getItemCount() {
        return fetchedData.size();
    }
    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
class DetailsViewHolder extends RecyclerView.ViewHolder{
    protected TextView sensorValueTxt;
    protected TextView timeTxt;
    DetailsViewHolder(View itemView){
        super(itemView);
        sensorValueTxt = (TextView)itemView.findViewById(R.id.value);
        timeTxt = (TextView)itemView.findViewById(R.id.day);
       }
}

