package tscanner.msquared.hr.travelscanner.activities;

import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.kogitune.activity_transition.ActivityTransition;
import com.kogitune.activity_transition.ActivityTransitionLauncher;
import com.kogitune.activity_transition.ExitActivityTransition;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.customViews.CustomDestinationInfoView;
import tscanner.msquared.hr.travelscanner.customViews.TravelerView;
import tscanner.msquared.hr.travelscanner.helpers.PrefsHelper;
import tscanner.msquared.hr.travelscanner.models.TravelerDataValues;
import tscanner.msquared.hr.travelscanner.models.restModels.TravelDestination;
import tscanner.msquared.hr.travelscanner.models.restModels.Traveler;

/**
 * Created by Matej on 5/29/2015.
 * Done by Mihael xD
 */
public class AddDestinationTravelersActivity extends Activity {

    private final String TAG = this.getClass().getSimpleName();

    private final int SharedAnimationDurationMillis = 750;
    public static final String DESTINATION_IMAGE_URL_EXTRA = "destination_image_url_extra";
    public static final String DESTINATION_SERIALIZED_DATA = "destination_serialized_data";

    public static final String NEW_TRAVELER_SERIALIZED_DATA = "new_traveler_serialized_data";

    private final String SAVED_TRAVELERS_STATE = "saved_travelers_state";

    private final int TRAVELER_RESULT_REQUEST_CODE = 1978;

    private LinearLayout containerLinearLayout;

    private ImageView destinationBottomImage;
    private TextView firstTravelerText;

    // this needs to be hidden first - GONE
    private FloatingActionButton addTravelerButton;
    private FrameLayout contentFrameLayout;

    PrefsHelper prefsHelper;

    private Gson gson;

    private TravelDestination travelDestination;

    private ActivityTransition entryAnimation;
    private ExitActivityTransition exitActivityTransition;

    private Timer timer;
    private TimerTask timerTask;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        setContentView(R.layout.activity_add_destination_travelers);

        this.prefsHelper = new PrefsHelper(this);
        prefsHelper.putStringSet(PrefsHelper.TRAVELERS_DATA, null);
        this.gson = new Gson();
        this.referenceViews();

        this.hideAllViewsForTransition(null);

        String imgUrl = getIntent().getStringExtra(this.DESTINATION_IMAGE_URL_EXTRA);
        Picasso.with(this).load(imgUrl).into(this.destinationBottomImage);
        this.entryAnimation = ActivityTransition.with(getIntent()).to(this.destinationBottomImage).duration(SharedAnimationDurationMillis);
        this.exitActivityTransition = this.entryAnimation.start(savedInstanceState);
        this.startTimer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "On resume");
    }

    private void referenceViews(){

        this.containerLinearLayout = (LinearLayout) findViewById(R.id.containerLinearLayout);

        this.addTravelerButton = (FloatingActionButton) findViewById(R.id.btnAddDestinationTraveler);
        this.contentFrameLayout = (FrameLayout) findViewById(R.id.contentFrameLayout);

        this.destinationBottomImage = (ImageView) findViewById(R.id.imgDestinationBottom);
        String destinationData = getIntent().getStringExtra(this.DESTINATION_SERIALIZED_DATA);
        if(destinationData != null){
            travelDestination = gson.fromJson(destinationData, TravelDestination.class);
            Picasso.with(this).load(travelDestination.getPicture()).into(this.destinationBottomImage);
        }

        this.firstTravelerText = (TextView) findViewById(R.id.txtAddFirstTraveler);

        this.addTravelerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentForTravelerData = new Intent(AddDestinationTravelersActivity.this, ScanActivity.class);
                startActivityForResult(intentForTravelerData, TRAVELER_RESULT_REQUEST_CODE);
            }
        });
    }

    private interface ViewsHideListener{
        void onCompletion();
    }

    private void hideAllViewsForTransition(final ViewsHideListener viewsHideListener){
        if(viewsHideListener == null){
            this.contentFrameLayout.animate().alpha(0.0f).setDuration(0).start();
        }
        else{
            this.contentFrameLayout.animate().alpha(0.0f).setDuration(SharedAnimationDurationMillis/2).setListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animator) {

                }

                @Override
                public void onAnimationEnd(Animator animator) {
                    if(viewsHideListener != null){
                        viewsHideListener.onCompletion();
                    }
                }

                @Override
                public void onAnimationCancel(Animator animator) {

                }

                @Override
                public void onAnimationRepeat(Animator animator) {

                }
            });
        }
    }

    private void showAllViewsAfterTransition(){
        this.contentFrameLayout.animate().alpha(1.0f).setDuration(SharedAnimationDurationMillis/2).start();
    }

    private void startTimer(){
        this.timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showAllViewsAfterTransition();
                    }
                }, SharedAnimationDurationMillis);
                stopTimer();
            }
        };
        this.timer.schedule(this.timerTask, 0, this.SharedAnimationDurationMillis + 100);
    }

    private void stopTimer(){
        if(this.timerTask != null){
            this.timer.cancel();
            this.timer = null;
        }
    }

    @Override
    public void onBackPressed() {
        this.hideAllViewsForTransition(new ViewsHideListener() {
            @Override
            public void onCompletion() {
                exitActivityTransition.exit(AddDestinationTravelersActivity.this);
            }
        });
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Log.w(TAG, "Restoring instance state");
        Set<String> travelers = prefsHelper.getStringSet(PrefsHelper.TRAVELERS_DATA, null);
        if(travelers != null){
            for(String s : travelers){
                TravelerDataValues values = gson.fromJson(s, TravelerDataValues.class);
                containerLinearLayout.addView(getViewFromData(values));
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == TRAVELER_RESULT_REQUEST_CODE){
            if(resultCode == RESULT_OK){
                String travelerData = data.getStringExtra(NEW_TRAVELER_SERIALIZED_DATA);
                if(travelerData != null){
                    TravelerDataValues dataValues = gson.fromJson(travelerData, TravelerDataValues.class);
                    if(dataValues != null){
                        Set<String> travelers = prefsHelper.getStringSet(PrefsHelper.TRAVELERS_DATA, null);
                        if(travelers == null){
                            travelers = new HashSet<String>();
                        }
                        travelers.add(gson.toJson(dataValues));
                        prefsHelper.putStringSet(PrefsHelper.TRAVELERS_DATA, travelers);
                        containerLinearLayout.addView(getViewFromData(dataValues));
                        firstTravelerText.setVisibility(View.GONE);
                    }
                }
            }
        }
    }

    private TravelerView getViewFromData(TravelerDataValues dataValues){
        TravelerView travelerView = new TravelerView(AddDestinationTravelersActivity.this);
        travelerView.setDeleteCallback(new TravelerView.DeleteViewInterface() {
            @Override
            public void deleteView(TravelerView view) {
                TravelerDataValues values = view.getData();
                String dataString = gson.toJson(values);
                Set<String> travelers = prefsHelper.getStringSet(PrefsHelper.TRAVELERS_DATA, null);
                travelers.remove(dataString);
                containerLinearLayout.removeView(view);
            }
        });
        travelerView.setTravelerTitleName(dataValues.getName());
        travelerView.setTravelerNameAndSurname(dataValues.getName() + " " + dataValues.getSurname());
        travelerView.setTravelerBirthDate(dataValues.getDateOfBirth());
        travelerView.setTravelerIdNumber(dataValues.getIdNumber());
        travelerView.setData(dataValues);
        return travelerView;
    }
}
