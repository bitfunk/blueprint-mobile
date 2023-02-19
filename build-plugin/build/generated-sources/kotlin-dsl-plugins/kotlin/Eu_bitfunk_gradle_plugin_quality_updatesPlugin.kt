/**
 * Precompiled [eu.bitfunk.gradle.plugin.quality.updates.gradle.kts][Eu_bitfunk_gradle_plugin_quality_updates_gradle] script plugin.
 *
 * @see Eu_bitfunk_gradle_plugin_quality_updates_gradle
 */
public
class Eu_bitfunk_gradle_plugin_quality_updatesPlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Eu_bitfunk_gradle_plugin_quality_updates_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java, org.gradle.api.Project::class.java)
                .newInstance(target, target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
