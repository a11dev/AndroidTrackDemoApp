package dev.a11dev.trackapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class StopTrackingReceiver extends BroadcastReceiver {

    private static final String TAG = "TRACKAPP";

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null && TrackingService.ACTION_STOP_TRACKING.equals(intent.getAction())) {
            Log.d(TAG, "Stop intent received in static receiver");
            // Avvia la logica di stop del TrackingService

            Intent serviceIntent = new Intent(context, TrackingService.class);
            context.stopService(serviceIntent);
        }
    }
}
