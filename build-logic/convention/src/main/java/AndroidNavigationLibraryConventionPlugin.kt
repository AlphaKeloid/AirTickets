import io.captaingaga.airtickets.effective.mobile.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidNavigationLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("androidx.navigation.safeargs.kotlin")
            }
            dependencies {
                add("implementation", libs.findLibrary("androidx-navigation-fragment").get())
                add("implementation", libs.findLibrary("androidx-navigation-ui").get())
            }
        }
    }
}