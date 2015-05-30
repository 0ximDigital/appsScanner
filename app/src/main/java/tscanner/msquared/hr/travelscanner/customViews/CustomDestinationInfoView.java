package tscanner.msquared.hr.travelscanner.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import tscanner.msquared.hr.travelscanner.R;

/**
 * Created by Mihael on 30.5.2015..
 */
public class CustomDestinationInfoView extends FrameLayout {

    private final String TAG = this.getClass().getSimpleName();
    private Context context;

    private TextView destinationName;
    private TextView destinationLongDescription;
    private TextView destinationPrice;
    private TextView destinationDepartureDate;
    private TextView destinationDuration;
    private TextView destinationTransportType;
    private TextView destinationTravelPoints;

    private FloatingActionButton proceedFAB;

    public CustomDestinationInfoView(Context context) {
        super(context);
        this.init(context);
    }

    public CustomDestinationInfoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public CustomDestinationInfoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    private void init(Context context){
        this.context = context;
        inflate(this.context, R.layout.view_destination_info, this);
        referenceViews();
    }

    private void referenceViews(){
        this.destinationName = (TextView) findViewById(R.id.txtTravelDestinationName);
        this.destinationLongDescription = (TextView) findViewById(R.id.txtTravelDestinationLongDescription);
        this.destinationPrice = (TextView) findViewById(R.id.txtTravelPrice);
        this.destinationDepartureDate = (TextView) findViewById(R.id.txtDepartureDate);
        this.destinationDuration = (TextView) findViewById(R.id.txtTravelDuration);
        this.destinationTransportType = (TextView) findViewById(R.id.txtTraveVehicleType);
        this.destinationTravelPoints = (TextView) findViewById(R.id.txtAwardingTravelPoints);
        this.proceedFAB = (FloatingActionButton) findViewById(R.id.btnselectTravelDestination);
    }

    public void setDestinationDepartureDate(String destinationDepartureDate) {
        String[] dateOnly = destinationDepartureDate.split("\\s+");
        this.destinationDepartureDate.setText(dateOnly[0]);
    }

    public void setDestinationDuration(int destinationDuration) {
        this.destinationDuration.setText(destinationDuration + " days");
    }

    public void setDestinationLongDescription(String destinationLongDescription) {
        this.destinationLongDescription.setText(destinationLongDescription);
    }

    public void setDestinationName(String destinationName) {
        this.destinationName.setText(destinationName);
    }

    public void setDestinationPrice(double destinationPrice) {
        this.destinationPrice.setText(destinationPrice + " euros");
    }

    public void setDestinationTransportType(String destinationTransportType) {
        this.destinationTransportType.setText(destinationTransportType);
    }

    public void setDestinationTravelPoints(int destinationTravelPoints) {
        this.destinationTravelPoints.setText("" + destinationTravelPoints);
    }

    public void setFABOnClickListener(OnClickListener listener){
        this.proceedFAB.setOnClickListener(listener);
    }
}
