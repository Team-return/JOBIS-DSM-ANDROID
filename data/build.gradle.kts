import java.util.Properties

plugins {
    id(BuildPlugins.ANDROID_LIBRARY)
    id(BuildPlugins.KOTLIN_ANDROID_PLUGIN)
}
val properties = Properties()
properties.load(project.rootProject.file("local.properties").inputStream())

android {
    namespace = ProjectProperties.NAME_SPACE_DATA
    compileSdk = 33
    buildToolsVersion = "30.0.3"

    defaultConfig {
        minSdk = ProjectProperties.COMPILE_SDK
        targetSdk = ProjectProperties.COMPILE_SDK

        testInstrumentationRunner = ProjectProperties.TEST_RUNNER

        buildConfigField("String", "BASE_URL", properties.getProperty("BASE_URL"))
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
        jvmTarget = ProjectProperties.JVM_VERSION
    }
}

dependencies {

    implementation(project(":domain"))

    implementation(Dependency.HILT.HILT)

    implementation(Dependency.OKHTTP.OKHTTP)
    implementation(Dependency.RETROFIT.RETROFIT_CLIENT)
    implementation(Dependency.RETROFIT.GSON_CONVERTER)

    implementation(Dependency.Android.ANDROIDX_CORE)
    implementation(Dependency.Android.APPCOMMPAT)
    implementation(Dependency.Android.APPCOMMPAT)
    testImplementation(Dependency.TEST.JUNIT)
    androidTestImplementation(Dependency.TEST.EXT_JUNIT)
    androidTestImplementation(Dependency.TEST.ESPRESSO)
}