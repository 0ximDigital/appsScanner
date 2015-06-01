package tscanner.msquared.hr.travelscanner.customViews;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;

import java.util.Random;

import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.activities.MainActivity;
import tscanner.msquared.hr.travelscanner.helpers.PrefsHelper;

/**
 * Created by Mihael on 31.5.2015..
 */
public class PurchaseDialog extends FrameLayout {

    private Context context;

    private TextView statusText;
    private FloatingActionButton button;
    private FloatingActionButton buttonCancerl;
    private boolean exitPurchase;

    private PrefsHelper prefsHelper;

    public PurchaseDialog(Context context) {
        super(context);
        this.init(context);
    }

    public PurchaseDialog(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public PurchaseDialog(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    private void init(Context context){
        this.context = context;
        inflate(this.context, R.layout.dialog_purchase, this);
        prefsHelper = new PrefsHelper(context);
        this.referenceViews();
    }

    private void referenceViews(){
        this.statusText = (TextView) findViewById(R.id.txtStatusDialog);
        this.button = (FloatingActionButton) findViewById(R.id.btnFinishAndPay);
        this.button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                if (exitPurchase) {
                    prefsHelper.putStringSet(PrefsHelper.TRAVELERS_DATA, null);
                    updateDatabase();
                    Intent resetintent = new Intent(context, MainActivity.class);
                    resetintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    context.startActivity(resetintent);
                } else {
                    setVisibility(GONE);
                }
            }
        });

        this.buttonCancerl = (FloatingActionButton) findViewById(R.id.btnCancel);
        this.buttonCancerl.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setVisibility(GONE);
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

    private void updateDatabase(){
        String purchaseSignatureCheck = this.generatePurchaseSignature();
    }




    private String generatePurchaseSignature(){
        char[] chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 24; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        return sb.toString();
    }
}
