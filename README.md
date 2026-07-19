# QRCode

A simple, native Android app for **generating** and **scanning** QR codes. Type any text to turn it into a QR image, or point your camera at a QR code to read its contents — all in one app with two swipeable tabs.

## Features

- **Generate QR** — Enter text and instantly render it as a QR code image (powered by [ZXing](https://github.com/zxing/zxing)).
- **Scan QR** — Live camera scanning that decodes QR codes in real time and displays the result (powered by [CameraX](https://developer.android.com/training/camerax) + [ML Kit Barcode Scanning](https://developers.google.com/ml-kit/vision/barcode-scanning)).
- Tabbed interface with swipe navigation between the two tools.
- On-device scanning — no network required, works offline.

## Screens

| Tab | Description |
|-----|-------------|
| **Generate QR** | Text field + "Generate QR" button; the encoded QR renders in the image view above. |
| **Scan QR** | Camera preview; decoded text appears below the viewfinder. |

## Tech Stack

- **Language:** Kotlin
- **UI:** XML layouts + ViewBinding (single Activity, ViewPager2 + TabLayout, two Fragments)
- **QR generation:** ZXing `core` 3.4.0
- **QR scanning:** CameraX 1.3.4 + ML Kit Barcode Scanning 17.2.0
- **Build:** Gradle (Kotlin DSL) with a version catalog (`gradle/libs.versions.toml`)

## Architecture

```
QRActivity (host)
 └── ViewPager2  ──  TabLayout ("Generate QR" | "Scan QR")
      └── QRViewPagerAdapter (FragmentStateAdapter)
           ├── QRGeneratorView  → ZXing QRCodeWriter → Bitmap
           └── QRScannerView    → CameraX ImageAnalysis
                                    └── QRCodeAnalyzer → ML Kit BarcodeScanning
```

Source layout under `app/src/main/java/com/moin/qrcode`:

- `ui/screens/QRActivity.kt` — host Activity and launcher entry point
- `ui/adapters/QRViewPagerAdapter.kt` — supplies the two tab fragments
- `ui/screens/QRGeneration/QRGeneratorView.kt` — QR generation tab
- `ui/screens/QRScanner/QRScannerView.kt` — QR scanning tab (camera + permissions)
- `ui/screens/QRScanner/QRCodeAnalyzer.kt` — per-frame ML Kit barcode analysis

## Requirements

- Android Studio (recent version)
- Android device or emulator running **Android 5.0 (API 21)** or higher
- A camera is required for the scanning feature

| Setting | Value |
|---------|-------|
| minSdk | 21 |
| targetSdk / compileSdk | 34 |
| Kotlin | 1.9.0 |
| Android Gradle Plugin | 8.5.2 |

## Getting Started

1. Clone the repository:
   ```bash
   git clone https://github.com/m01n008/QRCode.git
   ```
2. Open the project in Android Studio and let Gradle sync.
3. Connect a device (or start an emulator) and click **Run**.

Or build from the command line:

```bash
./gradlew assembleDebug        # build the debug APK
./gradlew installDebug         # build and install on a connected device
```

## Permissions

- **Camera** (`android.permission.CAMERA`) — required for scanning. The app requests it at runtime; if denied, the scan tab shows a prompt explaining it's needed.

## License

No license file is currently included in this repository.
