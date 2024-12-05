plugins {
    alias(libs.plugins.airtickets.android.library)
}

android {
    namespace = "io.captaingaga.airtickets.effective.mobile.common"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {

    implementation(libs.kotlinx.coroutines)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}