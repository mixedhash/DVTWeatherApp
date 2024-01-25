# DVTWeatherApp

Weather application that displays current weather at the user's location and a 5-day forecast.

## Features

- Display current and 5-day weather forecast
- Save current location as favourite
- Show a list of favourite locations
- Get extra information/ recommended accommodation around a specific location
- Offline mode (show last time updated forecast)
- Favourite locations map view

## Tech stack

- MVI Architecture
- Jetpack Compose
- Kotlin Flows
- Coroutines
- JUnit 5 and [MockK](https://mockk.io) for unit testing
- [Detekt](https://github.com/detekt/detekt) for static analysis
- [Kover](https://github.com/Kotlin/kotlinx-kover) for Kotlin code coverage
- [Retrofit](https://github.com/square/retrofit) for handling API calls with [Moshi](https://github.com/square/moshi) for parsing JSON
- Github actions for the CI pipeline

## Setup

## Journal

This section will mark my steps used in solving this assignment:

1. Examined requirements and chose my tech stack and architecture.
2. Analysing API's response through Postman and adding keys as secrets.
3. Prepared CI pipeline.
