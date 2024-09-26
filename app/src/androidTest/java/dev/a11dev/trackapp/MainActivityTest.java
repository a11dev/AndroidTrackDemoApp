package dev.a11dev.trackapp;

import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Test
    public void testActivityReceivesBroadcastAndDisplaysCoordinates() {
        // Avvia la MainActivity
        ActivityScenario<MainActivity> scenario = ActivityScenario.launch(MainActivity.class);

        // Invia un broadcast simulato con una coordinata
        Intent intent = new Intent(TrackingService.ACTION_COORDINATE_UPDATE);
        intent.putExtra(TrackingService.EXTRA_COORDINATE, "Lat: 12.34, Lng: 56.78");
        scenario.onActivity(activity -> activity.sendBroadcast(intent));

        // Verifica che la RecyclerView contenga la coordinata
        onView(withId(R.id.coordinatesRecyclerView))
                .check(matches(withText("Lat: 12.34, Lng: 56.78")));
    }
}
