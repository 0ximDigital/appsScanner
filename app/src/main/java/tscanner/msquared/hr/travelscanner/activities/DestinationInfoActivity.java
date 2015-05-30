package tscanner.msquared.hr.travelscanner.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.squareup.picasso.Picasso;

import tscanner.msquared.hr.travelscanner.R;

public class DestinationInfoActivity extends Activity {
    Button goToPay;

    private ExitActivityTransition exitTransition;

    private ImageView topDestinationImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.activity_destination_info);

        topDestinationImageView = (ImageView) findViewById(R.id.topImgView);

        String imgUrl = getIntent().getStringExtra("imgURL");

        Picasso.with(this).load(imgUrl).into(topDestinationImageView);

        // sexy animacija :D
        exitTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.topImgView)).start(savedInstanceState);

        this.referenceViews();

    }

    private void referenceViews(){
        //this.topDestinationImageView = (ImageView) findViewById(R.id.topImgView);

        goToPay=(Button)findViewById(R.id.button3);
        goToPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DestinationInfoActivity.this, ScanActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        exitTransition.exit(this);
    }
}
