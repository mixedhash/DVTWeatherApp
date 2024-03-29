# DVTWeatherApp

Weather application that displays current weather at the user's location and a 5-day forecast.

## Features

- Display current and 5-day weather forecast using OpenWeather API
- Get extra information about venues around current location using Places API
- Offline mode (shows last time updated timestamp)

## Tech stack

- MVI Architecture + CLEAN
- Jetpack Compose
- Kotlin Flows
- Coroutines
- JUnit 5 and [MockK](https://mockk.io) for unit testing
- [Detekt](https://github.com/detekt/detekt) for static analysis
- [Kover](https://github.com/Kotlin/kotlinx-kover) for Kotlin code coverage
- [Retrofit](https://github.com/square/retrofit) for handling API calls with [Moshi](https://github.com/square/moshi) for parsing JSON
- [Dagger Hilt](https://dagger.dev/hilt/) for dependency injection
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) for local storage
- [KSP](https://github.com/google/ksp) for annotation processing
- [Accompanist](https://google.github.io/accompanist/permissions/) for handling permissions in Jetpack Compose
- [Timber](https://github.com/JakeWharton/timber) for logging
- Github actions for the CI pipeline

## Pipeline

There are 2 separate workflows:
1.  Android CI: it's triggered at each push/PR, runs unit tests, detekt and kover, generates kover reports as artifacts, pipeline file: .github/workflows/android.yml
2. Generate Debug APK: it's triggered manually from Github Actions with `Run workflow`, generates .apk by using secrets stored in repo for API keys and URLs, pipeline file: ./github/workflows/manual-apk.yml

## Setup for building the project and your own APK

You can add in your local.properties the following 4 lines so the environment variables are set as build config fields:
```
OPEN_WEATHER_API_KEY = c9693cb7a194cab94924a2ba3ac3219d
OPEN_WEATHER_BASE_URL = https://api.openweathermap.org/data/2.5/
GOOGLE_MAPS_APIS_KEY = AIzaSyCP4EDtBsblyKcS6cI7aL_4pGlQiLj-x54
NEARBYSEARCH_BASE_URL = https://maps.googleapis.com/maps/api/place/nearbysearch/
```

Now you should be able to run and build the project and create your own .apk file locally.
There is also a special workflow for this if you need an .apk generated by the pipeline called Generate Debug APK and you can run it manually from Github Actions for this repo.

## Possible improvements

- Swipe to refresh
- Auto location reloading
- Loading Spinner
- UI Tests
- Places API also has an Android SDK (noticed this afterwards)
- Forest/Sea Theme change by build flavour
- Make use of Theme package to standardize font sizes, colors and types
- Support for metric/imperial values
- Can change the system bar to be the colour of weather type background (e.g gray for clouds, etc.)
- Icon asset for snow type weather is missing


## Unfinished work

- Location/Datastore repository unit tests class (Roboelectric is needed and is not compatible yet with JUnit 5)
- Favourites screen and maps screen (left them as empty as I didn't have more time, but wanted to show bottom tabs approach with compose nav graph)

## If you're testing this on a fresh emulator!

Android getLastKnownLocation is null on emulator. To solve this go to Google Maps application and after that you will get a location.