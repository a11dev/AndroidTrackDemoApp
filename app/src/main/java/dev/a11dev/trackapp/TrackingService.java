package dev.a11dev.trackapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

public class TrackingService extends Service {

    private static final String TAG = "TRACKAPP";
    private static final String CHANNEL_ID = "TrackAppChannel";
    private LocationManager locationManager;

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null) {
            String action = intent.getAction();
            if ("dev.a11dev.trackapp.START".equals(action)) {
                startGpsTracking();
                showNotification("GPS Tracking Started");
            } else if ("dev.a11dev.trackapp.STOP".equals(action)) {
                stopGpsTracking();
                showNotification("GPS Tracking Stopped");
            }
        }
        return START_STICKY;
    }

    private void startGpsTracking() {
        Log.d(TAG, "TrackingService: GPS tracking started.");
        try {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    5000,
                    10,
                    locationListener);
        } catch (SecurityException e) {
            Log.e(TAG, "Permission error for location updates", e);
        }
    }

    private void stopGpsTracking() {
        Log.d(TAG, "TrackingService: GPS tracking stopped.");
        locationManager.removeUpdates(locationListener);
        stopSelf();
    }

    private final LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.d(TAG, "Location update: " + location.getLatitude() + ", " + location.getLongitude());
            // Handle location update (e.g., add to table or log data).
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) { }

        @Override
        public void onProviderEnabled(String provider) { }

        @Override
        public void onProviderDisabled(String provider) { }
    };

    private void showNotification(String message) {
        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("TrackApp")
                .setContentText(message)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .build();

        startForeground(1, notification);
    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(
                CHANNEL_ID,
                "TrackApp Notifications",
                NotificationManager.IMPORTANCE_DEFAULT);
        NotificationManager manager = getSystemService(NotificationManager.class);
        manager.createNotificationChannel(channel);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
