package tscanner.msquared.hr.travelscanner.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import tscanner.msquared.hr.travelscanner.InputFieldsCheck;
import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.activities.RegistrationActivity;
import tscanner.msquared.hr.travelscanner.activities.ScanActivity;
import tscanner.msquared.hr.travelscanner.activities.SettingsActivity;

/**
 * Created by Matej on 5/31/2015.
 */
public class SettingsFragment extends android.support.v4.app.Fragment {
    String TAG=getClass().getName();

    private EditText mUsernameView;
    private EditText mPasswordRepView;
    private EditText mPasswordView;
    private EditText mEmailView;
    private Button btsSave;

    private InputFieldsCheck ifc=new InputFieldsCheck();

    public static final String TITLE = "Setting";

    public static SettingsFragment newInstance(){
        return new SettingsFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,@Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        final View v=inflater.inflate(R.layout.fragment_settings,container,false);
        this.mUsernameView = (EditText) v.findViewById(R.id.usernameS);
        this.mPasswordView = (EditText) v.findViewById(R.id.passwordS);
        this.mPasswordRepView = (EditText)v.findViewById(R.id.passwordRepeatS);
        this.mEmailView = (AutoCompleteTextView) v.findViewById(R.id.emailS);
        this.btsSave = (Button) v.findViewById(R.id.save);

        this.btsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSave();
            }
        });

        return v;
    }

    public void attemptSave() {

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mPasswordRepView.setError(null);
        mUsernameView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String passwordR = mPasswordRepView.getText().toString();
        String username = mUsernameView.getText().toString();

        final boolean[] cancel = {false};
        final View[] focusView = {null};

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
                if(isItValid){
                    //sve je oke
                }
                else{
                    mEmailView.setError((CharSequence) ifc.getErrorMessage());
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
                }
                else{
                    mUsernameView.setError((CharSequence) ifc.getErrorMessage());
                    focusView[0] = mUsernameView;
                    cancel[0] = true;
                }
            }
        });


        if (cancel[0]) {
            Log.d(TAG, "invalid change settings");
            focusView[0].requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //TODO change data in base
            startActivity(new Intent(getActivity(), SettingsActivity.class));
        }
    }




 }
