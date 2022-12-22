// https://youtrack.jetbrains.com/issue/KTIJ-19369
@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.android.library) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.binary.compatibility.validator) apply false
    alias(libs.plugins.sqldelight) apply false

    alias(libs.plugins.bitfunk.quality)
    alias(libs.plugins.bitfunk.versioning)

    id("eu.bitfunk.gradle.plugin.quality.updates")
}

reportConfig {
   sonarProjectKey.set("bitfunk_blueprint-mobile")
   sonarOrganization.set("bitfunk")
   coverageReportSourceDirs.set(
       listOf(
           "$projectDir/build/reports/jacoco/testCodeCoverageReport"
       )
   )
}

project(":docs") {
   sonarqube {
       isSkipProject = true
   }
}

tasks.maybeCreate("clean", Delete::class.java).delete("build")

tasks.named<Wrapper>("wrapper") {
    gradleVersion = libs.versions.gradle.get()
    distributionType = Wrapper.DistributionType.ALL
}
