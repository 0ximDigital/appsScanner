package tscanner.msquared.hr.travelscanner.models.restModels;

import com.google.gson.annotations.Expose;

/**
 * Created by Mihael on 29.5.2015..
 */
public class ResponseMessage {

    @Expose
    private String error;

    public ResponseMessage(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return error;
    }
}
