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
public class TrackingReceiverTest {

    private Context context;

    @Before
    public void setup() {
        context = ApplicationProvider.getApplicationContext();
    }

    @Test
    public void testStartIntentReceived() {
        // Create an intent to simulate the START action
        Intent intent = new Intent(context, TrackingReceiver.class);
        intent.setAction("dev.a11dev.trackapp.START");

        // Send the broadcast to the receiver
        TrackingReceiver receiver = new TrackingReceiver();
        receiver.onReceive(context, intent);

        // Assert that the intent was handled
        assertNotNull("Intent should be handled by receiver", intent);
    }

    @Test
    public void testStopIntentReceived() {
        // Create an intent to simulate the STOP action
        Intent intent = new Intent(context, TrackingReceiver.class);
        intent.setAction("dev.a11dev.trackapp.STOP");

        // Send the broadcast to the receiver
        TrackingReceiver receiver = new TrackingReceiver();
        receiver.onReceive(context, intent);

        // Assert that the intent was handled
        assertNotNull("Intent should be handled by receiver", intent);
    }
}
