package tscanner.msquared.hr.travelscanner.customViews;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.activities.MainActivity;

/**
 * Created by Mihael on 31.5.2015..
 */
public class PurchaseDialog extends FrameLayout {

    private Context context;

    private TextView statusText;
    private FloatingActionButton button;
    private boolean exitPurchase;

    public PurchaseDialog(Context context) {
        super(context);
    }

    public PurchaseDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PurchaseDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void init(Context context){
        this.context = context;
        inflate(this.context, R.layout.dialog_purchase, this);
        this.referenceViews();
    }

    private void referenceViews(){
        this.statusText = (TextView) findViewById(R.id.txtStatus);
        this.button = (FloatingActionButton) findViewById(R.id.btnFinishAndPay);
        this.button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resetintent = new Intent(context, MainActivity.class);
                resetintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                context.startActivity(resetintent);
            }
        });
    }

    public void showStatus(boolean sveOk){
        exitPurchase = sveOk;
        if(sveOk){
            this.statusText.setText("Thank you for traveling with us :D");
        }
        else{
            this.statusText.setText("Please add at least one person");
        }
    }
}
