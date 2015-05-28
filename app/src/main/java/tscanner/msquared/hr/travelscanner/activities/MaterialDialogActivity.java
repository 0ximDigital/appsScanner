package tscanner.msquared.hr.travelscanner.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import me.drakeet.materialdialog.MaterialDialog;
import tscanner.msquared.hr.travelscanner.R;


/**
 * Created by Matej on 5/29/2015.
 */
public class MaterialDialogActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        Button b=(Button)findViewById(R.id.test);


    }

    public void click(View view){
        MaterialDialogStart();
    }

    public void MaterialDialogStart(){
        final MaterialDialog mMaterialDialog = new MaterialDialog(this);
        mMaterialDialog.setTitle("MaterialDialog");
        mMaterialDialog.setMessage("Hello world!");
        mMaterialDialog.setPositiveButton("OK", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();

            }
        });
        mMaterialDialog.setNegativeButton("CANCEL", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMaterialDialog.dismiss();

            }
        });

        mMaterialDialog.show();
    }


}
