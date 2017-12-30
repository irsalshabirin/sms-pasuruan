package co.dust.smspasuruan.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.Calendar;

import co.dust.smspasuruan.BuildConfig;

/**
 * Created by irsal on 3/1/17.
 */

public class CredentialStorage {

    private static final String TAG = CredentialStorage.class.getSimpleName();

    //    public static final String PREF_IS_FIRST_START = "pref_is_first_start";
    public static final String PREF_IS_ALREADY_LOGIN = "pref_is_already_login";

    public static final String PREF_USERID = "pref_userid";
    public static final String PREF_USERNAME = "pref_username";

    public static final String PREF_SATKER_ID = "pref_satker_id";
    private static final String PREF_SATKER_NAME = "pref_satker_name";

    public static final String PREF_NAME = "pref_name";
    private static final String PREF_FCM_TOKEN = "pref_fcm_token";
    private static final String PREF_YEAR = "pref_year";


    private Activity activity;
    private Context context;
    private SharedPreferences sharedPreferences;


    public CredentialStorage(Activity activity) {
        this.activity = activity;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity);
    }

    public CredentialStorage(Context mContext) {
        this.context = mContext;
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
    }


    public Activity getActivity() {
        if (activity == null) {
            return (Activity) context;
        }
        return activity;
    }

    @Nullable
    public String getUserId() {
        return sharedPreferences.getString(PREF_USERID, "");
    }

    public void setUserId(@NonNull String userId) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "userId : " + userId);
        }
        sharedPreferences.edit().putString(PREF_USERID, userId).apply();
    }

    @Nullable
    public String getUsername() {
        return sharedPreferences.getString(PREF_USERNAME, "");
    }


    public void setUsername(@NonNull String username) {
        sharedPreferences.edit().putString(PREF_USERNAME, username).apply();
    }

//    public boolean isFirstStart() {
//        return sharedPreferences.getBoolean(PREF_IS_FIRST_START, true);
//    }

    public boolean isAlreadyLogin() {
        return sharedPreferences.getBoolean(PREF_IS_ALREADY_LOGIN, false);
    }

    public void setIsAlreadyLogin(boolean isAlreadyLogin) {
        sharedPreferences.edit().putBoolean(PREF_IS_ALREADY_LOGIN, isAlreadyLogin).apply();
    }


    public void logout() {
        sharedPreferences.edit().remove(PREF_IS_ALREADY_LOGIN).apply();
        sharedPreferences.edit().remove(PREF_USERID).apply();
        sharedPreferences.edit().remove(PREF_USERNAME).apply();
        sharedPreferences.edit().remove(PREF_NAME).apply();

        sharedPreferences.edit().remove(PREF_FCM_TOKEN).apply();
        sharedPreferences.edit().remove(PREF_SATKER_ID).apply();
    }

//    public void setIsFirstStart(boolean isFirstStart) {
//        sharedPreferences.edit().putBoolean(PREF_IS_FIRST_START, isFirstStart).apply();
//    }

    public void setName(String name) {
        sharedPreferences.edit().putString(PREF_NAME, name).apply();
    }

    public String getName() {
        return sharedPreferences.getString(PREF_NAME, "");
    }

    public void setSatkerId(String satKerID) {
        sharedPreferences.edit().putString(PREF_SATKER_ID, satKerID).apply();
    }

    public String getSatkerId() {
        return sharedPreferences.getString(PREF_SATKER_ID, "");
    }

    public void setFCMToken(String refreshedToken) {
        sharedPreferences.edit().putString(PREF_FCM_TOKEN, refreshedToken).apply();
    }

    public String getFCMToken() {
        return sharedPreferences.getString(PREF_FCM_TOKEN, "");
    }

    public void setYear(String s) {
        sharedPreferences.edit().putString(PREF_YEAR, s).apply();
    }

    public String getYear() {
        return sharedPreferences.getString(PREF_YEAR, String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
    }

    public void setSatkerName(String satKer) {
        sharedPreferences.edit().putString(PREF_SATKER_NAME, satKer).apply();
    }

    public String getSatkerName() {
        return sharedPreferences.getString(PREF_SATKER_NAME, "");
    }
}
