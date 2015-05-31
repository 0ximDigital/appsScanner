package tscanner.msquared.hr.travelscanner.customViews;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.models.TravelerDataValues;

/**
 * Created by Matej on 5/29/2015.
 */
public class TravelerView extends FrameLayout {
    private final String TAG = getClass().getSimpleName();
    private Context context;

    private FrameLayout travelersContainer;
    private LinearLayout travelersInfo;

    private TravelerDataValues data;

    private TextView travelerTitleName;
    private TextView travelerNameAndSurname;
    private TextView travelerBirthDate;
    private TextView travelerIdNumber;
    private ImageView deleteTravelerIcon;

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

        this.travelerTitleName = (TextView) findViewById(R.id.txtTravelerNameTitle);
        this.travelerNameAndSurname = (TextView) findViewById(R.id.txtTravelerNameAndSurname);
        this.travelerBirthDate = (TextView) findViewById(R.id.txtTravelerBirthDate);
        this.travelerIdNumber = (TextView) findViewById(R.id.txtTravelerIdNumber);

        this.deleteTravelerIcon = (ImageView) findViewById(R.id.imgBtnRemoveTraveler);
        this.deleteTravelerIcon.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                delteSelf();
            }
        });

        this.travelersContainer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (travelersInfo.getVisibility() == VISIBLE) {
                    travelersInfo.animate().translationX(travelersInfo.getWidth() * -1).setDuration(400).setListener(travelerInfoAnimatorListener);
                } else {
                    travelersInfo.setVisibility(VISIBLE);
                    travelersInfo.animate().translationX(0).setDuration(399).setListener(travelerInfoAnimatorListener);
                }
            }
        });
    }

    public TravelerDataValues getData() {
        return data;
    }

    public void setData(TravelerDataValues data) {
        this.data = data;
    }

    private void delteSelf(){
        if (deleteCallback != null) {
            deleteCallback.deleteView(this);
        }
    }

    public interface DeleteViewInterface{
        void deleteView(TravelerView view);
    }

    private DeleteViewInterface deleteCallback;

    public void setDeleteCallback(DeleteViewInterface deleteCallback) {
        this.deleteCallback = deleteCallback;
    }

    public void setTravelerTitleName(String travelerTitleName){
        this.travelerTitleName.setText(travelerTitleName);
    }

    public void setTravelerNameAndSurname(String travelerName){
        this.travelerNameAndSurname.setText(travelerName);
    }

    public void setTravelerBirthDate(String travelerBirthDate){
        this.travelerBirthDate.setText(travelerBirthDate);
    }

    public void setTravelerIdNumber(String travelerIdNumber){
        this.travelerIdNumber.setText(travelerIdNumber);
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
