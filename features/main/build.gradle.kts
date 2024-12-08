plugins {
    alias(libs.plugins.airtickets.koin.android.library)
    alias(libs.plugins.airtickets.android.navigation.library)
}

android {
    namespace = "io.captaingaga.airtickets.effective.mobile.main"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation(libs.androidx.legacy.support.v4)
    implementation(libs.androidx.recyclerview)
    projects.apply {
        api(core.common)
        api(core.data)
    }
    libs.apply {
        api(bundles.adapter)
        api(glide)
    }
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}