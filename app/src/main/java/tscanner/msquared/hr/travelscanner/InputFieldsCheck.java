package tscanner.msquared.hr.travelscanner;

import android.content.res.Resources;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import tscanner.msquared.hr.travelscanner.helpers.Rest.ServerManager;
import tscanner.msquared.hr.travelscanner.models.restModels.AppUser;

/**
 * Created by Matej on 6/1/2015.
 */
public class InputFieldsCheck {
    String TAG=getClass().getName();
    private ServerManager serverManager;

    private int MIN_PASSWORD_LENGTH=5;
    private int MIN_USERNAME_LENGTH=5;

    private String errorMessage="";


    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    public boolean isEmailValid(String email) {
        boolean valid_email=true;

        if(!email.contains("@")){
            valid_email=false;
            setErrorMessage("Invalid e-mail");
        }else  if (TextUtils.isEmpty(email)) {
            valid_email=false;
            setErrorMessage("E-mail field required");
        }else{
          //ako postoj
        }

    return    valid_email;
    }



    public boolean isUsernameValid(String username) {
        boolean valid_username=true;
        if(!( username.length() > MIN_USERNAME_LENGTH)){
           setErrorMessage("Prekratak tekst.Min 6 znakova.");
            valid_username=false;
        }else if (TextUtils.isEmpty(username)) {
            setErrorMessage(Resources.getSystem().getString(R.string.error_field_required));
            valid_username=false;
        }else{

           //ako postoji error

        }
        return   valid_username;
    }


    public boolean isPasswordValid(String password) {
        boolean valid_password=true;

        if (TextUtils.isEmpty(password)) {
            valid_password=false;
            setErrorMessage("Password field required");
        }else if(!(password.length() > MIN_PASSWORD_LENGTH)){
            valid_password=false;
            setErrorMessage("Password too short");
        }
        return  valid_password;
    }

    public boolean isPasswordsEqual(String password1,String password2) {
        boolean valid_password=true;
        if(!(password1.equals(password2))){
            valid_password=false;
            setErrorMessage("Passwords are not equal");
        }
        return valid_password;
    }

}
