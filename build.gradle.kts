buildscript {
    val agp_version by extra("8.1.0")
    val agp_version1 by extra("3.2.0")
    val agp_version2 by extra("8.0.0")
    dependencies {
        classpath("com.android.tools.build:gradle:$agp_version2")
        classpath("com.google.gms:google-services:4.3.15")
        classpath("com.google.firebase:firebase-appdistribution-gradle:4.0.0")
    }
}
plugins {
    id("com.android.library") version "7.3.0" apply false
    id("org.jetbrains.kotlin.android") version "1.8.10" apply false
    id("com.google.dagger.hilt.android") version "2.44.2" apply false
}