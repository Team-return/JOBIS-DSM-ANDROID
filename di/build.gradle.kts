import java.util.*

plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
    id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
    id(BuildPlugins.HILT_PLUGIN)
    id(BuildPlugins.KOTLIN_KAPT)
}

kapt {
    javacOptions {
        option("-target", ProjectProperties.JVM_VERSION)
    }
}

android {
    namespace = ProjectProperties.NAME_SPACE_DI
    compileSdk = 33

    defaultConfig {
        minSdk = ProjectProperties.MIN_SDK
        targetSdk = ProjectProperties.COMPILE_SDK

        testInstrumentationRunner = ProjectProperties.TEST_RUNNER
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile(ProjectProperties.PROGUARD),
                ProjectProperties.PROGUARD_RULES
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

    implementation(project(":data"))
    implementation(project(":domain"))

    implementation(Dependency.HILT.HILT)
    kapt(Dependency.HILT.HILT_COMPILER)

    implementation(Dependency.RETROFIT.RETROFIT_CLIENT)
    implementation(Dependency.RETROFIT.GSON_CONVERTER)
    implementation(Dependency.OKHTTP.OKHTTP)
    implementation(Dependency.OKHTTP.LOGINTERCEPTER)

    implementation(Dependency.ANDROID.ANDROIDX_CORE)
    implementation(Dependency.ANDROID.APPCOMMPAT)
    implementation(Dependency.ANDROID.MATERIAL)
    testImplementation(Dependency.TEST.JUNIT)
    androidTestImplementation(Dependency.TEST.EXT_JUNIT)
    androidTestImplementation(Dependency.TEST.ESPRESSO)
}