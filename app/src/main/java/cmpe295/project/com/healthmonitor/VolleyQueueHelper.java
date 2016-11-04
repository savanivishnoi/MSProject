package cmpe295.project.com.healthmonitor;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

/**
 * Created by savani on 10/15/16.
 */

public class VolleyQueueHelper {
    private static VolleyQueueHelper mInstance;
    private Context mContext;
    private RequestQueue queue;

    private VolleyQueueHelper(Context context) {
        mContext = context;
        queue = Volley.newRequestQueue(context);
    }
    public static VolleyQueueHelper getInstance(Context context){
        if(mInstance == null){
           mInstance = new VolleyQueueHelper(context);
        }
        return mInstance;
    }

    public RequestQueue getQueue(){
        return queue;
    }

    public void put(){

    }
    protected void get(){

    }

    protected  void post(){


    }


}
