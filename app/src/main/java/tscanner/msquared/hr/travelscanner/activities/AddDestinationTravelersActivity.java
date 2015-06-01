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
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.customViews.CustomDestinationInfoView;
import tscanner.msquared.hr.travelscanner.customViews.PurchaseDialog;
import tscanner.msquared.hr.travelscanner.customViews.TravelerView;
import tscanner.msquared.hr.travelscanner.helpers.PrefsHelper;
import tscanner.msquared.hr.travelscanner.helpers.Rest.ServerManager;
import tscanner.msquared.hr.travelscanner.models.TravelerDataValues;
import tscanner.msquared.hr.travelscanner.models.restModels.AppUser;
import tscanner.msquared.hr.travelscanner.models.restModels.Purchase;
import tscanner.msquared.hr.travelscanner.models.restModels.ResponseMessage;
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

    private List<Traveler> travelersList;
    private Traveler[] travelerArray;

    // this needs to be hidden first - GONE
    private FloatingActionButton addTravelerButton;
    private FloatingActionButton purchaseButton;
    private FrameLayout contentFrameLayout;

    private PurchaseDialog purchaseDialog;

    private AppUser appUser;
    private TravelDestination travelDestination;
    private ServerManager serverManager;

    PrefsHelper prefsHelper;

    private Gson gson;

    private ActivityTransition entryAnimation;
    private ExitActivityTransition exitActivityTransition;

    private Timer timer;
    private TimerTask timerTask;
    private final Handler handler = new Handler();

    private Purchase purchase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e(TAG, "onCreate");
        setContentView(R.layout.activity_add_destination_travelers);

        this.prefsHelper = new PrefsHelper(this);
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
        if(containerLinearLayout.getChildCount() > 0){
            firstTravelerText.setVisibility(View.GONE);
        }
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

        String currentUserData = prefsHelper.getString(PrefsHelper.LOGGED_IN_USER_APPUSER_DATA, null);
        if(currentUserData != null){
            appUser = gson.fromJson(currentUserData, AppUser.class);
        }
        else{
            appUser = null;
        }

        this.firstTravelerText = (TextView) findViewById(R.id.txtAddFirstTraveler);

        this.addTravelerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentForTravelerData = new Intent(AddDestinationTravelersActivity.this, ScanActivity.class);
                startActivityForResult(intentForTravelerData, TRAVELER_RESULT_REQUEST_CODE);
            }
        });

        this.purchaseDialog = (PurchaseDialog) findViewById(R.id.purchaseDialog);
        this.purchaseDialog.setOnPurchaseListener(new PurchaseDialog.OnPurchaseListener() {
            @Override
            public void onPurchase() {
                updateDatabase();
            }
        });
        this.purchaseDialog.setVisibility(View.GONE);

        this.purchaseButton = (FloatingActionButton) findViewById(R.id.btnFinishAndPay);
        this.purchaseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                purchaseDialog.showStatus(containerLinearLayout.getChildCount() > 0);
                purchaseDialog.setVisibility(View.VISIBLE);
            }
        });
    }

    private interface ViewsHideListener{
        void onCompletion();
    }

    private void updateDatabase(){
        Log.i(TAG, "Updating database");
        if(appUser != null) {
            Log.i(TAG, "Updating database - app user is not null");
            String purchaseSignatureCheck = this.generatePurchaseSignature();
            purchase = new Purchase(travelDestination.getId(), null, null, appUser.getId(), purchaseSignatureCheck);
            travelersList = new ArrayList<Traveler>();
            appUser.setTravelPoints((appUser.getTravelPoints() != null) ?
                    (appUser.getTravelPoints() + (travelDestination.getTravelPoints() * containerLinearLayout.getChildCount())) :
                    (travelDestination.getTravelPoints() * containerLinearLayout.getChildCount()));
            this.updateUser(appUser);
        }
        else{
            this.finishActivity();
        }
    }

    private void updateUser(AppUser user){
        if(serverManager == null){
            serverManager = new ServerManager();
        }
        serverManager.updateAppUserWithId(user.getId(), user, new ServerManager.Callback<ResponseMessage>() {
            @Override
            public void requestResult(ResponseMessage responseMessage) {
                if (responseMessage.getError() == null) {
                    addNewPurchase(purchase);
                } else {
                    Log.e(TAG, responseMessage.getError());
                    finishActivity();
                }
            }
        });
    }

    private void addNewPurchase(final Purchase purchase){
        if(serverManager == null){
            serverManager = new ServerManager();
        }
        purchase.setPurchaseDate(null);
        Log.i(TAG, "Updating new purchase");
        serverManager.addNewPurchase(purchase, new ServerManager.Callback<ResponseMessage>() {
            @Override
            public void requestResult(ResponseMessage responseMessage) {
                if (responseMessage.getError() == null) {
                    fetchPurchaseWithSignature(purchase.getPurchaseSignature());
                } else {
                    Log.w(TAG, "Error POST-ing purchase -> " + responseMessage.getError());
                    finishActivity();
                }
            }
        });
    }

    private void fetchPurchaseWithSignature(String signature){
        if(serverManager == null){
            serverManager = new ServerManager();
        }
        serverManager.getPurchaseWithSignature(signature, new ServerManager.Callback<Purchase>() {
            @Override
            public void requestResult(Purchase purchase) {
                if (purchase != null) {
                    Log.i(TAG, "fetched purchase with signature " + purchase.getPurchaseSignature());
                    int travelerCount = containerLinearLayout.getChildCount();
                    TravelerView travelerView;
                    TravelerDataValues travelerDataValues;
                    for (int i = 0; i < travelerCount; i++) {
                        travelerView = (TravelerView) containerLinearLayout.getChildAt(i);
                        travelerDataValues = travelerView.getData();
                        Traveler traveler = new Traveler(travelerDataValues.getAge(), "M", null, travelerDataValues.getName(), purchase.getId(), travelerDataValues.getSurname());
                        travelersList.add(traveler);
                    }
                    postAllTravelers();
                } else {
                    finishActivity();
                }
            }
        });
    }

    private void postAllTravelers(){
        this.travelerArray = travelersList.toArray(new Traveler[travelersList.size()]);
        postTravelerAtIndex(-1);
    }

    private void postTravelerAtIndex(int i){
        i++;
        Log.i(TAG, "Posting traveler " + i);
        if(this.travelerArray.length > i){
            addNewTraveler(this.travelerArray[i], i);
        }
        else{
            this.finishActivity();
        }
    }

    private void addNewTraveler(Traveler traveler,final int depth){
        if(serverManager == null){
            serverManager = new ServerManager();
        }
        serverManager.addNewTraveler(traveler, new ServerManager.Callback<ResponseMessage>() {
            @Override
            public void requestResult(ResponseMessage responseMessage) {
                if (responseMessage.getError() == null) {
                    postTravelerAtIndex(depth);
                } else {
                    Log.e(TAG, responseMessage.getError());
                }
            }
        });
    }

    private String generatePurchaseSignature(){
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 24; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }

    private void finishActivity(){
        Intent resetintent = new Intent(this, MainActivity.class);
        resetintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        this.startActivity(resetintent);
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
        this.timer.schedule(this.timerTask, 0, this.SharedAnimationDurationMillis + 200);
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
                prefsHelper.putStringSet(PrefsHelper.TRAVELERS_DATA, null);
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
        if(containerLinearLayout.getChildCount() > 0){
            firstTravelerText.setVisibility(View.GONE);
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
