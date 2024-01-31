plugins {
    `java-gradle-plugin`
    `maven-publish`
    alias(libs.plugins.kotlin)
    // alias(libs.plugins.gradle.pluginPublish) // for publishing to Gradle Plugin Portal
    alias(libs.plugins.gmazzo.buildConfig)
}

buildConfig {
    buildConfigField("COMPOSE_COMPILER_VERSION", libs.versions.composeCompiler.get())
    buildConfigField("ANDROID_COMPILE_SDK", 34)
    buildConfigField("ANDROID_TARGET_SDK", 34)
    buildConfigField("ANDROID_MIN_SDK", 26)
    buildConfigField("JAVA_VERSION", 17)
}

gradlePlugin {
    website = "https://github.com/OmarHP/android-build-tools"
    vcsUrl = "https://github.com/OmarHP/android-build-tools.git"
    plugins {
        create("AndroidLibrary") {
            id = "com.omarhp.android.library"
            displayName = "Android Library Plug-in with predefined configuration"
            description = "Gradle plug-in to build Android libraries with a predefined configuration"
            tags = listOf("android", "library", "build-tools")
            implementationClass = "com.omarhp.android.build.plugins.LibraryPlugin"
        }
        create("AndroidApplication") {
            id = "com.omarhp.android.application"
            displayName = "Android Application Plug-in with predefined configuration"
            description = "Gradle plug-in to build Android applications with a predefined configuration"
            tags = listOf("android", "app", "build-tools")
            implementationClass = "com.omarhp.android.build.plugins.ApplicationPlugin"
        }
    }
}

dependencies {
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
    testImplementation(libs.kotlin.test)
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    repositories {
        maven {
            name = "PrivateMaven"
            url = uri("https://repo.repsy.io/mvn/omarhp90/default")
        }
    }
}