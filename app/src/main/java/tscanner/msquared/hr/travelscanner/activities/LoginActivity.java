package tscanner.msquared.hr.travelscanner.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import tscanner.msquared.hr.travelscanner.R;

//pocetna
public class LoginActivity extends Activity {

    private Button login;
    EditText username=null;
    EditText password=null;
    TextView loginAnonymous;
    boolean GLOBAL_FAST_ENTRY=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.login = (Button)findViewById(R.id.login);
        this.username = (EditText)findViewById(R.id.editUsername);
        this.password = (EditText)findViewById(R.id.editPassword);
        this.loginAnonymous=(TextView)findViewById(R.id.loginAnonymous);
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
            Log.d("Sifra i user je:", "admin");
        }
    }

    public void anonymousOnClick(View view){
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
