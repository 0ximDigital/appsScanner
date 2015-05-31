package tscanner.msquared.hr.travelscanner.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.squareup.picasso.Picasso;

import java.util.Timer;
import java.util.TimerTask;

import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.customViews.CustomDestinationInfoView;
import tscanner.msquared.hr.travelscanner.models.restModels.TravelDestination;

public class DestinationInfoActivity extends Activity{

    private final String TAG = this.getClass().getSimpleName();

    private final int SharedAnimationDurationMillis = 750;
    public static final String DESTINATION_IMAGE_URL_EXTRA = "destination_image_url_extra";
    public static final String DESTINATION_SERIALIZED_DATA = "destination_serialized_data";

    private LinearLayout rootLinearLayout;

    private ExitActivityTransition exitTransition;
    private ActivityTransition entryTransition;

    private ImageView topDestinationImageView;

    private TravelDestination travelDestination;
    private String destinationData;

    private Timer timer;
    private TimerTask timerTask;
    private final Handler handler = new Handler();

    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

        setContentView(R.layout.activity_destination_info);

        gson = new Gson();
        this.topDestinationImageView = (ImageView) findViewById(R.id.topImgView);
        String imgUrl = getIntent().getStringExtra(this.DESTINATION_IMAGE_URL_EXTRA);
        destinationData = getIntent().getStringExtra(this.DESTINATION_SERIALIZED_DATA);
        Picasso.with(this).load(imgUrl).into(topDestinationImageView);

        // sexy animacija :D
        entryTransition = ActivityTransition.with(getIntent()).to(findViewById(R.id.topImgView)).duration(this.SharedAnimationDurationMillis);
        this.startTimer();
        exitTransition = entryTransition.start(savedInstanceState);

        this.referenceViews();
    }

    private void referenceViews(){
        this.rootLinearLayout = (LinearLayout) findViewById(R.id.rootLinearLayout);
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
                        if(travelDestination == null){
                            travelDestination = gson.fromJson(destinationData, TravelDestination.class);
                        }
                        if(travelDestination != null) {
                            infoView.setDestinationName(travelDestination.getName());
                            infoView.setDestinationLongDescription(travelDestination.getDescriptionLong());
                            infoView.setDestinationPrice(travelDestination.getPrice());
                            infoView.setDestinationDepartureDate(travelDestination.getDepartureDate());
                            infoView.setDestinationDuration(travelDestination.getDuration());
                            infoView.setDestinationTransportType(travelDestination.getTravelBy());
                            infoView.setDestinationTravelPoints(travelDestination.getTravelPoints());
                            infoView.setFABOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(DestinationInfoActivity.this, AddDestinationTravelersActivity.class);
                                    intent.putExtra(AddDestinationTravelersActivity.DESTINATION_IMAGE_URL_EXTRA, travelDestination.getPicture());
                                    intent.putExtra(AddDestinationTravelersActivity.DESTINATION_SERIALIZED_DATA, gson.toJson(travelDestination));
                                    ActivityTransitionLauncher.with(DestinationInfoActivity.this).from(topDestinationImageView).launch(intent);
                                }
                            });
                        }
                        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                        params.addRule(RelativeLayout.ALIGN_PARENT_LEFT, RelativeLayout.TRUE);
                        params.addRule(RelativeLayout.ALIGN_PARENT_TOP, RelativeLayout.TRUE);
                        rootLinearLayout.addView(infoView, params);
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
