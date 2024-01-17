buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.2.1")
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.google.firebase:firebase-appdistribution-gradle:4.0.0")
    }
}
plugins {
    id("com.android.library") version "8.2.1" apply false
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.44.2" apply false
}
