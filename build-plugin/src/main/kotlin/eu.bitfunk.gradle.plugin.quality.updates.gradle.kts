plugins {
    id("com.github.ben-manes.versions")
}

tasks.withType<com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask> {
    // reject all non stable versions
    // and disallow release candidates as upgradable versions from stable versions
    resolutionStrategy {
        componentSelection {
            all {
                if (isStable(currentVersion) && !isStable(candidate.version)) {
                    reject("Release candidate")
                }
            }
        }
    }
}

fun isStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.toUpperCase().contains(it) }
    val regex = "^([0-9]+)\\.([0-9]+)\\.([0-9]+)\$".toRegex()
    return stableKeyword || regex.matches(version)
}
