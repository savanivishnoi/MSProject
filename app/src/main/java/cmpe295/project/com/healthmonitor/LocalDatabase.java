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

import java.util.ArrayList;

/**
 * Created by savani on 10/9/16.
 */

public class LocalDatabase extends SQLiteOpenHelper{
    private static final String db_name = "HealthMonitorData";
    private static final int db_version = 4;
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
                        "Val" + " REAL, "+
                        SensorFieldsKeys.ACCURACY+ " INTEGER )";  //values r multiple but we enter only first one
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
                sdm.setAccuracy(cursor.getInt(3));
                sdm.setValues(cursor.getFloat(2));
                sdm.setTimestamp(cursor.getLong(0));
                sdm.setSensorType(cursor.getInt(1));
                sensors.add(sdm);
            }

        }catch (SQLException e){
            Log.d(TAG, "exception on read"+e);
        }
    }

    public SensorDataModel getMRUData(int sensorType){
        String query = "SELECT * FROM  "+db_name+" where " + SensorFieldsKeys.SENSOR_TYPE +  " = "+sensorType;
        SQLiteDatabase db = this.getWritableDatabase();
        try{
                Cursor cursor = db.rawQuery(query, null);
                cursor.moveToLast();
                SensorDataModel sdm = new SensorDataModel();
                sdm.setAccuracy(cursor.getInt(3));
                sdm.setValues(cursor.getFloat(2));
                sdm.setTimestamp(cursor.getLong(0));
                sdm.setSensorType(cursor.getInt(1));
               // sensors.add(sdm);
                return sdm;


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
        cv.put(SensorFieldsKeys.ACCURACY, sm.getAccuracy());

        return cv;
    }



}
