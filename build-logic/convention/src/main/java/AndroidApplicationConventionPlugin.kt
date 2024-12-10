import com.android.build.api.dsl.ApplicationExtension
import io.captaingaga.airtickets.effective.mobile.convention.configureKotlinAndroid
import io.captaingaga.airtickets.effective.mobile.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidApplicationConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.application")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<ApplicationExtension> {
                configureKotlinAndroid(this)
            }

            dependencies {
                add("implementation", platform(libs.findLibrary("koin-bom").get()))
                add("implementation", libs.findLibrary("koin-core").get())
                add("implementation", libs.findLibrary("koin-viewmodel").get())
                add("implementation", libs.findLibrary("koin-android").get())
            }
        }
    }
}