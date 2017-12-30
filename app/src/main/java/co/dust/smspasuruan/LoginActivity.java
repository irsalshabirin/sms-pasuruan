package co.dust.smspasuruan;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import co.dust.smspasuruan.adapter.CustomSpinnerAdapter;
import co.dust.smspasuruan.tools.APIService;
import co.dust.smspasuruan.tools.CommonConstants;
import co.dust.smspasuruan.tools.CredentialStorage;
import co.dust.smspasuruan.tools.ServiceFactory;
import co.dust.smspasuruan.tools.StaticHelper;
import co.dust.smspasuruan.view.MyBetterSpinner;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by irsal on 6/5/17.
 */

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();

    private APIService apiService = ServiceFactory.createRetrofitService(APIService.class, true);

    private CredentialStorage credentialStorage;

    @BindView(R.id.til_username)
    TextInputLayout tilUsername;

    @BindView(R.id.et_username)
    EditText etUsername;

    private boolean isValidUsername() {
        if (etUsername.getText().toString().equals("")) {
            tilUsername.setError(getString(R.string.must_be_filled));
            tilUsername.setErrorEnabled(true);
            StaticHelper.requestFocus(this, etUsername);
            return false;
        } else {
            tilUsername.setError("");
            tilUsername.setErrorEnabled(false);
        }
        return true;
    }

    @BindView(R.id.til_password)
    TextInputLayout tilPassword;


    @BindView(R.id.et_password)
    EditText etPassword;

    private boolean isValidPassword() {
        if (etPassword.getText().toString().equals("")) {
            tilPassword.setError(getString(R.string.must_be_filled));
            tilPassword.setErrorEnabled(true);
            StaticHelper.requestFocus(this, etPassword);
            return false;
        } else {
            tilPassword.setError("");
            tilPassword.setErrorEnabled(false);
        }
        return true;
    }

    private CustomSpinnerAdapter customSpinnerAdapter;

    @BindView(R.id.mbs_year)
    MyBetterSpinner mbsYear;

    @BindView(R.id.btn_login)
    AppCompatButton btnLogin;

    private boolean fromNotification = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        credentialStorage = new CredentialStorage(this);

        handleNotification();

        if (credentialStorage.isAlreadyLogin()) {
            Intent intent = new Intent(this, IncomingMailActivity2.class);
            startActivity(intent);
            finish();
        } else {
            initYearAdapter();
        }

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidAllForm()) {
                    submitForm();
                }


            }
        });

        mbsYear.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)));
    }

    private void handleNotification() {
        // If a notification message is tapped, any data accompanying the notification
        // message is available in the intent extras. In this sample the launcher
        // intent is fired when the notification is tapped, so any accompanying data would
        // be handled here. If you want a different intent fired, set the click_action
        // field of the notification message to the desired intent. The launcher intent
        // is used when no click_action is specified.
        //
        // Handle possible data accompanying notification message.
        // [START handle_data_extras]
//        String nomor = "";
//        String perihal = "";
        String suratMasukId = "";

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {

                String value = String.valueOf(getIntent().getExtras().get(key));

                if (BuildConfig.DEBUG) {
                    Log.e(TAG, "Key: " + key + " Value: " + value);
                }

                switch (key) {
//                    case MyFirebaseMessagingService.NOMOR:
//                        fromNotification = true;
//                        nomor = String.valueOf(value);
//                        break;
//                    case MyFirebaseMessagingService.PERIHAL:
//                        fromNotification = true;
//                        perihal = String.valueOf(value);
//                        break;

                    case CommonConstants.TAG_SURAT_MASUK_ID:
                        fromNotification = true;
                        suratMasukId = String.valueOf(value);
                        break;
                }
            }
        }
        // [END handle_data_extras]

        if (fromNotification) {
            if (credentialStorage.isAlreadyLogin()) {
                showNotification(suratMasukId);
                finish();
            }
        }
    }

    private void showNotification(String suratMasukId) {

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());

        if (BuildConfig.DEBUG) {
            Log.e(TAG, "handle notification");
        }

        Intent resultIntent;

        if (BuildConfig.DEBUG) {
            Log.e(TAG, "handle notification");
        }


        if (BuildConfig.DEBUG) {
            Log.e(TAG, "handle notification");
        }

        resultIntent = new Intent(this, DetailIncomingMailActivity.class);
        resultIntent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        Bundle bundle = new Bundle();
        bundle.putString(CommonConstants.TAG_SURAT_MASUK_ID, suratMasukId);
        resultIntent.putExtras(bundle);

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
        stackBuilder.addNextIntent(intent);
        stackBuilder.addNextIntent(resultIntent);

        stackBuilder.startActivities();


    }

    private void initYearAdapter() {
        StaticHelper.showProgressDialog(this, null, getString(R.string.loading), false);

        apiService.getYear().enqueue(new Callback<APIService.YearArrResponse>() {
            @Override
            public void onResponse(Call<APIService.YearArrResponse> call, Response<APIService.YearArrResponse> response) {
                StaticHelper.hideProgressDialog();
                if (response.isSuccessful()) {
                    customSpinnerAdapter = new CustomSpinnerAdapter(getApplicationContext(), response.body().years);
//                    customSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
                    mbsYear.setAdapter(customSpinnerAdapter);
                }
            }

            @Override
            public void onFailure(Call<APIService.YearArrResponse> call, Throwable t) {
                StaticHelper.hideProgressDialog();
                Toast.makeText(LoginActivity.this, "failed to fetch data", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }

    private void submitForm() {

        StaticHelper.showProgressDialog(this, null, getString(R.string.loading), false);

        apiService.login(
                etUsername.getText().toString(),
                etPassword.getText().toString()
        ).enqueue(new Callback<APIService.UserResponse>() {
            @Override
            public void onResponse(Call<APIService.UserResponse> call, Response<APIService.UserResponse> response) {
                StaticHelper.hideProgressDialog();
                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (BuildConfig.DEBUG) {
                            Log.e(TAG, "response body not null");
                        }

                        if (!response.body().error) {
                            Intent intent = new Intent(LoginActivity.this, IncomingMailActivity2.class);

                            if (response.body().user != null) {
                                credentialStorage.setUserId(String.valueOf(response.body().user.getUserLoginID()));

                                credentialStorage.setSatkerId(response.body().user.getSatKerID());
                                credentialStorage.setSatkerName(response.body().user.getSatKer());

                                credentialStorage.setName(response.body().user.getNamaUser());
                                credentialStorage.setYear(mbsYear.getText().toString());

                                if (BuildConfig.DEBUG) {
                                    Log.e(TAG, "userId  : " + response.body().user.getUserLoginID());
                                    Log.e(TAG, "name : " + response.body().user.getNamaUser());
                                    Log.e(TAG, "satkerId : " + response.body().user.getSatKerID());
                                }
                                String refreshedToken = FirebaseInstanceId.getInstance().getToken();
                                credentialStorage.setFCMToken(refreshedToken);

                                doUpdateFCM(refreshedToken, credentialStorage.getUserId());


                                credentialStorage.setIsAlreadyLogin(true);
                                startActivity(intent);
                                finish();
                            }

                        } else {

                            if (BuildConfig.DEBUG) {
                                Log.e(TAG, "message : " + response.body().message);
                            }

                            Toast.makeText(LoginActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    if (BuildConfig.DEBUG) {
                        Log.e(TAG, "response is not successfull");
                    }
                }
            }

            @Override
            public void onFailure(Call<APIService.UserResponse> call, Throwable t) {
                StaticHelper.hideProgressDialog();
            }
        });
    }

    private void doUpdateFCM(String token, String userId) {
        apiService.updateFCMToken(userId, token).enqueue(new Callback<APIService.Message>() {
            @Override
            public void onResponse(Call<APIService.Message> call, Response<APIService.Message> response) {

            }

            @Override
            public void onFailure(Call<APIService.Message> call, Throwable t) {

            }
        });

    }

    private boolean isValidAllForm() {
        return isValidUsername() &&
                isValidPassword();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
