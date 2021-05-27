plugins {
    id("com.android.application")
    kotlin("plugin.serialization") version "1.4.10"
    kotlin("android")
    id("kotlin-android")
    id("com.google.gms.google-services")
}
group = "com.sixbynine.civ3guide"
version = "1.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    google()
    maven(url = "https://dl.bintray.com/icerockdev/moko")
    mavenCentral()
}
dependencies {
    implementation(project(":shared"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-protobuf:1.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:1.0.1")
    implementation("com.google.android.material:material:1.2.1")

    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    implementation("dev.icerock.moko:resources:0.15.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation(platform("com.google.firebase:firebase-bom:28.0.1"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-analytics-ktx")
}
android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.sixbynine.civ3guide.android"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 14
        versionName = "20210527.1"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}