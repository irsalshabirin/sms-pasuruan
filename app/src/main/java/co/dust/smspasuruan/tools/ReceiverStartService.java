package co.dust.smspasuruan.tools;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import co.dust.smspasuruan.firebase.MyFirebaseInstanceIDService;
import co.dust.smspasuruan.firebase.MyFirebaseMessagingService;


/**
 * Created by irsal on 7/31/16.
 */
public class ReceiverStartService extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equalsIgnoreCase(Intent.ACTION_BOOT_COMPLETED)) {
            Intent myIntent1 = new Intent(context, MyFirebaseInstanceIDService.class);
            context.startService(myIntent1);

            Intent myIntent2 = new Intent(context, MyFirebaseMessagingService.class);
            context.startService(myIntent2);
        }
//        Intent i = new Intent(context, MyAutoStartActivity.class);
//        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);

    }
}
