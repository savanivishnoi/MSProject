package cmpe295.project.com.healthmonitor;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.wearable.view.BoxInsetLayout;
import android.util.Log;
import android.widget.TextView;

import java.util.List;

/**
 * Created by savani on 10/1/16.
 */

public class testActivity extends Activity implements SensorEventListener {
    SensorManager mSensorManager;

    private BoxInsetLayout mContainerView;
    private TextView mTextView;
    private TextView mClockView;
    private  final String TAG = "test Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        //setAmbientEnabled();

        mContainerView = (BoxInsetLayout) findViewById(R.id.container);
        mTextView = (TextView) findViewById(R.id.text);
      //  mClockView = (TextView) findViewById(R.id.clock);
        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);

        List<Sensor> sensorList = mSensorManager.getSensorList(Sensor.TYPE_ALL);
        for(Sensor sensor: sensorList){
            System.out.println("Sensor type ... "+sensor.getName() + "Sensor id ... "+sensor.getType());
        }
//        Sensor heartRateSensor =  mSensorManager.getDefaultSensor(Sensor.TYPE_HEART_RATE);
//         System.out.println(heartRateSensor.getName()+" hert");
//        mSensorManager.registerListener(this, heartRateSensor, 2000 );//SensorManager.SENSOR_DELAY_NORMAL);

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
        SensorReporting sr =SensorReporting.getInstance(this);
      //  if(sr!=null) {
            Log.d(TAG, "sensor reporting ");
            SensorReporting.getInstance(this).sendReadings(sensorEvent.sensor.getType(),
                    sensorEvent.accuracy, sensorEvent.timestamp, sensorEvent.values);
       // }

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
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}


