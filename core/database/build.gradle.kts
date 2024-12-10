plugins {
    alias(libs.plugins.airtickets.koin.android.library)
    alias(libs.plugins.airtickets.room.android.library)
}

android {
    namespace = "io.captaingaga.airtickets.effective.mobile.database"

    defaultConfig {
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}

room {
    schemaDirectory("${rootProject.projectDir}/schemas")
}