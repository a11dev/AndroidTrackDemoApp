# Android GPS Tracking App

## Overview
An Android application designed to track GPS coordinates and respond to external Intents (`START`/`STOP`). It showcases real-time tracking data in a user-friendly table on the main screen. The app leverages foreground and background service operations to ensure that GPS tracking remains active, with updates delivered to the UI via broadcast Intents.

### Main Components
- **TrackingService**: A foreground service that collects GPS coordinates and sends broadcast Intents to update `MainActivity`.
- **MainActivity**: Manages the user interface, controls tracking, and receives updates through `BroadcastReceivers`.

---

## Application Behavior
### 1. TrackingService
- **Foreground GPS Collection**: The service gathers GPS coordinates while running in the foreground, ensuring continuous operation.
- **Broadcasting Updates**: Sends Intents to `MainActivity` for updating the coordinates table or stopping display upon receiving a `STOP` action.
- **Lifecycle Methods**:
  - `onCreate()`: Initializes resources for GPS tracking.
  - `onStartCommand()`: Handles the start request via explicit Intents.
  - `onDestroy()`: Stops location updates and releases resources.

### 2. Intent Filters & BroadcastReceivers
- **Dynamic Receivers in MainActivity**: Registered within `onCreate()` to handle specific actions (`START_TRACKING`, `STOP_TRACKING`, `COORDINATE_UPDATE`).
  - Allows dynamic control over tracking behavior.
  - Supports multiple `IntentFilter`s for handling different actions.
- **Explicit Intents for Reliable Communication**: Ensures that Intents reach their destination, especially necessary on Android 8.0+ due to restrictions on implicit Intents.

### 3. Service and UI Interaction
- **Starting the Service**: An explicit Intent from `MainActivity` starts the `TrackingService`.
- **Broadcast Updates**: The `MainActivity` receives state updates (coordinate changes, tracking status) via a dynamically registered `BroadcastReceiver`.
- **Stopping Data Display**: On receiving a `STOP` Intent, `TrackingService` halts coordinate collection without bringing the app back to the foreground.

### 4. AndroidManifest Configuration
- **Permissions**: Declares required permissions for location access:
  - `ACCESS_FINE_LOCATION`
  - `ACCESS_BACKGROUND_LOCATION`
  - `FOREGROUND_SERVICE`
- **TrackingService Declaration**: Defined with `android:foregroundServiceType="location"` for GPS functionality.
- **BroadcastReceiver Management**:
  - **Dynamic Receivers**: Used for handling Intents at runtime.
  - **Static Receivers**: Optionally declared in the manifest but limited for implicit Intents on Android 8.0+.

### 5. Broadcasting & Receiving Intents
The app’s control is primarily managed by Intents:
- **Start/Stop Receivers**: `StartTrackingReceiver` and `StopTrackingReceiver` handle the `START` and `STOP` actions.
- **Foreground Operation**: `TrackingService` uses `startForeground()` to persist and run the service actively.

### 6. Managing BroadcastReceivers
- **Dynamic Registration**: Receivers are dynamically registered in `onCreate()` and unregistered in `onDestroy()` of `MainActivity`.
- **Static Receivers**: Registered in `AndroidManifest.xml`, they are automatically managed by the system but face limitations for implicit Intents on Android 8.0+.

### 7. Handling Implicit Intent Limitations
Since Android 8.0, restrictions on implicit Intents require:
- **Explicit Intents**: Utilize `setClass()` or `setPackage()` methods to ensure proper targeting of `BroadcastReceiver`.
- **Explicit Intent Benefits**: Improves compatibility and ensures that the right receiver is triggered.

### 8. User Interface Behavior
- **On `START` Intent**:
  - A button is displayed in `MainActivity` to initiate GPS tracking.
  - Collected coordinates are shown in a table.
  - URI parameters from the `START` Intent are displayed above the table.
- **On `STOP` Intent**:
  - Tracking is stopped, but the app remains in the background.
  - A notification is sent to inform the user without forcing the app to the foreground.

---

## Notifications
The app provides notifications to alert the user:
- **Tracking Started**: A notification appears when the `START` action is received.
- **Tracking Stopped**: A notification is shown upon receiving a `STOP` action.

## Logging
The app uses detailed logging to verify the flow of actions and correct data handling:
- **TrackingService Logs**: Tracks coordinate collection, Intent processing, and service status.
- **MainActivity Logs**: Captures user interactions, UI updates, and received Intents.

Logs are accessible through `logcat` for debugging:
```bash
adb logcat -s TrackingService MainActivity


### Start Tracking
```bash
adb shell am broadcast -a dev.a11dev.trackapp.ACTION_START_TRACKING -n "dev.a11dev.trackapp/.StartTrackingReceiver"

### Stop Tracking
```bash
adb shell am broadcast -a dev.a11dev.trackapp.ACTION_STOP_TRACKING -n "dev.a11dev.trackapp/.StopTrackingReceiver"
