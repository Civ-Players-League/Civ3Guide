buildscript {
    val compose_version by extra("1.0.0-beta01")
    repositories {
        gradlePluginPortal()
        google()
        mavenCentral()
        maven(url = "https://dl.bintray.com/icerockdev/plugins")
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.30")
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath("dev.icerock.moko:resources-generator:0.15.0")
    }
}
group = "com.sixbynine.civ3guide"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven(url = "https://dl.bintray.com/icerockdev/moko")
}
