package cmpe295.project.com.healthmonitor;

/**
 * Created by savani on 10/15/16.
 */

public class FetchedDataModel {
    private double value;
    private int date;
    FetchedDataModel(){

    }
    FetchedDataModel(double value, int date){
        this.date = date;
        this.value = value;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
