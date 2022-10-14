// https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    id("com.android.application") version libs.versions.android.gradlePlugin apply false
    id("com.android.library") version libs.versions.android.gradlePlugin apply false
    id("org.jetbrains.kotlin.android") version libs.versions.kotlin apply false
}
}

tasks.maybeCreate("clean", Delete::class.java).delete("build")

tasks.named<Wrapper>("wrapper") {
    gradleVersion = libs.versions.gradle.get()
    distributionType = Wrapper.DistributionType.ALL
}
