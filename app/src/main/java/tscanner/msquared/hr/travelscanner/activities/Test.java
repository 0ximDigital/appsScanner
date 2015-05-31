package tscanner.msquared.hr.travelscanner.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import tscanner.msquared.hr.travelscanner.customViews.CustomMaterialDialog;
import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.customViews.TravelerView;

/**
 * Created by Matej on 5/29/2015.
 */
public class Test extends Activity {

    private LinearLayout containerLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        this.referenceViews();
    }

    private void referenceViews(){

        this.containerLinearLayout = (LinearLayout) findViewById(R.id.containerLinearLayout);

        for(int i=0; i<5; i++){
            TravelerView view = new TravelerView(this, this.containerLinearLayout);
            view.setTravelerTitleName("Traveler -> " + i);
            this.containerLinearLayout.addView(view);
        }

        /*
        TravelerView tv=new TravelerView(this);
        this.cmd.addTravelerView(tv);
         */
    }
}
