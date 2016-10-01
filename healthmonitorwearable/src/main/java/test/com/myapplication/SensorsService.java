package test.com.myapplication;

import android.app.Service;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;

/**
 * Created by savani on 9/10/16.
 */
public class SensorsService extends Service implements SensorEventListener {
    SensorManager mSensorManager;
    public SensorsService() {
        super();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        System.out.println("Savani !!");
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor: sensorList){
            System.out.println("Sensor type ... "+sensor.getName() + "Sensor id ... "+sensor.getType());
        }
        Sensor heartRateSensor =  mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
        // System.out.println(heartRateSensor.getName()+" hert");
        //mSensorManager.registerListener(this, heartRateSensor, 2000 );//SensorManager.SENSOR_DELAY_NORMAL);

        Sensor wellnessSensor = mSensorManager.getDefaultSensor(65538);
        mSensorManager.registerListener(this, wellnessSensor, SensorManager.SENSOR_DELAY_NORMAL);

        Sensor stepCounter = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        mSensorManager.registerListener(this, stepCounter, SensorManager.SENSOR_DELAY_NORMAL);

        Sensor stepType = mSensorManager.getDefaultSensor(Sensor.TYPE_STEP_DETECTOR);
        if(stepType != null){
            mSensorManager.registerListener(this, stepType, SensorManager.SENSOR_DELAY_NORMAL);
        }

        Sensor stepDet = mSensorManager.getDefaultSensor(65537);
        mSensorManager.registerListener(this, stepDet, SensorManager.SENSOR_DELAY_NORMAL);


    }
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        //     System.out.println("Length vues"+ sensorEvent.values.length );
//        for(int i = 0 ; i < sensorEvent.values.length; i++){
//            System.out.println("vue .. "+i+"  "+sensorEvent.values[i]);
//        }
        SensorReporting.getInstance(this).sendReadings(sensorEvent.sensor.getType(),
                                    sensorEvent.accuracy, sensorEvent.timestamp, sensorEvent.values);

        if (sensorEvent.sensor.getType() == Sensor.TYPE_HEART_RATE) {
            String msg = "" + (int) sensorEvent.values[0];
            Log.d("Main Activity", msg+" "+sensorEvent.sensor.getName());
        }
        if(sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            Log.d("Mn Activity", "  "+sensorEvent.values[0]+" "+sensorEvent.sensor.getName());
        }
//        if(sensorEvent.sensor.getType() == Sensor.TYPE_HEART_BEAT){
//            Log.d("Mn Activity", "  "+sensorEvent.values[0]+" "+sensorEvent.sensor.getName());
//        }
        if(sensorEvent.sensor.getType() == Sensor.TYPE_STEP_DETECTOR){
            Log.d("Mn Activity", "  "+sensorEvent.values[0]+" "+sensorEvent.sensor.getName());
        }
        if(sensorEvent.sensor.getType() == 65538){
            Log.d("Mn Activity", "  "+sensorEvent.values[0]+" "+sensorEvent.sensor.getName());
        }

        if(sensorEvent.sensor.getType() == 65537){
            Log.d("Mn Activity", "  "+sensorEvent.values[0]+" "+sensorEvent.sensor.getName());
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
