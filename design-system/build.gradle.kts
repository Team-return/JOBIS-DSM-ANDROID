plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
    id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
}

android {
    namespace = "com.example.design_system"
    compileSdk = ProjectProperties.COMPILE_SDK

    defaultConfig {
        minSdk = ProjectProperties.COMPILE_SDK
        targetSdk = ProjectProperties.COMPILE_SDK
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
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.2.0"
    }
    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(Dependency.COMPOSE.VIEWMODEL)
    implementation(Dependency.COMPOSE.ACTIVITY)
    implementation(Dependency.COMPOSE.UI)
    implementation(Dependency.COMPOSE.PREVIEW)
    implementation(Dependency.COMPOSE.MATERIAL)
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.3.1")
    implementation("androidx.activity:activity-compose:1.3.1")
    implementation("androidx.compose.ui:ui:${rootProject.extra["compose_ui_version"]}")
    implementation("androidx.compose.ui:ui-tooling-preview:${rootProject.extra["compose_ui_version"]}")
    implementation("androidx.compose.material:material:1.2.0")
    androidTestImplementation(Dependency.COMPOSE.JUNIT)
    androidTestImplementation("androidx.compose.ui:ui-test-junit4:${rootProject.extra["compose_ui_version"]}")
    debugImplementation(Dependency.COMPOSE.UI_TOOLING)
    debugImplementation(Dependency.COMPOSE.UI_TEST)

    implementation(Dependency.Android.ANDROIDX_CORE)
    implementation(Dependency.Android.APPCOMMPAT)
    implementation(Dependency.Android.MATERIAL)
    testImplementation(Dependency.TEST.JUNIT)
    androidTestImplementation(Dependency.TEST.EXT_JUNIT)
    androidTestImplementation(Dependency.TEST.ESPRESSO)
    debugImplementation("androidx.compose.ui:ui-tooling:${rootProject.extra["compose_ui_version"]}")
    debugImplementation("androidx.compose.ui:ui-test-manifest:${rootProject.extra["compose_ui_version"]}")
}