plugins {
    `kotlin-dsl`
}

dependencies {
    implementation(files(libs.javaClass.superclass.protectionDomain.codeSource.location))

    fun plugin(provider: Provider<PluginDependency>) = with(provider.get()) {
        "$pluginId:$pluginId.gradle.plugin:$version"
    }

    implementation(plugin(libs.plugins.android.library))
    implementation(plugin(libs.plugins.kotlin.android))
    implementation(plugin(libs.plugins.binary.compatibility.validator))
    implementation(plugin(libs.plugins.kotlin.serialization))

    implementation(libs.plugin.version.update)
}
