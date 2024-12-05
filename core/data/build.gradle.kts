plugins {
    alias(libs.plugins.airtickets.koin.android.library)
}

android {
    namespace = "io.captaingaga.airtickets.effective.mobile.data"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    projects.apply {
        api(core.common)
        api(core.domain)
        api(core.network)
    }
    libs.apply {
        implementation(kotlinx.datetime)
    }
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}