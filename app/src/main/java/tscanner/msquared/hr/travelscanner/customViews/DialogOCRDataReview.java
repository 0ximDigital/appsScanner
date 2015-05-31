package tscanner.msquared.hr.travelscanner.customViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.github.clans.fab.FloatingActionButton;

import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.models.TravelerDataValues;

/**
 * Created by Mihael on 31.5.2015..
 */
public class DialogOCRDataReview extends FrameLayout {

    private final String TAG = this.getClass().getSimpleName();

    private Context context;

    private FloatingActionButton cancelButton;
    private FloatingActionButton acceptButton;

    private EditText editName;
    private EditText editBirth;
    private EditText editCardId;

    private LinearLayout dataContainer;
    private FrameLayout spinnerContainer;
    private ProgressBar progressSpinnerClockwise;
    private ProgressBar progressSpinnerAnticlockwise;

    private TravelerDataValues values;

    public DialogOCRDataReview(Context context) {
        super(context);
        this.init(context);
    }

    public DialogOCRDataReview(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public DialogOCRDataReview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    private void init(Context context){
        this.context = context;
        inflate(this.context, R.layout.view_ocr_data_review_dialog, this);
        this.referenceViews();

        this.hideData();
    }

    private void referenceViews(){
        this.editName = (EditText) findViewById(R.id.edTxtTravelerNameAndSurname);
        this.editBirth = (EditText) findViewById(R.id.edTxtTravelerBirthDate);
        this.editCardId = (EditText) findViewById(R.id.edTxtTravelerIdNumber);

        this.dataContainer = (LinearLayout) findViewById(R.id.AllDataLinearLayout);
        this.spinnerContainer = (FrameLayout) findViewById(R.id.spinnerContainer);
        this.progressSpinnerClockwise = (ProgressBar) findViewById(R.id.progressSpinnerClockwise);
        this.progressSpinnerAnticlockwise = (ProgressBar) findViewById(R.id.progressSpinnerAntiClockwise);

        this.cancelButton = (FloatingActionButton) findViewById(R.id.btnReshootID);
        this.cancelButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                hideDialog();
            }
        });
        this.acceptButton = (FloatingActionButton) findViewById(R.id.btnConfirmDestinationTravelerData);
        this.acceptButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO return result
            }
        });
    }

    public void showData(TravelerDataValues values, boolean warning){
        this.values = values;
        this.editName.setText( values.getName() + " " + values.getSurname() );
        this.editBirth.setText(values.getDateOfBirth());
        this.editCardId.setText(values.getIdNumber());

        this.progressSpinnerClockwise.setVisibility(INVISIBLE);
        this.progressSpinnerAnticlockwise.setVisibility(INVISIBLE);
        this.spinnerContainer.setVisibility(INVISIBLE);
        this.dataContainer.setVisibility(VISIBLE);
    }

    public void hideData(){
        this.progressSpinnerClockwise.setVisibility(VISIBLE);
        this.progressSpinnerAnticlockwise.setVisibility(VISIBLE);
        this.spinnerContainer.setVisibility(VISIBLE);
        this.dataContainer.setVisibility(INVISIBLE);
    }

    public void setEditName(String name){
        this.editName.setText(name);
    }

    public void setEditBirth(String birthDate){
        this.editBirth.setText(birthDate);
    }

    public void setEditCardId(String cardId){
        this.editCardId.setText(cardId);
    }

    private void hideDialog(){
        this.setVisibility(GONE);
    }
}
