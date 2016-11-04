package cmpe295.project.com.healthmonitor;

/**
 * Created by savani on 10/9/16.
 */

public class SensorDataModel {
    private int sensorType;
     private long timestamp;
    private String values;

    public SensorDataModel(int sensorType, long timestamp, String values) {
        this.sensorType = sensorType;

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


    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }
    //     int sensorType, final int accuracy, final long timestamp,
//    final float[] values
}
