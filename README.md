# 4DD Android App

<img src="4dd-icon.png" alt="4DD App Icon" width="120"/>

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

- **Min SDK**: API 24 (Android 7.0)
- **Target SDK**: API 34 (Android 14)
- **Language**: Kotlin
- **Build System**: Gradle 8.2

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
│   └── google-services.json                       # Firebase config
├── build.gradle.kts                               # Project-level build config
├── settings.gradle.kts                            # Project settings
└── README.md                                      # This file
```

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
