package tscanner.msquared.hr.travelscanner.activities;

import android.app.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;

import net.steamcrafted.loadtoast.LoadToast;

import java.util.List;

import tscanner.msquared.hr.travelscanner.InputFieldsCheck;
import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.helpers.PrefsHelper;
import tscanner.msquared.hr.travelscanner.helpers.Rest.ServerManager;
import tscanner.msquared.hr.travelscanner.models.restModels.AppUser;
import tscanner.msquared.hr.travelscanner.models.restModels.ResponseMessage;


public class RegistrationActivity extends Activity {

    String TAG=getClass().getName();


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordRepView;
    private EditText mUsernameView;
    private View mProgressView;

    private FloatingActionButton registerButton;

    private PrefsHelper prefsHelper;
    private Gson gson;

    private ServerManager serverManager;
    private LoadToast loadToast;

    private InputFieldsCheck ifc=new InputFieldsCheck();

    boolean[] cancel = {false};
    View[] focusView = {null};

    String email;
    String password;
    String passwordR;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        prefsHelper = new PrefsHelper(this);
        gson = new Gson();

        this.loadToast = new LoadToast(this);
        this.loadToast.setTranslationY(300);

        mEmailView = (AutoCompleteTextView) findViewById(R.id.emailS);
        mPasswordView = (EditText) findViewById(R.id.passwordS);
        mPasswordRepView =(EditText) findViewById(R.id.passwordRepeatS);
        mUsernameView=(EditText) findViewById(R.id.usernameS);

       /* mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        mPasswordRepView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {

                    attemptLogin();
                    return true;
                }
                return false;
            }
        });*/

        registerButton = (FloatingActionButton) findViewById(R.id.email_sign_in_button);
        registerButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });


    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    public void attemptLogin() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mPasswordRepView.setError(null);
        mUsernameView.setError(null);

        loadToast.setText("Registering..").show();

        cancel = new boolean[]{false};
        focusView = new View[]{null};

        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();
        passwordR= mPasswordRepView.getText().toString();
        username = mUsernameView.getText().toString();

        if(!ifc.isPasswordValid(passwordR)){
            mPasswordRepView.setError((CharSequence) ifc.getErrorMessage());
            focusView[0] = mPasswordRepView;
            cancel[0] = true;
        }else if(!ifc.isPasswordsEqual(password,passwordR)){
            mPasswordRepView.setError((CharSequence) ifc.getErrorMessage());
            focusView[0] = mPasswordRepView;
            cancel[0] = true;
        }

        if(!ifc.isPasswordValid(password)) {
            mPasswordView.setError((CharSequence) ifc.getErrorMessage());
            focusView[0] = mPasswordView;
            cancel[0] = true;
        }

        ifc.validadeEmail(email, new InputFieldsCheck.FieldCheckCallback() {
            @Override
            public void checkedField(boolean isItValid) {
                if (isItValid) {
                    Log.d("Sve je oke kod maila", "Ne postoji");
                } else {
                    Log.d("ERROR", "Vec postoji mail");
                    mEmailView.setError(ifc.getErrorMessage());
                    focusView[0] = mEmailView;
                    cancel[0] = true;
                }
                continueToUsernameValidation();
            }
        });
    }

    private void continueToUsernameValidation(){
        ifc.validateUsername(username, new InputFieldsCheck.FieldCheckCallback() {
            @Override
            public void checkedField(boolean isItValid) {
                if (isItValid) {
                    Log.d("Sve je oke kod usera", "Ne postoji");
                } else {
                    mUsernameView.setError(ifc.getErrorMessage());
                    focusView[0] = mUsernameView;
                    cancel[0] = true;
                }
                continueToFinalValidation();
            }
        });
    }

    private void continueToFinalValidation(){
        if (cancel[0]) {
            Log.d(TAG, "invalid change registration");
            focusView[0].requestFocus();
            loadToast.error();
        } else {
            loadToast.success();
            AppUser loggingInUser = new AppUser(email, null, false, password, 0, username);

            this.prefsHelper.putString(PrefsHelper.LOGGED_IN_USER_APPUSER_DATA, (loggingInUser != null) ? gson.toJson(loggingInUser) : null);
            this.prefsHelper.putBoolean(PrefsHelper.FIRST_TIME_TAKING_PHOTO_HINT, true);
            this.prefsHelper.putBoolean(PrefsHelper.FIRST_TIME_TRAVELPOINTS_HINT, true);

            this.addNewAppUser(loggingInUser);
        }
    }


    //not implemented yet
    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(RegistrationActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    private void addNewAppUser(AppUser appUser){
        if(serverManager == null){
            serverManager = new ServerManager();
        }
        serverManager.addNewAppUser(appUser, new ServerManager.Callback<ResponseMessage>() {
            @Override
            public void requestResult(ResponseMessage responseMessage) {
                if (responseMessage.getError() == null) {

                } else {
                    Log.e(TAG, responseMessage.getError());
                }
                Intent intent;
                intent = new Intent(RegistrationActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }


}

