package dev.a11dev.trackapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Location;
import android.os.Build;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

public class TrackingService extends Service {

    private static final String TAG = "TRACKAPP";
    public static final String ACTION_COORDINATE_UPDATE = "dev.a11dev.trackapp.ACTION_COORDINATE_UPDATE";
    public static final String EXTRA_COORDINATE = "dev.a11dev.trackapp.EXTRA_COORDINATE";
    public static final String ACTION_STOP_TRACKING = "dev.a11dev.trackapp.ACTION_STOP_TRACKING";
    public static final String ACTION_START_TRACKING= "dev.a11dev.trackapp.ACTION_START_TRACKING";
    private FusedLocationProviderClient fusedLocationClient;
    private LocationCallback locationCallback;
    private BroadcastReceiver stopTrackingReceiver;
    private static final String CHANNEL_ID = "TrackingServiceChannel";

    @Override
    public void onCreate() {
        super.onCreate();

        // Crea e avvia il servizio come foreground
        createNotificationChannel();

        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent,  PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new Notification.Builder(this, CHANNEL_ID)
                .setContentTitle("Tracking Service")
                .setContentText("Tracking location in progress...")
                .setSmallIcon(android.R.drawable.ic_menu_mylocation) // Assicurati di avere un'icona appropriata
                .setContentIntent(pendingIntent)
                .build();

        // Avvia il servizio come foreground service
        startForeground(1, notification);
        // Inizializza il FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Crea e configura la richiesta di posizione
        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(10000) // Intervallo di 10 secondi per acquisire la posizione
                .setFastestInterval(10000) // Intervallo più veloce di 10 secondi
                .setMaxWaitTime(10000); // Massimo tempo di attesa tra un aggiornamento e l'altro

        // Crea il LocationCallback per ricevere aggiornamenti della posizione
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult != null) {
                    for (Location location : locationResult.getLocations()) {
                        // Gestisci la posizione
                        String coordinate = "Lat: " + location.getLatitude() + ", Lng: " + location.getLongitude();
                        Log.d(TAG, "Location update: " + coordinate);

                        // Crea un Intent di broadcast per inviare le coordinate
                        Intent intent = new Intent(ACTION_COORDINATE_UPDATE);
                        intent.putExtra(EXTRA_COORDINATE, coordinate);
                        sendBroadcast(intent);

                    }
                }
            }
        };

//        // Registra il BroadcastReceiver per lo stop del tracking
//        stopTrackingReceiver = new BroadcastReceiver() {
//            @Override
//            public void onReceive(Context context, Intent intent) {
//                if (intent != null && ACTION_STOP_TRACKING.equals(intent.getAction())) {
//                    // Interrompe la raccolta delle coordinate
//                    stopLocationUpdates();
//                }
//            }
//        };
//
//        IntentFilter filter = new IntentFilter(ACTION_STOP_TRACKING);
//        registerReceiver(stopTrackingReceiver, filter);

        // Richiedi gli aggiornamenti della posizione
        startLocationUpdates(locationRequest);
    }

    private void startLocationUpdates(LocationRequest locationRequest) {
        // Verifica che i permessi siano concessi prima di richiedere gli aggiornamenti
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == android.content.pm.PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Log.d(TAG, "Location update request successful");
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Log.e(TAG, "Location update request failed: " + e.getMessage());
                        }
                    });
        } else {
            Log.e(TAG, "Location permission not granted");
        }
    }

    // Metodo per fermare gli aggiornamenti della posizione
    private void stopLocationUpdates() {
        if (fusedLocationClient != null && locationCallback != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
            Log.d(TAG, "Location updates stopped");

            // Invia un Intent di Broadcast per aggiornare lo stato a STOP
//            Intent stopIntent = new Intent(MainActivity.STOP_DISPLAY_TRACKING);
//            sendBroadcast(stopIntent);


        }
        Intent intent = new Intent(MainActivity.STOP_DISPLAY_TRACKING);
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Rimuovi gli aggiornamenti della posizione e annulla la registrazione del BroadcastReceiver
        stopLocationUpdates();


    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Tracking Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            if (manager != null) {
                manager.createNotificationChannel(serviceChannel);
            }
        }
    }


}
