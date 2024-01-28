import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("io.gitlab.arturbosch.detekt")
    id("com.google.dagger.hilt.android")
    id("com.google.devtools.ksp")
}

android {
    namespace = "com.silosoft.technologies.dvtweatherapp"
    compileSdk = 34

    val properties = Properties()

    val localPropertiesFile = project.rootProject.file("local.properties")
    if (localPropertiesFile.exists()) {
        localPropertiesFile.inputStream().use {
            properties.load(it)
        }
    } else {
        System.getenv().forEach { (key, value) ->
            properties.setProperty(key, value)
        }
    }

    defaultConfig {
        applicationId = "com.silosoft.technologies.dvtweatherapp"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        buildConfigField("String", "OPEN_WEATHER_API_KEY", "\"${properties["OPEN_WEATHER_API_KEY"]}\"")
        buildConfigField("String", "OPEN_WEATHER_BASE_URL", "\"${properties["OPEN_WEATHER_BASE_URL"]}\"")
        buildConfigField("String", "GOOGLE_MAPS_APIS_KEY", "\"${properties["GOOGLE_MAPS_APIS_KEY"]}\"")
        buildConfigField("String", "NEARBYSEARCH_BASE_URL", "\"${properties["NEARBYSEARCH_BASE_URL"]}\"")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.8"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    @Suppress("UnstableApiUsage")
    testOptions {
        unitTests.all {
            it.useJUnitPlatform()
        }
    }
}

dependencies {
    // Core/KTX
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")

    // Compose
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.10.01"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.7.6")

    // Work runtime?
    implementation("androidx.work:work-runtime-ktx:2.9.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-moshi:2.9.0")

    // Moshi
    implementation("com.squareup.moshi:moshi:1.15.0")
    ksp("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

    // Hilt
    implementation("com.google.dagger:hilt-android:2.50")
    implementation("androidx.hilt:hilt-navigation-compose:1.1.0")
    ksp("com.google.dagger:dagger-compiler:2.50")
    ksp("com.google.dagger:hilt-android-compiler:2.50")

    // GMS
    implementation("com.google.android.gms:play-services-location:21.1.0")

    // Accompanist
    implementation("com.google.accompanist:accompanist-permissions:0.34.0")

    // Room
    ksp("androidx.room:room-compiler:2.6.1")

    // Timber for logging
    implementation("com.jakewharton.timber:timber:5.0.1")

    // Compose test
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Testing
    testImplementation("io.mockk:mockk:1.13.9")
    testImplementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1")
    testImplementation("com.squareup.okhttp3:okhttp:4.11.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.1")
}