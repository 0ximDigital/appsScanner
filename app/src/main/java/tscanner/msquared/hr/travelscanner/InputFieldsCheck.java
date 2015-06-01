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
    private boolean valid_check=false;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public boolean isValid_check() {
        return valid_check;
    }

    public void setValid_check(boolean valid_check) {
        this.valid_check = valid_check;
    }




/*
    public boolean isEmailValid(String email) {
        if(!email.contains("@")){
                setValid_check(false);
        }else if() {


            if (serverManager == null) {
                serverManager = new ServerManager();
            }
            serverManager.getAllAppUsers(new ServerManager.Callback<List<AppUser>>() {
                @Override
                public void requestResult(List<AppUser> appUsers) {
                    if (appUsers != null) {
                        for (AppUser user : appUsers) {
                            Log.i(TAG, "User -> " + user);
                        }

                    }
                }
            });
        }


    return valid_mail;
    }*/



    public boolean isUsernameValid(String username) {
        boolean valid_username=false;

        if(!( username.length() > MIN_USERNAME_LENGTH)){
           setErrorMessage("Prekratak tekst.Min 5 znakova.");
            return;
        }else if (TextUtils.isEmpty(username)) {
            setErrorMessage(Resources.getSystem().getString(R.string.error_field_required));
        }else{

           //ako postoji error

        }
        return isValid_check();
    }


    public boolean isPasswordValid(String password) {
        boolean valid_passsword=false;
        if((password.length() > MIN_PASSWORD_LENGTH)){
            valid_passsword=true;
        }
        return valid_passsword;
    }

}
