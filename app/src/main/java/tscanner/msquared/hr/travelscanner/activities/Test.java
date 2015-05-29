package tscanner.msquared.hr.travelscanner.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import tscanner.msquared.hr.travelscanner.R;

/**
 * Created by Matej on 5/29/2015.
 */
public class Test extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);
    }

    public void click(View view){
        Intent intent;
        intent = new Intent(Test.this, MaterialDialogActivity.class);
        startActivity(intent);
    }


}
