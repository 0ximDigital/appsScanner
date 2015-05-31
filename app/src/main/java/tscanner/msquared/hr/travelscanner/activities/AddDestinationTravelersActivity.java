package tscanner.msquared.hr.travelscanner.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.github.clans.fab.FloatingActionButton;

import java.util.Random;

import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.customViews.TravelerView;

/**
 * Created by Matej on 5/29/2015.
 */
public class AddDestinationTravelersActivity extends Activity {

    private final String TAG = this.getClass().getSimpleName();

    private LinearLayout containerLinearLayout;

    private ImageView destinationBottomImage;

    private FloatingActionButton addTravelerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_destination_travelers);
        this.referenceViews();
    }

    private void referenceViews(){

        this.containerLinearLayout = (LinearLayout) findViewById(R.id.containerLinearLayout);
        this.addTravelerButton = (FloatingActionButton) findViewById(R.id.btnAddDestinationTraveler);
        this.destinationBottomImage = (ImageView) findViewById(R.id.imgDestinationBottom);
        // TODO - postavim image ovisno o destianciji
        this.addTravelerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // inace ce tu bit start activity for result sa acr-activitijem
                TravelerView travelerView = new TravelerView(AddDestinationTravelersActivity.this);
                travelerView.setParent(containerLinearLayout);
                travelerView.setTravelerTitleName("Traveler -> " + new Random().nextInt());
                containerLinearLayout.addView(travelerView);
            }
        });
    }
}
