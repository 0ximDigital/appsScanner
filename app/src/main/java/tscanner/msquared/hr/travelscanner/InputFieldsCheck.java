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


    public interface FieldCheckCallback{
        void checkedField(boolean isItValid);
    }

    public void validadeEmail(final String email, final FieldCheckCallback callback) {
         if(!email.contains("@")){
            setErrorMessage("Invalid e-mail");
            callback.checkedField(false);
        }else {
            if (TextUtils.isEmpty(email)) {
                setErrorMessage("E-mail field required");
                callback.checkedField(false);
            } else {
                if (serverManager == null) {
                    serverManager = new ServerManager();
                }

                serverManager.getAllAppUsers(new ServerManager.Callback<List<AppUser>>() {
                    @Override
                    public void requestResult(List<AppUser> appUsers) {
                        if (appUsers != null) {
                            for (AppUser user : appUsers) {
                           /* Log.d("IFC","dohvatio usera "+user.getEmail());
                            Log.d("IFC","upisao "+email);*/
                                if ((user.getEmail()).equals(email)) {
                                    if (callback != null) {
                                        setErrorMessage("E-mail already exists");
                                        callback.checkedField(false);
                                        return;
                                    }
                                }
                            }
                            if (callback != null) {
                                callback.checkedField(true);
                            }

                        } else {
                            if (callback != null) {
                                callback.checkedField(true);
                            }
                        }
                    }
                });

            }
        }

    }






    public void validateUsername(final String username, final FieldCheckCallback callback) {
        if(!( username.length() >= MIN_USERNAME_LENGTH)){
           setErrorMessage("Username too short.(Min. 5 characters)");
            callback.checkedField(false);
        }else if (TextUtils.isEmpty(username)) {
            setErrorMessage(Resources.getSystem().getString(R.string.error_field_required));
            callback.checkedField(false);
        }else{
            if(serverManager == null){
                serverManager = new ServerManager();
            }

            serverManager.getAllAppUsers(new ServerManager.Callback<List<AppUser>>() {
                @Override
                public void requestResult(List<AppUser> appUsers) {
                    if (appUsers != null) {
                        for (AppUser user : appUsers) {
                            if((user.getUsername()).equals(username)){
                                if(callback != null){
                                    setErrorMessage("Username already exists");
                                    callback.checkedField(false);
                                    return;
                                }
                            }
                        }
                        if(callback != null){
                            callback.checkedField(true);
                        }
                    }
                    else{
                        if(callback != null){
                            callback.checkedField(true);
                        }
                    }
                }
            });
        }
    }




    public boolean isPasswordValid(String password) {
        boolean valid_password=true;

        if (TextUtils.isEmpty(password)) {
            valid_password=false;
            setErrorMessage("Password field required");
        }else if(!(password.length() >= MIN_PASSWORD_LENGTH)){
            valid_password=false;
            setErrorMessage("Password too short.(Min. 5 characters)");
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
