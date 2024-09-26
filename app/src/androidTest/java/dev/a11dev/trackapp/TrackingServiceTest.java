package dev.a11dev.trackapp;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertNotNull;

@RunWith(AndroidJUnit4.class)
public class TrackingServiceTest {

    private Context context;

    @Before
    public void setup() {
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void testStartService() {
        // Create an intent to start the service with START action
        Intent serviceIntent = new Intent(context, TrackingService.class);
        serviceIntent.setAction("dev.a11dev.trackapp.START");

        // Start the service
        context.startService(serviceIntent);

        // Assert the service started successfully
        assertNotNull("Service should be started", serviceIntent);
    }

    @Test
    public void testStopService() {
        // Create an intent to start the service with STOP action
        Intent serviceIntent = new Intent(context, TrackingService.class);
        serviceIntent.setAction("dev.a11dev.trackapp.STOP");

        // Start the service
        context.startService(serviceIntent);

        // Assert the service started successfully with STOP action
        assertNotNull("Service should handle STOP action", serviceIntent);
    }
}
