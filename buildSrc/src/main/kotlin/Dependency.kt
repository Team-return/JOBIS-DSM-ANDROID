object Dependency {

    object RETROFIT {
        const val RETROFIT_CLIENT = "com.squareup.retrofit2:retrofit:${Version.RETROFIT}"
        const val GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:${Version.RETROFIT}"
    }

    object OKHTTP {
        const val OKHTTP = "com.squareup.okhttp3:okhttp:${Version.OKHTTP}"
        const val LOGINTERCEPTER = "com.squareup.okhttp3:logging-interceptor:${Version.OKHTTP}"
    }

    object HILT {
        const val HILT = "com.google.dagger:hilt-android:${Version.HILT}"
        const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Version.HILT}"
    }

    object COIL {
        const val COIL = "io.coil-kt:coil-compose:${Version.COIL}"
    }

    object COMPOSE {
        const val VIEWMODEL =
            "androidx.lifecycle:lifecycle-viewmodel-compose:${Version.COMPOSE_LIFECYCLE}"
        const val ACTIVITY = "androidx.activity:activity-compose:${Version.COMPOSE_ACTIVITY}"
        const val MATERIAL = "androidx.compose.material:material:${Version.COMPOSE_MATERIAL}"
        const val NAVIGATION = "androidx.navigation:navigation-compose:${Version.COMPOSE_NAVIGATION}"
        const val NAVIGATION_HILT =
            "androidx.hilt:hilt-navigation-compose:${Version.COMPOSE_NAVIGATION_HILT}"
        const val FLOW_LAYOUT =
            "com.google.accompanist:accompanist-flowlayout:${Version.COMPOSE_FLOW_LAYOUT}"
        const val NAVIGATION_ANIMATION =
            "com.google.accompanist:accompanist-navigation-animation:${Version.COMPOSE_NAVIGATION_ANIMATION}"
        const val SHIMMER = "com.valentinilk.shimmer:compose-shimmer:${Version.SHIMMER}"
        const val COLLECT_AS_STATE_WITH_LIFECYCLE =
            "androidx.lifecycle:lifecycle-runtime-compose:${Version.COLLECT_AS_STATE_WITH_LIFECYCLE}"
    }

    object SPLASH {
        const val SPLASH_SCREEN = "androidx.core:core-splashscreen:${Version.SPLASH_SCREEN}"
    }

    object ORBIT {
        const val VIEWMODEL = "org.orbit-mvi:orbit-viewmodel:${Version.ORBIT_MVI}"
        const val COMPOSE = "org.orbit-mvi:orbit-compose:${Version.ORBIT_MVI}"
    }

    object DESIGNSYSTEM {
        const val JOBIS =
            "com.github.Team-return.JOBIS-DESIGN-SYSTEM-ANDROID:designsystem:${Version.DESIGN_SYSTEM}"
    }
}