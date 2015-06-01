package tscanner.msquared.hr.travelscanner.fragments;

import android.content.Context;
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

import com.github.clans.fab.FloatingActionButton;
import com.google.gson.Gson;

import net.steamcrafted.loadtoast.LoadToast;

import java.security.PrivateKey;

import tscanner.msquared.hr.travelscanner.InputFieldsCheck;
import tscanner.msquared.hr.travelscanner.R;
import tscanner.msquared.hr.travelscanner.activities.RegistrationActivity;
import tscanner.msquared.hr.travelscanner.activities.ScanActivity;
import tscanner.msquared.hr.travelscanner.activities.SettingsActivity;
import tscanner.msquared.hr.travelscanner.helpers.PrefsHelper;
import tscanner.msquared.hr.travelscanner.helpers.Rest.ServerManager;
import tscanner.msquared.hr.travelscanner.models.restModels.AppUser;
import tscanner.msquared.hr.travelscanner.models.restModels.ResponseMessage;

/**
 * Created by Matej on 5/31/2015.
 */
public class SettingsFragment extends android.support.v4.app.Fragment {
    String TAG=getClass().getSimpleName();

    private EditText mUsernameView;
    private EditText mPasswordRepView;
    private EditText mPasswordView;
    private EditText mEmailView;
    private FloatingActionButton btsSave;

    private InputFieldsCheck ifc=new InputFieldsCheck();

    private PrefsHelper prefsHelper;
    private Gson gson;
    private Context context;

    private AppUser appUser;
    private ServerManager serverManager;

    private LoadToast loadToast;

    String email;
    String password;
    String passwordR;
    String username;

    boolean[] cancel = {false};
    View[] focusView = {null};

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
        final View v=inflater.inflate(R.layout.fragment_settings, container, false);
        this.mUsernameView = (EditText) v.findViewById(R.id.usernameS);
        this.mPasswordView = (EditText) v.findViewById(R.id.passwordS);
        this.mPasswordRepView = (EditText)v.findViewById(R.id.passwordRepeatS);
        this.mEmailView = (AutoCompleteTextView) v.findViewById(R.id.emailS);
        this.btsSave = (FloatingActionButton) v.findViewById(R.id.saveFab);
        this.context = getActivity();
        this.prefsHelper = new PrefsHelper(context);
        this.gson = new Gson();

        this.loadToast = new LoadToast(getActivity());
        this.loadToast.setTranslationY(300);

        populateUserData();

        this.btsSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptSave();
            }
        });
        return v;
    }

    private void populateUserData(){
        String appUserData = this.prefsHelper.getString(PrefsHelper.LOGGED_IN_USER_APPUSER_DATA, null);
        if(appUserData != null){
            this.appUser = gson.fromJson(appUserData, AppUser.class);
            if(this.appUser != null){
                this.mEmailView.setText(this.appUser.getEmail());
                this.mUsernameView.setText(this.appUser.getUsername());
            }
        }
    }

    public void attemptSave() {

        this.loadToast.setText("Updating..").show();
        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mPasswordRepView.setError(null);
        mUsernameView.setError(null);

        // Store values at the time of the login attempt.
        email = mEmailView.getText().toString();
        password = mPasswordView.getText().toString();
        passwordR = mPasswordRepView.getText().toString();
        username = mUsernameView.getText().toString();

        cancel = new boolean[]{false};
        focusView = new View[]{null};

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
                    //sve je oke
                } else {
                    mEmailView.setError((CharSequence) ifc.getErrorMessage());
                    focusView[0] = mEmailView;
                    cancel[0] = true;
                }
                proceedToFinalValidation();
            }
        });
    }

    private void proccedToUsernameValidation(){
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
                proceedToFinalValidation();
            }
        });
    }

    private void proceedToFinalValidation(){
        if (cancel[0]) {
            this.loadToast.error();
            Log.d(TAG, "invalid change settings");
            focusView[0].requestFocus();
        } else {
            appUser.setEmail(email);
            appUser.setPassword(password);
            appUser.setUsername(username);
            updateUser(appUser);
        }
    }

    private void updateUser(AppUser user){
        if(serverManager == null){
            serverManager = new ServerManager();
        }
        serverManager.updateAppUserWithId(user.getId(), user, new ServerManager.Callback<ResponseMessage>() {
            @Override
            public void requestResult(ResponseMessage responseMessage) {
                if (responseMessage.getError() == null) {
                    loadToast.success();
                    String userData = gson.toJson(user);
                    prefsHelper.putString(PrefsHelper.LOGGED_IN_USER_APPUSER_DATA, (userData != null) ? gson.toJson(userData) : null);
                    startActivity(new Intent(context, SettingsActivity.class));
                } else {
                    Log.e(TAG, responseMessage.getError());
                    loadToast.error();
                }
            }
        });
    }


 }
