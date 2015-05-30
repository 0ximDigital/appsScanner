package tscanner.msquared.hr.travelscanner.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.customViews.CustomDestinationInfoView;

public class DestinationInfoActivity extends Activity{

    private final String TAG = this.getClass().getSimpleName();

    private final int SharedAnimationDurationMillis = 750;
    public static final String DESTINATION_IMAGE_URL_EXTRA = "destination_image_url_extra";

    private LinearLayout rootLinearLayout;

    private ExitActivityTransition exitTransition;
    private ActivityTransition entryTransition;

    private ImageView topDestinationImageView;

    private Timer timer;
    private TimerTask timerTask;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.activity_destination_info);

        this.topDestinationImageView = (ImageView) findViewById(R.id.topImgView);
        String imgUrl = getIntent().getStringExtra(this.DESTINATION_IMAGE_URL_EXTRA);
        Picasso.with(this).load(imgUrl).into(topDestinationImageView);

        // sexy animacija :D
        entryTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.topImgView)).duration(this.SharedAnimationDurationMillis);
        this.startTimer();
        exitTransition = entryTransition.start(savedInstanceState);

        this.referenceViews();
    }

    private void referenceViews(){
        this.rootLinearLayout = (LinearLayout) findViewById(R.id.rootLinearLayout);

        this.topDestinationImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DestinationInfoActivity.this, ScanActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(0, 0);
            }
        });
    }

    private void startTimer(){
        this.timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        CustomDestinationInfoView infoView = new CustomDestinationInfoView(DestinationInfoActivity.this);
                        rootLinearLayout.addView(infoView);
                    }
                }, SharedAnimationDurationMillis);
                stopTimer();
            }
        };
        this.timer.schedule(this.timerTask, 0, this.SharedAnimationDurationMillis);
    }

    private void stopTimer(){
        if(this.timerTask != null){
            this.timer.cancel();
            this.timer = null;
        }
    }

   /* @Override
    public void onBackPressed() {
        exitTransition.exit(this);
    }*/

    @Override
    public void onBackPressed() {
        this.finish();
        overridePendingTransition(0, 0);
    }
}
