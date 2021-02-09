plugins {
    id("com.android.application")
    kotlin("android")
    id("kotlin-android-extensions")
}
group = "com.sixbynine.civ3guide"
version = "1.0-SNAPSHOT"

repositories {
    gradlePluginPortal()
    google()
    jcenter()
    maven(url = "https://dl.bintray.com/icerockdev/moko")
    mavenCentral()
}
dependencies {
    implementation(project(":shared"))
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.4.2")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.4.2")
    implementation("com.google.android.material:material:1.2.1")

    implementation("androidx.appcompat:appcompat:1.2.0")
    implementation("androidx.core:core-ktx:1.3.2")
    implementation("androidx.constraintlayout:constraintlayout:2.0.4")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("dev.icerock.moko:resources:0.14.0")
}
android {
    compileSdkVersion(30)
    defaultConfig {
        applicationId = "com.sixbynine.civ3guide.android"
        minSdkVersion(21)
        targetSdkVersion(30)
        versionCode = 4
        versionName = "20210209.2"
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}