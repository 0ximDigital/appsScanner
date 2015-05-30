package tscanner.msquared.hr.travelscanner;

import android.content.Intent;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import java.util.List;

import tscanner.msquared.hr.travelscanner.activities.MainActivity;
import tscanner.msquared.hr.travelscanner.activities.RegistrationActivity;

/**
 * Created by Matej on 5/30/2015.
 */
/**
 * Represents an asynchronous login/registration task used to authenticate
 * the user.
 */
public class UserRegistrationTaskClass {

    private final String mEmail;
    private final String mPassword;


    UserRegistrationTaskClass(String email, String password) {
        mEmail = email;
        mPassword = password;
        /*LoginActivity la=new LoginActivity();
        la.AcceptedLogin();*/
    }
    //TODO dodavanje osobe u bazu i ulogiravanje korisnika



}