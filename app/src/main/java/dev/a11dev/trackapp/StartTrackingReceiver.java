package dev.a11dev.trackapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.net.Uri;

public class StartTrackingReceiver extends BroadcastReceiver {

    private static final String TAG = "TRACKAPP";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && TrackingService.ACTION_START_TRACKING.equals(intent.getAction())) {
            Log.d(TAG, "Start intent received in static receiver");
            // Avvia la logica di stop del TrackingService
            String action = intent.getAction();
            Uri dataUri = intent.getData();

            if ("dev.a11dev.trackapp.ACTION_STOP_TRACKING".equals(action) && dataUri != null) {
                Log.d(TAG, "Received Stop Tracking Broadcast with URI: " + dataUri.toString());

                // Handle the URI accordingly
                // For example, stop tracking and log the received URI
                // Custom handling code here...
            } else {
                Log.d(TAG, "Stop Tracking Broadcast received without a URI");
            }
            Intent serviceIntent = new Intent(context, TrackingService.class);
            context.startService(serviceIntent);

        }
    }
}
