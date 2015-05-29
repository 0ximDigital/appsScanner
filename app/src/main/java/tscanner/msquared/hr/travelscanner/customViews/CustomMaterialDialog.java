package tscanner.msquared.hr.travelscanner.customViews;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

import me.drakeet.materialdialog.MaterialDialog;
import tscanner.msquared.hr.travelscanner.R;


/**
 * Created by Matej on 5/29/2015.
 */
public class CustomMaterialDialog extends FrameLayout {
    private final String TAG = getClass().getName();
    private Context context;
    private LinearLayout travelersLinearLayout;

    public CustomMaterialDialog(Context context){
        super(context);
        this.init(context);
    }

    public CustomMaterialDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public CustomMaterialDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    private void init(Context context) {
        this.context = context;
        inflate(this.context, R.layout.material_dialog_layout, this);
        this.reference();
    }

    private void reference() {
        this.travelersLinearLayout=(LinearLayout)findViewById(R.id.MaterialDialogTravelerListLinearLayout);
    }
    public void addTravelerView(TravelerView tView){
        this.travelersLinearLayout.addView(tView);
    }


}

