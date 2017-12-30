package co.dust.smspasuruan.firebase;


import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import co.dust.smspasuruan.BuildConfig;
import co.dust.smspasuruan.tools.APIService;
import co.dust.smspasuruan.tools.CredentialStorage;
import co.dust.smspasuruan.tools.ServiceFactory;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by irsal on 7/15/16.
 */


public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    private static final String TAG = MyFirebaseInstanceIDService.class.getSimpleName();

    private CredentialStorage credentialStorage;

    @Override
    public void onTokenRefresh() {
        credentialStorage = new CredentialStorage(getApplicationContext());
        //Getting registration token
        String refreshedTokenFCM = FirebaseInstanceId.getInstance().getToken();
        sendRegistrationToServer(refreshedTokenFCM);

        //Displaying token on logcat
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "Refreshed token : " + refreshedTokenFCM);
        }

    }

    private void sendRegistrationToServer(String tokenFCM) {
        //You can implement this method to store the tokenFCM on your server
        //Not required for current project

        String myToken = credentialStorage.getFCMToken();
        String userId = credentialStorage.getUserId();

        if (credentialStorage.isAlreadyLogin()) {
            APIService apiService = ServiceFactory.createRetrofitService(APIService.class, true);
            apiService.updateFCMToken(userId, tokenFCM).enqueue(new Callback<APIService.Message>() {
                @Override
                public void onResponse(Call<APIService.Message> call, Response<APIService.Message> response) {

                }

                @Override
                public void onFailure(Call<APIService.Message> call, Throwable t) {

                }
            });

        }

    }
}