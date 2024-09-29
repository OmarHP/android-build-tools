import java.net.URI

plugins {
    `java-gradle-plugin`
    `maven-publish`
    alias(libs.plugins.kotlin)
    // alias(libs.plugins.gradle.pluginPublish) // for publishing to Gradle Plugin Portal
    alias(libs.plugins.gmazzo.buildConfig)
}

buildConfig {
    buildConfigField("ANDROID_COMPILE_SDK", 34)
    buildConfigField("ANDROID_TARGET_SDK", 34)
    buildConfigField("ANDROID_MIN_SDK", 26)
    buildConfigField("JAVA_VERSION", 17)

    buildConfigField("COMPOSE_COMPILER_VERSION", libs.versions.composeCompiler.get())
    buildConfigField("COMPOSE_BOM_DEPENDENCY", libs.androidx.compose.bom.get().toString())
    buildConfigField("COMPOSE_IMPLEMENTATION_BUNDLE", libs.bundles.compose.implementation.get().map { it.toString() })
    buildConfigField("COMPOSE_ANDROID_TEST_IMPLEMENTATION_BUNDLE", libs.bundles.compose.androidTestImplementation.get().map { it.toString() })
    buildConfigField("COMPOSE_DEBUG_IMPLEMENTATION_BUNDLE", libs.bundles.compose.debugImplementation.get().map { it.toString() })

    buildConfigField("HILT_ANDROID_DEPENDENCY", libs.hilt.android.get().toString())
    buildConfigField("HILT_COMPILER_DEPENDENCY", libs.hilt.compiler.get().toString())
}

gradlePlugin {
    website = "https://github.com/OmarHP/android-build-tools"
    vcsUrl = "https://github.com/OmarHP/android-build-tools.git"
    plugins {
        create("AndroidLibrary") {
            id = "com.omarhp.android.library"
            displayName = "Android library plugin with predefined configuration"
            description = "Gradle plugin to build Android libraries with a predefined configuration"
            tags = listOf("android", "library", "build-tools")
            implementationClass = "com.omarhp.android.build.plugins.LibraryPlugin"
        }
        create("AndroidApplication") {
            id = "com.omarhp.android.application"
            displayName = "Android application plugin with predefined configuration"
            description = "Gradle plugin to build Android applications with a predefined configuration"
            tags = listOf("android", "app", "build-tools")
            implementationClass = "com.omarhp.android.build.plugins.ApplicationPlugin"
        }
        create("ComposeModule") {
            id = "com.omarhp.android.compose"
            displayName = "Compose setup plugin with predefined configuration"
            description = "Gradle plugin to set up Compose with predefined configuration"
            tags = listOf("android", "compose", "build-tools")
            implementationClass = "com.omarhp.android.build.plugins.ComposePlugin"
        }
        create("HiltModule") {
            id = "com.omarhp.android.hilt"
            displayName = "Hilt setup plugin with predefined configuration"
            description = "Gradle plugin to set up Hilt with predefined configuration"
            tags = listOf("android", "hilt", "build-tools")
            implementationClass = "com.omarhp.android.build.plugins.HiltPlugin"
        }
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    implementation(libs.kotlin.composeCompilerPlugin)
    implementation(libs.hilt.gradlePlugin)
    testImplementation(libs.kotlin.test)
}

tasks.test {
    useJUnitPlatform()
}

val REPSY_USER: String? by project
val REPSY_PASS: String? by project
val REPSY_RELEASE_URL: String? by project

publishing {
    repositories {
        if (REPSY_RELEASE_URL != null) {
            maven {
                name = "PrivateMaven"
                url = URI.create(REPSY_RELEASE_URL)
                credentials {
                    username = REPSY_USER
                    password = REPSY_PASS
                }
            }
        }
    }
}