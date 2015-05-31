package tscanner.msquared.hr.travelscanner.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;
import com.kogitune.activity_transition.ActivityTransition;
import com.squareup.picasso.Picasso;

import java.util.Random;

import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.customViews.TravelerView;
import tscanner.msquared.hr.travelscanner.models.restModels.TravelDestination;

/**
 * Created by Matej on 5/29/2015.
 * Done by Mihael xD
 */
public class AddDestinationTravelersActivity extends Activity {

    private final String TAG = this.getClass().getSimpleName();

    private final int SharedAnimationDurationMillis = 750;
    public static final String DESTINATION_IMAGE_URL_EXTRA = "destination_image_url_extra";
    public static final String DESTINATION_SERIALIZED_DATA = "destination_serialized_data";

    private LinearLayout containerLinearLayout;

    private ImageView destinationBottomImage;
    private TextView firstTravelerText;

    private FloatingActionButton addTravelerButton;

    private Gson gson;

    private TravelDestination travelDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_destination_travelers);

        this.destinationBottomImage = (ImageView) findViewById(R.id.imgDestinationBottom);
        String imgUrl = getIntent().getStringExtra(this.DESTINATION_IMAGE_URL_EXTRA);
        Picasso.with(this).load(imgUrl).into(this.destinationBottomImage);
        ActivityTransition.with(getIntent()).to(findViewById(R.id.imgDestinationBottom)).duration(SharedAnimationDurationMillis).start(savedInstanceState);

        this.gson = new Gson();
        this.referenceViews();
    }

    private void referenceViews(){

        this.containerLinearLayout = (LinearLayout) findViewById(R.id.containerLinearLayout);
        this.addTravelerButton = (FloatingActionButton) findViewById(R.id.btnAddDestinationTraveler);

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
                // inace ce tu bit start activity for result sa acr-activitijem
                TravelerView travelerView = new TravelerView(AddDestinationTravelersActivity.this);
                travelerView.setParent(containerLinearLayout);
                travelerView.setTravelerTitleName("Traveler -> " + new Random().nextInt());
                containerLinearLayout.addView(travelerView);
                firstTravelerText.setVisibility(View.GONE);
            }
        });
    }
}
