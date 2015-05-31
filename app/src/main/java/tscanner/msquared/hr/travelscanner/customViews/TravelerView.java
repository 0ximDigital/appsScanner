package tscanner.msquared.hr.travelscanner.customViews;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import tscanner.msquared.hr.travelscanner.R;

/**
 * Created by Matej on 5/29/2015.
 */
public class TravelerView extends FrameLayout {
    private final String TAG = getClass().getSimpleName();
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
        this.travelersInfo = (LinearLayout) findViewById(R.id.TravelerViewTravelersInfoLinearLayout);
        this.travelersContainer = (FrameLayout) findViewById(R.id.TravelerViewTravelersContainerFrameLayout);
        this.travelersContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(travelersInfo.getVisibility() == VISIBLE){
                    travelersInfo.animate().translationX(travelersInfo.getWidth() * -1).setDuration(400).setListener(travelerInfoAnimatorListener);
                } else{
                    travelersInfo.setVisibility(VISIBLE);
                    travelersInfo.animate().translationX(0).setDuration(399).setListener(travelerInfoAnimatorListener);
                }
            }
        });
    }

    public void setTravelerName(String travelerName){
        this.travelerName.setText(travelerName);
    }

    private Animator.AnimatorListener travelerInfoAnimatorListener = new AnimatorListener() {
        @Override
        public void onAnimationStart(Animator animator) {

        }

        @Override
        public void onAnimationEnd(Animator animator) {
            travelersInfo.setVisibility((animator.getDuration() == 400) ? GONE : VISIBLE);
        }

        @Override
        public void onAnimationCancel(Animator animator) {

        }

        @Override
        public void onAnimationRepeat(Animator animator) {

        }
    };
}
