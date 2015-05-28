package tscanner.msquared.hr.travelscanner.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import tscanner.msquared.hr.travelscanner.R;

public class DestinationInfoActivity extends ActionBarActivity {
    Button goToPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_info);

        goToPay=(Button)findViewById(R.id.button3);
        goToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(DestinationInfoActivity.this,ScanActivity.class);
                startActivity(intent);
            }
        });

    }

}
