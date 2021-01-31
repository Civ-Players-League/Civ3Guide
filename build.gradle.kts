buildscript {
    repositories {
        gradlePluginPortal()
        jcenter()
        google()
        mavenCentral()
        maven(url = "https://dl.bintray.com/icerockdev/plugins")
    }
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:1.4.10")
        classpath("com.android.tools.build:gradle:4.1.2")
        classpath("dev.icerock.moko:resources-generator:0.13.1")
    }
}
group = "com.sixbynine.civ3guide"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}
