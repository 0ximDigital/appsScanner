package tscanner.msquared.hr.travelscanner.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import tscanner.msquared.hr.travelscanner.InternetConnectionCheck;
import tscanner.msquared.hr.travelscanner.R;

public class TempStartActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp_start);

        InternetConnectionCheck check = new InternetConnectionCheck(this);
        check.setCheckCallback(new InternetConnectionCheck.OnCheckCallback() {
            @Override
            public void onCheck(boolean hasConnection) {
                if(hasConnection){
                    Intent i = new Intent(TempStartActivity.this, MainActivity.class);
                    startActivity(i);
                    finish();
                }
                else{
                    finish();
                }
            }
        });
        check.checkConnection();
    }

}
