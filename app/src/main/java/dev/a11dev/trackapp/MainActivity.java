package dev.a11dev.trackapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "TRACKAPP";
    private TextView parametersTextView;
    private TextView statusTextView;
    private RecyclerView coordinatesRecyclerView;
    private CoordinateAdapter coordinateAdapter;
    private List<String> coordinatesList;
    private Button startButton;
    private boolean isTracking = false;

    // BroadcastReceiver per intercettare gli aggiornamenti delle coordinate
    private final BroadcastReceiver coordinateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                // Aggiorna lo stato a STOP quando viene ricevuto l'Intent corrispondente
                if (TrackingService.ACTION_STOP_TRACKING.equals(intent.getAction())) {
                    updateTrackingStatus(false);
                } else if (TrackingService.ACTION_COORDINATE_UPDATE.equals(intent.getAction())) {
                    // Estrai la coordinata dall'Intent
                    String coordinate = intent.getStringExtra(TrackingService.EXTRA_COORDINATE);
                    if (coordinate != null) {
                        Log.d(TAG, "Coordinate received: " + coordinate);
                        // Aggiungi la coordinata alla lista
                        coordinatesList.add(coordinate);
                        // Notifica l'adapter che i dati sono cambiati
                        coordinateAdapter.notifyDataSetChanged();
                    }
                }
            }
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inizializza la TextView per i parametri della URL
        parametersTextView = findViewById(R.id.parametersTextView);
        statusTextView = findViewById(R.id.statusTextView);

        // Inizializza la RecyclerView e la lista di coordinate
        coordinatesRecyclerView = findViewById(R.id.coordinatesRecyclerView);
        coordinatesList = new ArrayList<>();
        coordinateAdapter = new CoordinateAdapter(coordinatesList);

        // Configura la RecyclerView
        coordinatesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        coordinatesRecyclerView.setAdapter(coordinateAdapter);

        startButton = findViewById(R.id.startButton);

        // Registra il BroadcastReceiver per ascoltare gli Intent ACTION_COORDINATE_UPDATE
        IntentFilter filter = new IntentFilter(TrackingService.ACTION_COORDINATE_UPDATE);
        registerReceiver(coordinateReceiver, filter);



        // Recupera l'Intent che ha avviato l'Activity
        Intent intent = getIntent();
        String action = intent.getAction();

        // Imposta l'OnClickListener per il Button
        startButton.setOnClickListener(v -> startTracking());

        Uri data = intent.getData(); // Ottiene l'URI dall'Intent

        if (data != null && (Intent.ACTION_VIEW.equals(action) || "dev.a11dev.trackapp.ACTION_START_TRACKING".equals(action))) {
            // Visualizza i parametri della URL ricevuti
            displayUrlParameters(data);
            startTracking();
        } else {
            // Crea un Intent con parametri predefiniti
            Uri defaultUri = new Uri.Builder()
                    .scheme("https")
                    .authority("toftrackapp")
                    .path("/track")
                    .appendQueryParameter("id", "123")
                    .appendQueryParameter("action", "none")
                    .build();
            displayUrlParameters(defaultUri);
        }


        // Imposta l'OnClickListener per il Button
        startButton.setOnClickListener(v -> {
            if (isTracking) {
                stopTracking();
            } else {
                startTracking();
            }
        });

    }

    private void displayUrlParameters(Uri data) {
        if (data != null) {
            StringBuilder displayText = new StringBuilder();
            displayText.append("Action: ").append(getIntent().getAction()).append("\n");
            displayText.append("Host: ").append(data.getHost()).append("\n");
            displayText.append("Path: ").append(data.getPath()).append("\n");

            // Visualizza tutti i parametri della query
            for (String paramName : data.getQueryParameterNames()) {
                String paramValue = data.getQueryParameter(paramName);
                displayText.append(paramName).append(": ").append(paramValue).append("\n");
            }

            // Imposta il testo sulla TextView
            parametersTextView.setText(displayText.toString());
        }
    }

    private void startTracking() {
        // Cambia lo stato della raccolta coordinate
        isTracking = true;
        statusTextView.setText("Status: RUN");

        // Crea un Intent per avviare il TrackingService
        Intent serviceIntent = new Intent(this, TrackingService.class);
        startService(serviceIntent);
    }

    private void stopTracking() {
        // Cambia lo stato della raccolta coordinate
        isTracking = false;
        statusTextView.setText("Status: STOP");

        // Ferma il TrackingService (implementare il codice di stop nel servizio)
        Intent stopIntent = new Intent(this, TrackingService.class);
        stopService(stopIntent);
    }

    // Aggiorna lo stato di tracking
    private void updateTrackingStatus(boolean tracking) {
        isTracking = tracking;
        if (tracking) {
            statusTextView.setText("Status: RUN");
        } else {
            statusTextView.setText("Status: STOP");
            // Pulisci la lista delle coordinate
            coordinatesList.clear();
            coordinateAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Annulla la registrazione del BroadcastReceiver
        unregisterReceiver(coordinateReceiver);
    }
}
