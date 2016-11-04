package cmpe295.project.com.healthmonitor;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.util.Log;

import com.example.SensorFieldsKeys;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by savani on 10/9/16.
 */

public class LocalDatabase extends SQLiteOpenHelper{
    private static final String db_name = "HealthMonitorData";
    private static final int db_version = 5;
    private static final String TAG= "Local Database";
    LocalDatabase(Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + db_name + "(" +   // id is long timestamp
                        "_id"+" INTEGER PRIMARY KEY, "+
                        SensorFieldsKeys.SENSOR_TYPE+" INTEGER, "+
                        "Val" + " TEXT )";  //values r multiple but we enter only first one
        Log.d(TAG, "sql creation query"+SQL_CREATE_ENTRIES);
        try {
            db.execSQL(SQL_CREATE_ENTRIES);
        }catch(SQLException sqlException){
            Log.d(TAG, "Exception "+sqlException);
        }
        Log.d(TAG, "DB created");
    }
// Insert
    public void addEntry(SensorDataModel sm){

        SQLiteDatabase db1 = this.getWritableDatabase();
        db1.insert(db_name, null, prepareData(sm));
     //   Log.d(TAG, "DB inserted");
        db1.close();
    }

   //retrieve data
    public void retrieveData(int sensorType){
        String query = "SELECT * FROM  "+db_name+" where " + SensorFieldsKeys.SENSOR_TYPE +  " = "+sensorType;
        SQLiteDatabase db = this.getWritableDatabase();
        SensorDataModelSingleton sensors =  SensorDataModelSingleton.getInstance();
        try{
            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()){
                SensorDataModel sdm = new SensorDataModel();
                sdm.setValues(cursor.getString(2));
                sdm.setTimestamp(cursor.getLong(0));
                sdm.setSensorType(cursor.getInt(1));
                sensors.add(sdm);
            }

        }catch (SQLException e){
            Log.e(TAG, "exception on read"+e);
        }
    }

    //retrieve data
    public ArrayList<SensorDataModel> retrieveAllData(int sensorType){
        ArrayList<SensorDataModel> sensorsData = new ArrayList<>();
        String query = "SELECT * FROM  "+db_name+" where " + SensorFieldsKeys.SENSOR_TYPE +  " = "+sensorType;

        SQLiteDatabase db = this.getWritableDatabase();
        try{
            Cursor cursor = db.rawQuery(query, null);
            while (cursor.moveToNext()){
                JSONObject reading = new JSONObject();
                reading.put("timestamp",cursor.getLong(0));
                reading.put("sensorType", cursor.getInt(1) );
                reading.put("value",cursor.getString(2));
                Log.d(TAG, cursor.getInt(1) + " "+cursor.getString(2));
//                SensorDataModel sdm = new SensorDataModel();
//                sdm.setValues(cursor.getString(2));
//                sdm.setTimestamp(cursor.getLong(0));
//                sdm.setSensorType(cursor.getInt(1));
//                sensorsData.add(sdm);
            }

        }catch (SQLException e){
            Log.d(TAG, "exception on read"+e);
        }catch (JSONException e){
            Log.d(TAG, "exception in JSON"+e);
        }
        return sensorsData;
    }

    public SensorDataModel getMRUData(int sensorType) throws NoDataException {
        String query = "SELECT * FROM  "+db_name+" where " + SensorFieldsKeys.SENSOR_TYPE +  " = "+sensorType;
        SQLiteDatabase db = this.getWritableDatabase();
        try{
                Cursor cursor = db.rawQuery(query, null);
//test code
//            while (cursor.moveToNext()) {
//                System.out.println("Time:  "+cursor.getLong(0) + " type: "+ cursor.getInt(1) + " value: "+cursor.getString(2) );
//            }

                cursor.moveToLast();
            if(cursor!=null && cursor.getCount()>0) {

                SensorDataModel sdm = new SensorDataModel();
                sdm.setValues(cursor.getString(2));
                sdm.setTimestamp(cursor.getLong(0));
                sdm.setSensorType(cursor.getInt(1));
                Log.d(TAG, "graph"+cursor.getInt(1) + " " + cursor.getString(2));
                // sensors.add(sdm);
                return sdm;
            } else{
                throw new NoDataException("No data for sensor "+sensorType);
            }

        } catch (SQLException e){
            Log.d(TAG, "exception on read"+e);
        }


       return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " +db_name );
        Log.d(TAG, "table dropped");
        onCreate(db);

    }



    ContentValues prepareData(SensorDataModel sm){
        ContentValues cv = new ContentValues();
        cv.put("_id", sm.getTimestamp());
        cv.put(SensorFieldsKeys.SENSOR_TYPE, sm.getSensorType());
        cv.put("Val", sm.getValues());
        return cv;
    }



}
