package tscanner.msquared.hr.travelscanner.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.kogitune.activity_transition.ActivityTransition;

import tscanner.msquared.hr.travelscanner.R;

public class DestinationInfoActivity extends Activity {
    Button goToPay;

    private ImageView topDestinationImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_destination_info);

        // sexy animacija :D
        ActivityTransition.with(getIntent()).to(findViewById(R.id.topImgView)).duration(1000).start(savedInstanceState);

        this.referenceViews();

    }

    private void referenceViews(){
        //this.topDestinationImageView = (ImageView) findViewById(R.id.topImgView);

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
