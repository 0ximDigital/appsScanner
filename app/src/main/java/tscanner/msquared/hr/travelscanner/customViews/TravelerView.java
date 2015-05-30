package tscanner.msquared.hr.travelscanner.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import tscanner.msquared.hr.travelscanner.R;

/**
 * Created by Matej on 5/29/2015.
 */
public class TravelerView extends FrameLayout {
    private final String TAG = getClass().getName();
    private Context context;

    private FrameLayout travelersContainer;
    private LinearLayout travelersInfo;
    private TextView travelerName;
    private ImageView addTravelerImage;
    private ImageView deleteTravelerImage;

    public TravelerView(Context context) {
        super(context);
        this.init(context);
    }

    public TravelerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public TravelerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    private void init(Context context) {
        this.context = context;
        inflate(this.context, R.layout.traveler_view_layout, this);
        this.reference();
    }

    private void reference() {
        this.travelersContainer=(FrameLayout)findViewById(R.id.TravelerViewTravelersContainerFrameLayout);
        this.travelersContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(travelersInfo.getVisibility()==VISIBLE){
                    travelersInfo.setVisibility(GONE);
                }else{
                    travelersInfo.setVisibility(VISIBLE);
                }
            }
        });

        this.travelerName=(TextView) findViewById(R.id.txtTravelerName);
        this.travelersInfo=(LinearLayout) findViewById(R.id.TravelerViewTravelersInfoLinearLayout);

    }

    public void setTravelerName(String travelerName){
        this.travelerName.setText(travelerName);
    }


}
