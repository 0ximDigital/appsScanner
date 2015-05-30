package tscanner.msquared.hr.travelscanner.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import tscanner.msquared.hr.travelscanner.R;

/**
 * Created by Mihael on 30.5.2015..
 */
public class CustomDestinationInfoView extends FrameLayout {

    private final String TAG = this.getClass().getSimpleName();
    private Context context;

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

    }
}
