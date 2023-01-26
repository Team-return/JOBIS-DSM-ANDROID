object Dependency {

    object Android {
        const val ANDROIDX_CORE = "androidx.core:core-ktx:${Version.ANDROID_CORE}"
        const val APPCOMMPAT = "androidx.appcompat:appcompat:${Version.APPCOMMPAT}"
        const val MATERIAL = "com.google.android.material:material:${Version.ANDROID_CORE}"
        const val CONSTRAINT = "androidx.constraintlayout:constraintlayout:${Version.CONSTRAINT}"
        const val ACTIVITY = "androidx.activity:activity-ktx:${Version.ACTIVITY}"
        const val FRAGMENT = "androidx.fragment:fragment-ktx:${Version.FRAGMENT}"
    }

    object TEST {
        const val JUNIT = "junit:junit:${Version.JUNIT}"
        const val EXT_JUNIT = "androidx.test.ext:junit:${Version.EXT_JUNIT}"
        const val ESPRESSO = "androidx.test.espresso:espresso-core:${Version.ESPRESSO}"
    }

    object RETROFIT {
        const val RETROFIT_CLIENT = "com.squareup.retrofit2:retrofit:${Version.RETROFIT}"
        const val GSON_CONVERTER = "com.squareup.retrofit2:converter-gson:${Version.RETROFIT}"
    }

    object OKHTTP {
        const val OKHTTP = "com.squareup.okhttp3:okhttp:${Version.OKHTTP}"
    }

    object HILT {
        const val HILT = "com.google.dagger:hilt-android:${Version.HILT}"
        const val HILT_COMPILER = "com.google.dagger:hilt-android-compiler:${Version.HILT}"
    }

    object GLIDE {
        const val GLIDE = "com.github.bumptech.glide:glide:${Version.GLIDE}"
    }

}