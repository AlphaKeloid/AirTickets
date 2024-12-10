plugins {
    alias(libs.plugins.airtickets.android.application) // plugin includes koin dependencies
    alias(libs.plugins.airtickets.android.navigation.library)
}

android {
    namespace = "io.captaingaga.airtickets.effective.mobile"

    defaultConfig {
        applicationId = "io.captaingaga.airtickets.effective.mobile"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildFeatures {
        viewBinding = true
    }
    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    projects.apply {
        api(core.data)
        api(core.common)
        api(features.main)
        api(features.search)
        api(features.tickets)
    }

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}