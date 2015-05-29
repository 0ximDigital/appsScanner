package tscanner.msquared.hr.travelscanner.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import tscanner.msquared.hr.travelscanner.customViews.CustomMaterialDialog;
import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.customViews.TravelerView;

/**
 * Created by Matej on 5/29/2015.
 */
public class Test extends Activity {

    private CustomMaterialDialog cmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
        this.reference();

        TravelerView tv=new TravelerView(this);
        this.cmd.addTravelerView(tv);

    }

    private void reference() {
        this.cmd=(CustomMaterialDialog) findViewById(R.id.travelerView);
    }


}
