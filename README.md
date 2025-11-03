# 4DD Android App

An Android WebView application for [4dd.ro](https://4dd.ro) with Firebase Cloud Messaging (FCM) push notifications support.

> **Status**: App is built and ready for Google Play Store submission. See [INFO.md](INFO.md) for current status and remaining tasks.

## Overview

This is a native Android app that displays the 4dd.ro website within a WebView and supports push notifications through Firebase Cloud Messaging. The app is designed to be published on Google Play Store under the December Capital developer account.

**Current Version**: 1.0.0 (Release AAB available at `app/release/app-release.aab`)

## Features

- Native Android WebView displaying 4dd.ro
- Full web functionality support (links, text, embedded YouTube videos)
- Pull-to-refresh functionality
- Firebase Cloud Messaging (FCM) push notifications
- Deep linking from notifications
- Proper back button navigation
- Optimized for Android 7.0+ (API 24+)

## Technical Details

- **Package Name**: `com.decembercapital.furdichdeutsch.app`
- **Min SDK**: API 24 (Android 7.0)
- **Target SDK**: API 34 (Android 14)
- **Language**: Kotlin
- **Build System**: Gradle 8.2

## Prerequisites

Before building the app, ensure you have:

1. **Android Studio** (latest stable version recommended)
   - Download from: https://developer.android.com/studio

2. **JDK 8 or higher**

3. **Firebase Project** setup (see Firebase Setup section below)

## Project Structure

```
4dd-android-app/
├── app/
│   ├── src/main/
│   │   ├── java/com/decembercapital/furdichdeutsch/app/
│   │   │   ├── MainActivity.kt                    # Main activity with WebView
│   │   │   ├── CustomWebViewClient.kt             # WebView navigation handler
│   │   │   ├── CustomWebChromeClient.kt           # WebView chrome features
│   │   │   ├── FirebaseMessagingService.kt        # FCM message handler
│   │   │   ├── NotificationHelper.kt              # Notification utilities
│   │   │   └── FourDDApplication.kt               # Application class
│   │   ├── res/                                   # Android resources
│   │   └── AndroidManifest.xml                    # App manifest
│   ├── build.gradle.kts                           # App-level build config
│   └── google-services.json                       # Firebase config (ADD THIS!)
├── build.gradle.kts                               # Project-level build config
├── settings.gradle.kts                            # Project settings
└── README.md                                      # This file
```

## Setup Instructions

### 1. Clone or Download the Project

```bash
cd /path/to/4dd-android-app
```

### 2. Firebase Setup

**IMPORTANT**: You must complete Firebase setup before building the app.

#### Step 2.1: Create Firebase Project

1. Go to [Firebase Console](https://console.firebase.google.com/)
2. Click "Add project" or "Create a project"
3. Enter project name: "4DD Android App" (or your preferred name)
4. Follow the setup wizard (enable Google Analytics if desired)

#### Step 2.2: Register Android App

1. In Firebase Console, click "Add app" → Select Android icon
2. Enter the following details:
   - **Android package name**: `com.decembercapital.furdichdeutsch.app`
   - **App nickname**: "4DD" (optional)
   - **Debug signing certificate SHA-1**: (optional for now, required for some features)
3. Click "Register app"

#### Step 2.3: Download google-services.json

1. Download the `google-services.json` file
2. Place it in the `app/` directory of this project:
   ```
   4dd-android-app/app/google-services.json
   ```
3. **DO NOT commit this file to version control** (it's in .gitignore)

#### Step 2.4: Enable Cloud Messaging

1. In Firebase Console, go to Project Settings → Cloud Messaging
2. Under "Cloud Messaging API (Legacy)", note your **Server Key**
3. Save this key securely - you'll need it for your backend server

### 3. Open in Android Studio

1. Launch Android Studio
2. Select "Open an Existing Project"
3. Navigate to the `4dd-android-app` folder
4. Click "OK"
5. Wait for Gradle sync to complete

### 4. Build the Project

```bash
# Debug build
./gradlew assembleDebug

# Release build (for production)
./gradlew assembleRelease
```

Or use Android Studio:
- **Debug**: Click "Run" button (green play icon)
- **Release**: Build → Generate Signed Bundle/APK

### 5. Test on Device/Emulator

1. Connect an Android device with USB debugging enabled, OR
2. Create an Android Virtual Device (AVD) in Android Studio
3. Click "Run" in Android Studio
4. Select your device/emulator

## Firebase Cloud Messaging Integration

### Getting FCM Token

The app automatically retrieves the FCM token on startup. You can find it in the Android Logcat:

```
Tag: FourDDApplication
Message: FCM Token: [your-device-token]
```

### Backend Integration

Your hosting server needs to send POST requests to FCM to deliver push notifications.

#### HTTP v1 API (Recommended)

```bash
POST https://fcm.googleapis.com/v1/projects/YOUR_PROJECT_ID/messages:send
Content-Type: application/json
Authorization: Bearer YOUR_ACCESS_TOKEN

{
  "message": {
    "token": "DEVICE_FCM_TOKEN",
    "notification": {
      "title": "Notification Title",
      "body": "Notification message"
    },
    "data": {
      "url": "https://4dd.ro/specific-page"
    }
  }
}
```

#### Legacy API

```bash
POST https://fcm.googleapis.com/fcm/send
Content-Type: application/json
Authorization: key=YOUR_SERVER_KEY

{
  "to": "DEVICE_FCM_TOKEN",
  "notification": {
    "title": "Notification Title",
    "body": "Notification message"
  },
  "data": {
    "url": "https://4dd.ro/specific-page"
  }
}
```

### Notification Features

- Notifications appear in the system tray
- Tapping a notification opens the app
- If a `url` is provided in the data payload, the app opens to that specific URL
- Notifications work when app is in foreground, background, or closed

## Building for Production

> **Note**: Production build is already complete. Release AAB is available at `app/release/app-release.aab`

### Release Build Information

**✓ Completed Steps:**
- Signing keystore created (via keytool command)
- Release AAB signed and built
- Firebase integration configured
- Push notifications tested and working

**Keystore Details:**
- Alias: `4dd`
- Algorithm: RSA 2048-bit
- Validity: 25+ years
- **Location**: User-specified (MUST BE BACKED UP!)

**AAB Location:**
```
app/release/app-release.aab
```

### Building New Versions (For Future Updates)

To build a new release after making changes:

1. Update version in `app/build.gradle.kts`:
```kotlin
versionCode = 2  // Increment for each release
versionName = "1.0.1"  // Update version number
```

2. Build new AAB via Android Studio:
   - Build → Generate Signed Bundle/APK
   - Select existing keystore
   - Choose "release" build variant

3. Or via command line:
```bash
./gradlew bundleRelease
```

## Google Play Store Publishing

### Pre-Publishing Checklist

**✓ Completed:**
- [x] Add custom app icon (customized by user)
- [x] Test on emulator (Medium Phone API 36.1)
- [x] Test push notifications end-to-end (working)
- [x] Create privacy policy (`privacy-policy.html` - compliant with GDPR/CCPA)
- [x] Build signed release AAB (`app/release/app-release.aab`)
- [x] Create Google Play Developer account (Individual - December Capital)

**Remaining:**
- [ ] Upload privacy policy to hosting (december-capital.com/4dd/privacy-policy.html)
- [ ] Take screenshots (minimum 2 required - use emulator camera icon)
- [ ] Prepare feature graphic (1024x500px)
- [ ] Prepare Play Store app icon (512x512px)
- [ ] Write short description (80 chars max)
- [ ] Write full description

### Publishing Steps

1. Upload `privacy-policy.html` to your hosting and verify URL works
2. Take app screenshots from emulator
3. Go to [Google Play Console](https://play.google.com/console)
4. Click "Create app"
5. Complete all required sections:
   - Store listing (screenshots, descriptions, graphics)
   - Data safety form
   - Content rating questionnaire
   - Target audience
   - News app declaration (if applicable)
6. Upload AAB file from `app/release/app-release.aab`
7. Review and submit for approval (takes 1-3 days)

For detailed publishing instructions, see [INFO.md](INFO.md).

## Customization

### Change App Colors

Edit `app/src/main/res/values/colors.xml` to match 4dd.ro branding.

### Change App Icon

Replace files in these folders with your custom icon:
- `res/mipmap-mdpi/` (48x48)
- `res/mipmap-hdpi/` (72x72)
- `res/mipmap-xhdpi/` (96x96)
- `res/mipmap-xxhdpi/` (144x144)
- `res/mipmap-xxxhdpi/` (192x192)

### Change Website URL

Edit `MainActivity.kt:26`:
```kotlin
private val websiteUrl = "https://4dd.ro"
```

## Troubleshooting

### Build Fails: "google-services.json missing"

**Solution**: Download `google-services.json` from Firebase Console and place it in `app/` directory.

### Notifications Not Working

1. Verify `google-services.json` is properly configured
2. Check FCM token is being generated (see Logcat)
3. Verify server is sending notifications with correct token
4. On Android 13+, ensure notification permission is granted

### WebView Not Loading Content

1. Check internet connection
2. Verify `INTERNET` permission in AndroidManifest.xml
3. Check Logcat for WebView errors
4. Ensure JavaScript is enabled (already configured in MainActivity.kt)

### App Crashes on Startup

1. Clean and rebuild: `./gradlew clean build`
2. Check Logcat for error messages
3. Verify all dependencies are properly synced

## Dependencies

Key dependencies used in this project:

- AndroidX Core KTX
- AndroidX AppCompat
- Material Components
- ConstraintLayout
- SwipeRefreshLayout
- Firebase BOM
- Firebase Cloud Messaging
- Firebase Analytics

See `app/build.gradle.kts` for complete list and versions.

## Development

### Running Tests

```bash
# Unit tests
./gradlew test

# Instrumented tests
./gradlew connectedAndroidTest
```

### Debugging

1. Enable USB debugging on your device
2. Connect device
3. Click "Debug" in Android Studio
4. Use Logcat to view logs

### Useful Logcat Filters

- `FourDDApplication` - App initialization logs
- `FCMService` - Firebase messaging logs
- `MainActivity` - WebView activity logs

## Security Notes

- Never commit `google-services.json` to public repositories
- Keep your signing keystore secure and backed up
- Store Server Key securely on your backend
- Use ProGuard/R8 for code obfuscation in release builds (already configured)

## Support

**Contact Information:**
- **Email**: 4dd@december-capital.com
- **Website**: https://4dd.ro
- **Company**: https://december-capital.com

**Documentation:**
- Project plan and status: `CLAUDE.md`
- Quick status overview: `INFO.md`
- Firebase documentation: https://firebase.google.com/docs
- Android developer documentation: https://developer.android.com

## License

Copyright (c) 2025 December Capital

## Version History

### 1.0.0 (Initial Release)
- WebView integration for 4dd.ro
- Firebase Cloud Messaging support
- Pull-to-refresh functionality
- Notification deep linking
- Android 7.0+ support
- Custom app icon
- GDPR/CCPA compliant privacy policy

## Important Notes

### Notification Permissions
- On Android 13+ (API 33+), users must explicitly grant notification permission
- The app requests this permission on first launch
- Users can manage permissions in Settings → Apps → 4DD → Notifications

### Firebase Configuration
- Firebase account: December Capital email (not personal)
- `google-services.json` must not be committed to public repositories
- FCM Server Key needed for backend notification integration

### Keystore Security
- **CRITICAL**: Backup your signing keystore file
- Store passwords securely in a password manager
- Without the keystore, you cannot update the app on Play Store
- Never share the keystore publicly

### Testing Notes
- App tested on Android API 36.1 emulator
- Push notifications verified working
- Must be in background for notifications to appear in tray
- All web features (YouTube embeds, links) working correctly
