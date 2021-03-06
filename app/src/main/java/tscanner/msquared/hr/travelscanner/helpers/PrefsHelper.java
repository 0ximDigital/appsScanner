package tscanner.msquared.hr.travelscanner.helpers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * Created by Mihael on 14.12.2014..
 */
public class PrefsHelper {
    private final String TAG = this.getClass().getSimpleName();

    private final String prefsName = "hr.msquare.travelScanner";

    public static final String TRAVELERS_DATA = "travleres_data";

    public static final String LOGGED_IN_USER_APPUSER_DATA = "loged_in_user_appuser_data";

    public static final String FIRST_TIME_TAKING_PHOTO_HINT = "first_time_taking_photo_hint";
    public static final String FIRST_TIME_TRAVELPOINTS_HINT = "first_time_travel_points_hint";
    public static final String REMEMBER_USER_LOGIN_SKIP = "remember_user_login_skip";

    private Context context;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public PrefsHelper(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    // ### boiler code
    public String getString(String varName, String defaulrReturnValue) {
        return sharedPreferences.getString(varName, defaulrReturnValue);
    }

    public int getInt(String varName, int defaulrReturnValue) {
        return sharedPreferences.getInt(varName, defaulrReturnValue);
    }

    public Long getLong(String varName, long defaultReturnValue) {
        return sharedPreferences.getLong(varName, defaultReturnValue);
    }

    public boolean getBoolean(String varName, boolean defaulrReturnValue) {
        return sharedPreferences.getBoolean(varName, defaulrReturnValue);
    }

    public Set<String> getStringSet(String varName, Set<String> defaulrReturnValue) {
        return sharedPreferences.getStringSet(varName, defaulrReturnValue);
    }

    public void putString(String varName, String value) {
        editor.putString(varName, value);
        editor.commit();
    }

    public void putInt(String varName, int value) {
        editor.putInt(varName, value);
        editor.commit();
    }

    public void putLong(String varName, long value) {
        editor.putLong(varName, value);
        editor.commit();
    }

    public void putBoolean(String varName, boolean value) {
        editor.putBoolean(varName, value);
        editor.commit();
    }

    public void putStringSet(String varName, Set<String> value) {
        editor.remove(varName);
        editor.commit();
        editor.putStringSet(varName, value);
        editor.apply();
    }

    public boolean prefsContains(String varName) {
        return sharedPreferences.contains(varName);
    }

    public SharedPreferences.Editor remove(String varName) {
        editor.remove(varName);
        editor.commit();
        return editor;
    }

}
