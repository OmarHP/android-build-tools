plugins {
    `java-gradle-plugin`
    `maven-publish`
    alias(libs.plugins.kotlin)
    alias(libs.plugins.pluginPublish)
}

gradlePlugin {
    website = "https://github.com/OmarHP/android-build-tools"
    vcsUrl = "https://github.com/OmarHP/android-build-tools.git"
    plugins {
        create("Android Library Plugin") {
            id = "com.omarhp.android.library"
            displayName = "Android Library Plug-in with predefined configuration"
            description = "Gradle plug-in to build Android libraries with a predefined configuration"
            tags = listOf("android", "library", "build-tools")
            implementationClass = "com.omarhp.android.build.plugins.LibraryPlugin"
        }
        create("Android Application Plugin") {
            id = "com.omarhp.android.application"
            displayName = "Android Application Plug-in with predefined configuration"
            description = "Gradle plug-in to build Android applications with a predefined configuration"
            tags = listOf("android", "app", "build-tools")
            implementationClass = "com.omarhp.android.build.plugins.ApplicationPlugin"
        }
    }
}

dependencies {
    implementation(libs.gp.android)
    implementation(libs.gp.kotlin)
    testImplementation(libs.kotlin.test)
}

tasks.test {
    useJUnitPlatform()
}

publishing {
    repositories {
        mavenLocal()
    }
}

