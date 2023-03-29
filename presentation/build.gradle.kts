plugins {
    id(BuildPlugins.ANDROID_APPLICATION_PLUGIN)
    id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.HILT_PLUGIN)
}

android {
    namespace = ProjectProperties.NAME_SPACE
    compileSdk = 33

    defaultConfig {
        applicationId = ProjectProperties.NAME_SPACE
        minSdk = ProjectProperties.COMPILE_SDK
        targetSdk = ProjectProperties.COMPILE_SDK
        versionCode = ProjectProperties.VERSION_CODE
        versionName = ProjectProperties.VERSION_NAME

        testInstrumentationRunner = ProjectProperties.TEST_RUNNER
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures{
        compose = true
    }
}

dependencies {

    implementation(project(":di"))
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation("com.github.Team-Return.JOBIS-DESIGN-SYSTEM-ANDROID:final:v1.0.1")

    implementation(Dependency.COMPOSE.VIEWMODEL)
    //implementation(Dependency.COMPOSE.LIFECYCLE)
    implementation(Dependency.COMPOSE.ACTIVITY)
    implementation(Dependency.COMPOSE.UI)
    implementation(Dependency.COMPOSE.PREVIEW)
    implementation(Dependency.COMPOSE.MATERIAL)
    implementation(Dependency.COMPOSE.NAVIGATION)
    implementation(Dependency.COMPOSE.NAVIGATION_HILT)
    androidTestImplementation(Dependency.COMPOSE.JUNIT)
    debugImplementation(Dependency.COMPOSE.UI_TEST)

    implementation(Dependency.GLIDE.GLIDE)

    implementation(Dependency.RETROFIT.RETROFIT_CLIENT)
    implementation(Dependency.RETROFIT.GSON_CONVERTER)
    implementation(Dependency.OKHTTP.OKHTTP)

    implementation(Dependency.HILT.HILT)
    kapt(Dependency.HILT.HILT_COMPILER)

    implementation(Dependency.SPLASH.SPLASH_SCREEN)

    implementation(Dependency.ORBIT.MVI)
    implementation(Dependency.ORBIT.VIEWMODEL)
    implementation(Dependency.ORBIT.COMPOSE)
    testImplementation(Dependency.ORBIT.TEST)

    implementation(Dependency.Android.ANDROIDX_CORE)
    implementation(Dependency.Android.APPCOMMPAT)
    implementation(Dependency.Android.MATERIAL)
    implementation(Dependency.Android.ACTIVITY)
    implementation(Dependency.Android.FRAGMENT)

    implementation(Dependency.Android.CONSTRAINT)
    testImplementation(Dependency.TEST.JUNIT)
    androidTestImplementation(Dependency.TEST.EXT_JUNIT)
    androidTestImplementation(Dependency.TEST.ESPRESSO)
}