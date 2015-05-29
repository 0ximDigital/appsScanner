package tscanner.msquared.hr.travelscanner.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.helpers.ServerManager;
import tscanner.msquared.hr.travelscanner.models.restModels.AppUser;

//pocetna
public class LoginActivity extends Activity {

    private final String TAG = this.getClass().getName();

    private Button login;
    private EditText username=null;
    private EditText password=null;
    private TextView loginAnonymous;
    private boolean GLOBAL_FAST_ENTRY=true;

    private Button testButton;

    private ServerManager serverManager;
    private ProgressBar progressBar;

    private List<AppUser> appUserList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.referenceViews();
    }

    private void referenceViews(){
        this.login = (Button)findViewById(R.id.login);
        this.username = (EditText)findViewById(R.id.editUsername);
        this.password = (EditText)findViewById(R.id.editPassword);
        this.loginAnonymous=(TextView)findViewById(R.id.loginAnonymous);

        this.testButton = (Button) findViewById(R.id.btnTest);
        this.progressBar = (ProgressBar) findViewById(R.id.progressBarTest);
        this.testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(serverManager == null){
                   serverManager = new ServerManager();
                }
                Log.i(TAG, "Pocinje dohvat");
                appUserList = serverManager.getAllAppUsers();
                Log.i(TAG, "Gotov dohvat");

                for(AppUser user : appUserList){
                    Log.i(TAG, "User -> " + user);
                }
            }
        });
    }


    public void login(View view){
        if(username.getText().toString().equals("admin") &&
                password.getText().toString().equals("admin")) {
            //correcct password
            //Toast.makeText(getApplicationContext(),"Redirecting..",Toast.LENGTH_SHORT).show();
            AcceptedLogin();

        }else{
            //wrong password
            Toast.makeText(getApplicationContext(),"sve je: \"admin\" ",Toast.LENGTH_LONG).show();
            Log.d("Login:", "User");
        }
    }

    public void anonymousOnClick(View view){
        Log.d("Login:","Anonymous");
        if(GLOBAL_FAST_ENTRY){
            AcceptedLogin();
        }
    }


    private void AcceptedLogin(){
        Intent intent;
        intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
    }




}
