# Android GPS Coordinate Collection Test App

## Overview
This Android application is designed as a test tool, intended to be activated via an external application through an Intent. The primary function of this app is to collect GPS coordinates and display them in a table on the main screen. The app will react to specific parameters sent through the Intent, and handle both `START` and `STOP` actions to control its behavior.

## Features
- **Intent-based Activation**: The app can be triggered by an external Intent that contains URI parameters. These parameters are displayed above the main table on the app's UI.
- **GPS Coordinate Collection**: Once activated, a button is presented to start collecting GPS coordinates. These coordinates are continuously added to a table on the main page.
- **Background Operation**: The app supports background and foreground operations, meaning it can keep collecting GPS coordinates even when not in focus.
- **Intent Handling for START/STOP Actions**:
  - When an Intent with `action=START` is received, the app starts collecting GPS coordinates.
  - If an Intent with `action=STOP` is received, the app halts the coordinate collection process without returning to the foreground.
- **Notifications**: A notification is shown to the user when either `START` or `STOP` action is received, providing feedback on the status of the GPS collection.
- **Basic Logging**: A basic log is maintained and can be accessed via `logcat` for debugging purposes.

## Prerequisites
- Android SDK installed.
- A device or emulator set up for testing with USB Debugging enabled.

## Debugging the App
To monitor the app's behavior and collect logs for debugging, follow these steps:

1. **Enable USB Debugging on the Device**:
   - Go to `Settings` > `About phone`.
   - Tap on `Build number` 7 times to unlock developer options.
   - Go back to `Settings`, find `Developer options`, and enable `USB Debugging`.

2. **Connect Device and Launch ADB**:
   - Connect your Android device to your computer.
   - In your terminal, run:
     ```bash
     adb devices
     ```
     Ensure your device is listed as connected.

3. **Filtering Logs with `logcat`**:
   - To view logs specific to the app, use:
     ```bash
     adb logcat | grep "YOUR_APP_TAG"
     ```
     Replace `YOUR_APP_TAG` with a unique tag you have added to your app's logs (e.g., "GPSApp").

4. **Starting and Stopping GPS Collection**:
   - When sending an Intent to the app for testing:
     - `action=START` will initiate GPS data collection.
     - `action=STOP` will terminate GPS collection without bringing the app to the foreground.

## Usage
1. **Launch the App via Intent**: 
   The app is designed to be started by an external Intent, which includes the necessary URI parameters and `action`. 

2. **UI Components**:
   - **Start Button**: Appears on the first activation to allow the user to begin GPS collection.
   - **Coordinate Table**: Displays collected GPS data in real time.
   - **Parameter Display**: Shows parameters received via the Intent above the coordinate table.

3. **Receiving and Handling Intents in Background**:
   The app remains responsive to external Intents even when running in the background. Upon receiving a `START` or `STOP` Intent, the app will process the action accordingly.

## Example `logcat` Filter
To see only the logs related to the GPS collection process:
```bash
adb logcat | grep "GPS_COLLECTION"

