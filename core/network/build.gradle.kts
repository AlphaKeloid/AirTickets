plugins {
    alias(libs.plugins.airtickets.koin.android.library)
    alias(libs.plugins.kotlinx.serialization)
}

android {
    namespace = "io.captaingaga.airtickets.effective.mobile.network"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    libs.apply {
        implementation(bundles.network)
    }
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}