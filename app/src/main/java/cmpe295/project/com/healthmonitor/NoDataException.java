package cmpe295.project.com.healthmonitor;



/**
 * Created by savani on 10/21/16.
 */

public class NoDataException extends Exception {
    NoDataException(){

    }
    NoDataException(String message){
        super(message);
    }
}
