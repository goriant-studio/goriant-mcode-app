plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.goriant.app"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.goriant.app"
        minSdk = 24
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10" // latest
    }
}


dependencies {
    val composeBom = platform("androidx.compose:compose-bom:2024.02.00") // Use a stable BOM version
    implementation(composeBom)
    androidTestImplementation(composeBom)

    // Latest Stable Material 3
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.material3:material3-window-size-class")

    // Latest Stable Adaptive Navigation (still in alpha but more recent than previous result)
    implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.3.1")

    // Core Compose dependencies
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.runtime:runtime")
    implementation("androidx.compose.foundation:foundation")

    // Android Studio Preview support
    implementation("androidx.compose.ui:ui-tooling-preview")
    debugImplementation("androidx.compose.ui:ui-tooling")

    // Core Android dependencies
    implementation("androidx.core:core-ktx:1.12.0")// latest
    implementation("androidx.appcompat:appcompat:1.6.1")// latest

    implementation("androidx.recyclerview:recyclerview:1.3.2")// latest
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")// latest
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")// latest

    // Navigation component (latest)
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")// latest
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")// latest

    // Testing dependencies
    testImplementation("junit:junit:4.13.2") // latest
    androidTestImplementation("androidx.test.ext:junit:1.1.5") // latest
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")// latest
}