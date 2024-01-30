import java.util.Properties

plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
    id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
    id(BuildPlugins.KOTLIN_KAPT)
}

val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = ProjectProperties.NAME_SPACE_DATA
    compileSdk = 33

    defaultConfig {
        minSdk = ProjectProperties.MIN_SDK
        targetSdk = ProjectProperties.COMPILE_SDK

        testInstrumentationRunner = ProjectProperties.TEST_RUNNER

    }

    buildTypes {
        release {
            isMinifyEnabled = false
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = properties.getProperty("PROD_URL", "\"\"")
            )
        }
        debug{
            buildConfigField(
                type = "String",
                name = "BASE_URL",
                value = properties.getProperty("DEV_URL", "\"\"")
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_18
        targetCompatibility = JavaVersion.VERSION_18
    }
    kotlinOptions {
        jvmTarget = ProjectProperties.JVM_VERSION
    }
}

dependencies {

    implementation(project(":domain"))

    implementation(Dependency.HILT.HILT)
    kapt(Dependency.HILT.HILT_COMPILER)

    implementation(Dependency.OKHTTP.OKHTTP)
    implementation(Dependency.RETROFIT.RETROFIT_CLIENT)
    implementation(Dependency.RETROFIT.GSON_CONVERTER)

    implementation(Dependency.JODA.JODA_TIME)
}
