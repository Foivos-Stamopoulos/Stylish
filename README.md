# Stylish

## Architecture

The app follows best practices like:
- Separation of concerns (Clean Architecture)
- Drive UI from data models
- Coroutines and flows
- Unidirectional Data Flow (UDF)
- Model-View-ViewModel(MVVM)
- Model-View-Intent (MVI)
- Dependency Injection (DI) -> Hilt

## Testing

To facilitate UI testing, Styilsh app uses dependency injection with [Hilt](https://developer.android.com/training/dependency-injection/hilt-android).

In Unit Tests, [MockK](https://mockk.io/) is used to mock the dependencies.

In API Unit Tests, [MockWebServer](https://github.com/square/okhttp/tree/master/mockwebserver) is used to mock HTTP responses.

## UI

Home Screen and Select Category dialog of Edit Product screen are built using [Jetpack Compose](https://developer.android.com/compose).

In case Home Screen feels laggy, this is expected behaviour of Jetpack Compose since this a debug build.
In release builds Jetpack Compose applies several optimizations so that the UI works smoothly.

The rest of the app UI is built with Views.

The app theme for the demo purposes supports only Light mode.

App also supports Landscape mode.

## Typography

Font: Montserrat (based on the Figma designs)

## Continuous Integration (CI)

The project contains a CI workflow that uses Github Actions and automates the build and testing pipeline.

## APK

You can download the .apk of the app from inside this repository
- Navigate inside "Actions" tab
- Click on the latest workflow run
- Press on the "Stylish.apk" to download it locally
