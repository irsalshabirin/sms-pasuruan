package co.dust.smspasuruan.tools;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.View;
import android.view.WindowManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by irsal on 5/29/17.
 */

public class StaticHelper {


    private static ProgressDialog mProgressDialog;

    public static void showProgressDialog(Activity activity, String title, String message, boolean isCancelable) {
        try {
            if (mProgressDialog == null) {
                mProgressDialog = new ProgressDialog(activity);

                if (title != null) {
                    mProgressDialog.setTitle(title);
                }

                if (message != null) {
                    mProgressDialog.setMessage(message);
                }

                mProgressDialog.setCancelable(isCancelable);

                mProgressDialog.setIndeterminate(true);
            }

            if (!mProgressDialog.isShowing()) {
                mProgressDialog.show();
            }
        } catch (Exception ignored) {

        }
    }

    public static void hideProgressDialog() {
        try {
            if (mProgressDialog != null && mProgressDialog.isShowing()) {
                mProgressDialog.hide();
                mProgressDialog = null;
            }
        } catch (Exception ignored) {

        }
    }

    public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }

    public static void requestFocus(Activity activity, View view) {
        if (view.requestFocus()) {
            activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    //        Calendar calendar = Calendar.getInstance();
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
//        String currentDateandTime = sdf.format(new Date());

    @SuppressLint("SimpleDateFormat")
    public static Date formatDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH mm ss");
        try {
            return sdf.parse(date);
        } catch (ParseException ignored) {

        }
        return null;
    }

    @SuppressLint("SimpleDateFormat")
    public static String prettyDate(String str) {
        Date date = formatDate(str);
        SimpleDateFormat format = new SimpleDateFormat("dd MMMM yyyy");
        return format.format(date);
    }
}
