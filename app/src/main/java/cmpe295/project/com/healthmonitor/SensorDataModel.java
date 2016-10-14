package cmpe295.project.com.healthmonitor;

/**
 * Created by savani on 10/9/16.
 */

public class SensorDataModel {
    private int sensorType;
    private int accuracy;
    private long timestamp;
    private float values;

    public SensorDataModel(int sensorType, int accuracy, long timestamp, float values) {
        this.sensorType = sensorType;
        this.accuracy = accuracy;
        this.timestamp = timestamp;
        this.values = values;
    }
    SensorDataModel(){

    }

    public int getSensorType() {
        return sensorType;
    }

    public void setSensorType(int sensorType) {
        this.sensorType = sensorType;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public float getValues() {
        return values;
    }

    public void setValues(float values) {
        this.values = values;
    }
    //     int sensorType, final int accuracy, final long timestamp,
//    final float[] values
}
