package tscanner.msquared.hr.travelscanner.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;
import android.view.View;
import android.widget.Button;

import tscanner.msquared.hr.travelscanner.R;

public class MainActivity extends Activity {

    private Button goToDestInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.goToDestInfo =(Button)findViewById(R.id.button2);

        goToDestInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,DestinationInfoActivity.class);
                startActivity(intent);
            }
        });

    }

}
