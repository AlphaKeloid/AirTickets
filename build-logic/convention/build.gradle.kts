plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

kotlin {
    jvmToolchain(21)
}

dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "airtickets.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "airtickets.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }

        register("kotlinJvmLibrary") {
            id = "airtickets.kotlin.jvm.library"
            implementationClass = "KotlinJvmLibraryConventionPlugin"
        }

        register("koinAndroidLibrary") {
            id = "airtickets.koin.android.library"
            implementationClass = "KoinAndroidLibraryConventionPlugin"
        }

        register("androidNavigationLibrary") {
            id = "airtickets.android.navigation.library"
            implementationClass = "AndroidNavigationLibraryConventionPlugin"
        }

        register("roomAndroidLibrary") {
            id = "airtickets.room.android.library"
            implementationClass = "RoomAndroidLibraryConventionPlugin"
        }
    }
}

