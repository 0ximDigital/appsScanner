package tscanner.msquared.hr.travelscanner;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;



/**
 * Created by Matej on 5/31/2015.
 */
public class InternetConnectionCheck {

    private Context context;

    public InternetConnectionCheck(Context context) {
        this.context = context;
    }

    public OnCheckCallback callback;

    public interface OnCheckCallback {
        void onCheck(boolean hasConnection);
    }

    public void setCheckCallback(OnCheckCallback checkCallback) {
        callback = checkCallback;
    }

    public void checkConnection() {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] info = cm.getAllNetworkInfo();
        boolean hasConnection = false;

        // if connection detected - set it to true
        for(NetworkInfo n : info){
            if(n.isConnected() && !hasConnection)
                hasConnection = true;

        }

        if(!hasConnection){ //show dialog
            AlertDialog.Builder  builder = new AlertDialog.Builder(context);
            builder.setTitle("Network ERROR")
                    .setMessage("Unable to connect to internet. \nPlease connect to Wifi or cellular network and try again. ")
                    .setPositiveButton("TRY AGAIN", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // retry button
                            checkConnection();
                        }
                    })
                    .setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if(callback != null){
                                callback.onCheck(false);
                            }
                        }
                    })
                    .create();
            builder.show();
        }else{
            if(callback != null){
                callback.onCheck(true);
            }
        }
    }

}
