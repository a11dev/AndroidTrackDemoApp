package dev.a11dev.trackapp;

import android.content.Intent;
import android.net.Uri;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.core.app.ApplicationProvider.getApplicationContext;
import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class IntentExternalLaunchTest {

    @Test
    public void testExternalIntentLaunchesActivity() {
        // Crea l'Intent simulando un Intent esterno con l'URI specificato
        Intent externalIntent = new Intent(Intent.ACTION_VIEW);
        externalIntent.setData(Uri.parse("https://toftrackapp/track"));

        // Avvia l'Activity con il nuovo Intent e verifica che venga lanciata
        try (ActivityScenario<MainActivity> scenario = ActivityScenario.launch(externalIntent)) {
            scenario.onActivity(activity -> {
                // Verifica che l'Activity sia stata avviata con successo
                assertNotNull(activity);

                // Verifica ulteriori comportamenti, ad esempio controllando i dati ricevuti
                Uri data = activity.getIntent().getData();
                assertNotNull(data);
                assertNotNull(data.getHost());
                assertNotNull(data.getPath());

                // Verifica che il percorso e l'host corrispondano a quelli previsti
                assert(data.getHost().equals("toftrackapp"));
                assert(data.getPath().equals("/track"));
            });
        }
    }
}
