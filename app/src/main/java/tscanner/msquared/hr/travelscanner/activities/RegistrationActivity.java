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

import java.util.List;

import tscanner.msquared.hr.travelscanner.InputFieldsCheck;
import tscanner.msquared.hr.travelscanner.R;


public class RegistrationActivity extends Activity {

    String TAG=getClass().getName();


    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordRepView;
    private EditText mUsernameView;
    private View mProgressView;

    private FloatingActionButton registerButton;

    private InputFieldsCheck ifc=new InputFieldsCheck();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        // Set up the login form.
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

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String passwordR= mPasswordRepView.getText().toString();
        String username = mUsernameView.getText().toString();

        final boolean[] cancel = {false};
        final View[] focusView = {null};

        /*//Check for a valid repedated password,if the user entered one
        if (TextUtils.isEmpty(passwordR)) {
            mPasswordRepView.setError(getString(R.string.error_field_required));
            focusView = mPasswordRepView;
            cancel = true;
        }else if (!ifc.isPasswordValid(passwordR)) {
            mPasswordRepView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordRepView;
            cancel = true;
        }else if(!(passwordR.equals(password))){
            mPasswordRepView.setError(getString(R.string.error_password_match));
            focusView = mPasswordRepView;
            cancel = true;
        }*/

        if(!ifc.isPasswordValid(passwordR)){
            mPasswordRepView.setError((CharSequence) ifc.getErrorMessage());
            focusView[0] = mPasswordRepView;
            cancel[0] = true;
        }else if(!ifc.isPasswordsEqual(password,passwordR)){
            mPasswordRepView.setError((CharSequence) ifc.getErrorMessage());
            focusView[0] = mPasswordRepView;
            cancel[0] = true;
        }


      /*  // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }else if(!ifc.isPasswordValid(password)){
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }*/

        if(!ifc.isPasswordValid(password)) {
            mPasswordView.setError((CharSequence) ifc.getErrorMessage());
            focusView[0] = mPasswordView;
            cancel[0] = true;
        }

        // Check for a valid email address.
       /* if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!ifc.isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }*/

        ifc.validadeEmail(email, new InputFieldsCheck.FieldCheckCallback() {
            @Override
            public void checkedField(boolean isItValid) {
                if(isItValid){
                    //sve je oke
                    Log.d("Sve je oke kod maila","Ne postoji");


                }
                else{
                    Log.d("ERROR","Vec postoji mail");
                    mEmailView.setError(ifc.getErrorMessage());
                    focusView[0] = mEmailView;
                    cancel[0] = true;
                }
            }


        });

        ifc.validateUsername(username, new InputFieldsCheck.FieldCheckCallback() {
            @Override
            public void checkedField(boolean isItValid) {
                if(isItValid){
                   //sve je oke
                    Log.d("Sve je oke kod usera","Ne postoji");

                }
                else{
                    mUsernameView.setError( ifc.getErrorMessage());
                    focusView[0] = mUsernameView;
                    cancel[0] = true;
                }
            }
        });




        if (cancel[0]) {
            Log.d(TAG, "invalid change registration");
            focusView[0].requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //TODO send data to UserRegistrationTaskClass
            Intent intent;
            intent = new Intent(RegistrationActivity.this, MainActivity.class);
            startActivity(intent);

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


}

