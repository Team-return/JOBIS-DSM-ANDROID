plugins {
    id(BuildPlugins.ANDROID_APPLICATION_PLUGIN)
    id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
    id(BuildPlugins.KOTLIN_KAPT)
    id(BuildPlugins.HILT_PLUGIN)
    id(BuildPlugins.APP_DISTRIBUTION)
    id(BuildPlugins.GOOGLE_SERVICES)
}

android {
    namespace = ProjectProperties.NAME_SPACE
    compileSdk = 34

    defaultConfig {
        applicationId = ProjectProperties.NAME_SPACE
        minSdk = ProjectProperties.MIN_SDK
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
                ProjectProperties.PROGUARD_RULES,
            )
        }
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.4.2"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = ProjectProperties.JVM_VERSION
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(project(":di"))
    implementation(project(":domain"))
    implementation(project(":data"))

    implementation(Dependency.DESIGNSYSTEM.JOBIS)

    implementation(Dependency.COMPOSE.VIEWMODEL)
    implementation(Dependency.COMPOSE.ACTIVITY)
    implementation(Dependency.COMPOSE.MATERIAL)
    implementation(Dependency.COMPOSE.NAVIGATION)
    implementation(Dependency.COMPOSE.NAVIGATION_HILT)
    implementation(Dependency.COMPOSE.FLOW_LAYOUT)
    implementation(Dependency.COMPOSE.NAVIGATION_ANIMATION)
    implementation(Dependency.COMPOSE.SHIMMER)

    implementation(Dependency.COMPOSE.COLLECT_AS_STATE_WITH_LIFECYCLE)

    implementation(Dependency.COIL.COIL)

    implementation(Dependency.HILT.HILT)
    implementation(Dependency.GOOGLE.APP_UPDATE)
    kapt(Dependency.HILT.HILT_COMPILER)

    implementation(Dependency.SPLASH.SPLASH_SCREEN)

    implementation(Dependency.ORBIT.VIEWMODEL)
    implementation(Dependency.ORBIT.COMPOSE)
}
