plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")

    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("kotlin-parcelize")
}

android {
    namespace = "com.example.recipeandroidapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.recipeandroidapp"
        minSdk = 23
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
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
        compose = true
    }
}

dependencies {

    // Splash Api
    implementation("androidx.core:core-splashscreen:1.2.0")

    // Compose Navigation
    implementation("androidx.navigation:navigation-compose:2.9.6")

    // New Icons
    implementation("androidx.compose.material:material-icons-extended:1.7.8")

    // Datastore
    implementation("androidx.datastore:datastore-preferences:1.2.0")

    // Dagger Hilt
    implementation("com.google.dagger:hilt-android:2.57.2")
    kapt("com.google.dagger:hilt-compiler:2.57.2")
    implementation("androidx.hilt:hilt-navigation-compose:1.3.0")

    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:3.0.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")

    // Kotlinx Serialization
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.9.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")

    // Coil
    implementation("io.coil-kt:coil-compose:2.4.0")

    // Compose Foundation
    implementation("androidx.compose.foundation:foundation:1.10.0")

    // Accompanist
    implementation("com.google.accompanist:accompanist-systemuicontroller:0.36.0")

    // Paging 3
    implementation("androidx.paging:paging-runtime:3.3.6")
    implementation("androidx.paging:paging-compose:3.3.6")

    // Room
    implementation("androidx.room:room-runtime:2.8.4")
    kapt("androidx.room:room-compiler:2.8.4")
    implementation("androidx.room:room-ktx:2.8.4")

    implementation("androidx.core:core-ktx:1.17.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.10.0")
    implementation("androidx.activity:activity-compose:1.12.1")
    implementation(platform("androidx.compose:compose-bom:2024.09.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    androidTestImplementation(platform("androidx.compose:compose-bom:2024.09.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}