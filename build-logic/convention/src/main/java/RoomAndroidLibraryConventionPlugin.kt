import com.android.build.api.dsl.LibraryExtension
import io.captaingaga.airtickets.effective.mobile.convention.configureKotlinAndroid
import io.captaingaga.airtickets.effective.mobile.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class RoomAndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
                apply("androidx.room")
            }

            dependencies {
                add("implementation", libs.findLibrary("room-coroutines").get())
                add("ksp", libs.findLibrary("room-compiler").get())
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
            }

        }
    }
}