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
        getByName("debug") {
            buildConfigField("String", "ENVIRONMENT", "\"development\"")
            buildConfigField("Boolean", "LOG_ENABLED", "false")
            buildConfigField("Boolean", "DEV", "true")
        }
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "ENVIRONMENT", "\"production\"")
            buildConfigField("Boolean", "LOG_ENABLED", "false")
            buildConfigField("Boolean", "DEV", "false")
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
        buildConfig = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.10" // latest
    }
}

dependencies {
    // Compose BOM (already included in your project)
    val composeBom = platform("androidx.compose:compose-bom:2025.03.00")
    implementation(composeBom)

    // Jetpack Compose UI dependencies (already included)
    implementation(libs.ui)
    implementation("androidx.compose.material3:material3:1.3.1")
    implementation(libs.foundation)
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.10.1")

    // Activity Compose: Provides the setContent extension for ComponentActivity
    implementation("androidx.activity:activity-compose:1.7.2") // Use the latest stable version

    // Lifecycle ViewModel Compose: Provides viewModel() composable
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.6.1") // Use the latest stable version

    // Other dependencies from your project...
    implementation("androidx.window:window:1.3.0")
    implementation("androidx.window:window-core:1.3.0")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.7")
    implementation("androidx.navigation:navigation-fragment-ktx:2.8.9")
    implementation("androidx.navigation:navigation-ui-ktx:2.8.9")

    // Testing dependencies
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.2.1")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.6.1")
}
