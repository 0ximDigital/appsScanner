package tscanner.msquared.hr.travelscanner.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import tscanner.msquared.hr.travelscanner.R;

/**
 * Created by Mihael on 30.5.2015..
 */
public class TravelPointsCardIndicatorView extends FrameLayout {

    private final String TAG = this.getClass().getSimpleName();
    private Context context;

    public final static int PLANE_ICON = R.drawable.ic_traveler_plane;
    public final static int BOAT_ICON = R.drawable.ic_traveler_boat;
    public final static int TRAIN_ICON = R.drawable.ic_traveler_train;

    private final String serverPlaneString = "plane";
    private final String serverBoatString = "boat";
    private final String serverTrainString = "train";

    private ImageView travelByIcon;
    private TextView travelPointsText;

    public TravelPointsCardIndicatorView(Context context) {
        super(context);
        this.init(context);
    }

    public TravelPointsCardIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public TravelPointsCardIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    private void init(Context context){
        this.context = context;
        inflate(this.context, R.layout.view_travel_points, this);
        this.referenceViews();
    }

    private void referenceViews(){
        this.travelByIcon = (ImageView) findViewById(R.id.imgTravelByIcon);
        this.travelPointsText = (TextView) findViewById(R.id.txtTravelerPoints);
    }

    public void setTravelByIcon(String trawelBy) {
        switch (trawelBy){
            case serverBoatString:
                this.travelByIcon.setImageResource(BOAT_ICON);
                break;
            case serverPlaneString:
                this.travelByIcon.setImageResource(PLANE_ICON);
                break;
            case serverTrainString:
                this.travelByIcon.setImageResource(TRAIN_ICON);
        }
    }

    public void setTravelPointsText(int travelPoints) {
        this.travelPointsText.setText(""+travelPoints);
    }
}
