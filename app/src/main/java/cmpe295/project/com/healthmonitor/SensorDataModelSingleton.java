package cmpe295.project.com.healthmonitor;

import java.util.ArrayList;

/**
 * Created by savani on 10/13/16.
 */

public class SensorDataModelSingleton {
    ArrayList<SensorDataModel> sensorsData;
    private static SensorDataModelSingleton mInstance;
    private SensorDataModelSingleton(){
        sensorsData = new ArrayList<>();
    }
    public static SensorDataModelSingleton getInstance(){
        if(mInstance == null){
          mInstance =  new SensorDataModelSingleton();
        }

        return mInstance;
    }
    void add(SensorDataModel sdm){
        sensorsData.add(sdm);
    }
    ArrayList<SensorDataModel> getList(){
        return sensorsData;
    }



}
