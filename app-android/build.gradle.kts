import com.android.build.api.dsl.ManagedVirtualDevice
import eu.bitfunk.gradle.plugin.tool.versioning.version
import eu.bitfunk.gradle.plugin.tool.versioning.versionCode

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "eu.bitfunk.blueprint.mobile.android.app"
    compileSdk = libs.versions.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "eu.bitfunk.blueprint.mobile.android.app"

        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()

        versionCode = versionCode()
        versionName = version()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }

    compileOptions {
        isCoreLibraryDesugaringEnabled = true

        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
        freeCompilerArgs = freeCompilerArgs + "-opt-in=kotlin.RequiresOptIn"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.androidx.compose.compiler.get()
    }

    packagingOptions {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1,*.md}"
        }
    }

    lint {
        warningsAsErrors = true
        abortOnError = true

        baseline = file("lint-baseline.xml")
    }

    testOptions {
        managedDevices {
            devices {
                maybeCreate<ManagedVirtualDevice>("Pixel_2_API_30").apply {
                    device = "Pixel 2"
                    apiLevel = 30
                    systemImageSource = "google-atd"
                }
            }
            groups {
                maybeCreate("android").apply {
                    targetDevices.add(devices["Pixel_2_API_30"])
                }
            }
        }
    }
}

dependencies {
    coreLibraryDesugaring(libs.android.desugaring)

    implementation(libs.bundles.app.android)
    implementation(libs.bundles.app.android.compose)
    debugImplementation(libs.bundles.app.android.compose.debug)
    testImplementation(libs.bundles.app.android.test)
    androidTestImplementation(libs.bundles.app.android.androidTest)
    androidTestImplementation(libs.bundles.app.android.androidTest.compose)
}
