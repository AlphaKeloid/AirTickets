plugins {
    alias(libs.plugins.airtickets.koin.android.library)
    alias(libs.plugins.airtickets.android.navigation.library)
    alias(libs.plugins.kotlin.android)
}

android {
    namespace = "io.captaingaga.airtickets.effective.mobile.stub"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

}

dependencies {
    projects.apply {
        api(core.common)
    }
    libs.apply {
        api(glide)
    }
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}