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

    private ImageView travelByIcon;
    private TextView travelPointsText;

    public TravelPointsCardIndicatorView(Context context) {
        super(context);
    }

    public TravelPointsCardIndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TravelPointsCardIndicatorView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
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

    public void setTravelByIcon(int resId) {
        this.travelByIcon.setImageResource(resId);
    }

    public void setTravelPointsText(int travelPoints) {
        this.travelPointsText.setText(travelPoints);
    }
}
