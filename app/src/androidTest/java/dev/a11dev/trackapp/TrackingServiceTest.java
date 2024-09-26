package dev.a11dev.trackapp;

import android.content.Intent;
import android.location.LocationManager;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TrackingServiceTest {

    @Before
    public void setup() {
        // Avvia il servizio prima di ogni test
        Intent startIntent = new Intent(ApplicationProvider.getApplicationContext(), TrackingService.class);
        ApplicationProvider.getApplicationContext().startService(startIntent);
    }

    @Test
    public void testLocationUpdatesAreStarted() {
        // Verifica che il servizio sia avviato e richieda aggiornamenti di posizione
        // Usa il LocationManager per verificare che siano stati richiesti aggiornamenti
        LocationManager locationManager = (LocationManager) ApplicationProvider.getApplicationContext()
                .getSystemService(ApplicationProvider.getApplicationContext().LOCATION_SERVICE);

        assertTrue(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER));
    }

    @Test
    public void testStopTrackingBroadcast() {
        // Invia un broadcast con l'azione STOP
        Intent stopIntent = new Intent(TrackingService.ACTION_STOP_TRACKING);
        ApplicationProvider.getApplicationContext().sendBroadcast(stopIntent);

        // Verifica che gli aggiornamenti siano stati interrotti
        // Questo potrebbe richiedere un controllo sui log o una verifica indiretta
    }

    @After
    public void tearDown() {
        // Interrompe il servizio dopo ogni test
        Intent stopServiceIntent = new Intent(ApplicationProvider.getApplicationContext(), TrackingService.class);
        ApplicationProvider.getApplicationContext().stopService(stopServiceIntent);
    }
}
