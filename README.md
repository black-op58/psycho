# SaninTV

An Android TV anime streaming app with AniList and MyAnimeList integration.

## Features

- 🎬 Stream anime from multiple sources (Consumet, AllAnime, AnimePahe, and custom scrapers)
- 📺 Android TV (Leanback) + Phone support
- 🔗 AniList & MyAnimeList integration — track your watchlist, score, and sync progress
- 🔔 New episode notifications
- 📊 Profile statistics widget
- 🔍 Advanced search with filters (genre, year, country, status, tags)
- 📝 Episode notes & bookmarks
- 🎨 Customizable themes (OLED mode, color editor)
- 📱 Android widgets (upcoming episodes, profile stats)
- 🧩 Extension support (Tachiyomi/Aniyomi sources)

## Downloads

### Latest release
Go to the [Releases page](https://github.com/black-op58/psycho/releases) and download the latest APK.

### Latest build artifacts
1. Go to the [Actions tab](https://github.com/black-op58/psycho/actions)
2. Click on the latest successful workflow run
3. Scroll down to **Artifacts**
4. Download:
   - `SaninTV-debug` — Debug APK (signed with debug keystore)
   - `SaninTV-release-signed` — Release APK (signed with release keystore)

### Build yourself

```bash
# Clone the repo
git clone https://github.com/black-op58/psycho.git
cd psycho

# Build debug APK
./gradlew assembleGoogleDebug

# Build release APK (requires signing config)
./gradlew assembleGoogleRelease
```

APKs will be in:
- `app/build/outputs/apk/google/debug/`
- `app/build/outputs/apk/google/release/`

## CI/CD

This repo uses GitHub Actions to automatically build on every push:

| Workflow | Trigger | Artifacts |
|----------|---------|-----------|
| **Build** | Push to `main`, PR, or manual | Debug & Release APKs |
| **Release** | Manual (`workflow_dispatch`) | Signed Release APK + GitHub Release |

### Release signing
Release builds are signed using a keystore stored as GitHub Secrets:
- `KEYSTORE_BASE64` — Base64-encoded `.keystore` file
- `STORE_PASSWORD` — Keystore password
- `KEY_ALIAS` — Key alias
- `KEY_PASSWORD` — Key password

### Creating a release
1. Go to **Actions** → **Release SaninTV** → **Run workflow**
2. Enter the version number (e.g. `3.2.4`)
3. The workflow builds, signs, and creates a GitHub Release with the APK

## Tech Stack

- **Language:** Kotlin
- **UI:** Jetpack Compose + ViewBinding
- **DI:** Hilt (Dagger)
- **Database:** Room
- **Networking:** OkHttp, Retrofit
- **Image loading:** Coil, Glide
- **Video:** Media3 ExoPlayer
- **Build:** Gradle with Kotlin DSL + Version Catalog
