package cmpe295.project.com.healthmonitor;

import java.util.ArrayList;

/**
 * Created by savani on 10/15/16.
 */

public class FetchedDataSingleton {
    private ArrayList<FetchedDataModel> fetchedDataList;
    public static FetchedDataSingleton mInstance;
    private FetchedDataSingleton(){
        fetchedDataList = new ArrayList<>();
    }
    public static FetchedDataSingleton getInstance(){
        if(mInstance == null){
            mInstance =  new FetchedDataSingleton();
        }

        return mInstance;
    }
    void add(FetchedDataModel fdm){
        fetchedDataList.add(fdm);
    }
    ArrayList<FetchedDataModel> getList(){
        return fetchedDataList;
    }
    void addList(ArrayList<FetchedDataModel> list){
        fetchedDataList = list;
    }

}
