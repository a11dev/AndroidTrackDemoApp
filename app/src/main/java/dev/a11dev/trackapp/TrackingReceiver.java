package dev.a11dev.trackapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class TrackingReceiver extends BroadcastReceiver {

    private static final String TAG = "TRACKAPP";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && intent.getAction() != null) {
            String action = intent.getAction();
            Log.d(TAG, "TrackingReceiver received action: " + action);

            // Start or stop the tracking service based on the received action
            Intent serviceIntent = new Intent(context, TrackingService.class);
            serviceIntent.setAction(action);
            context.startService(serviceIntent);
        }
    }
}
