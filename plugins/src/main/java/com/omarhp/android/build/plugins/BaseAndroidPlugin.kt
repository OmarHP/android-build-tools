package com.omarhp.android.build.plugins

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradleSubplugin
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinAndroidPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

abstract class BaseAndroidPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        // Apply the Kotlin Android plugin
        project.pluginManager.apply(KotlinAndroidPluginWrapper::class.java)

        // Apply the Compose Compiler plugin
        project.pluginManager.apply(ComposeCompilerGradleSubplugin::class.java)

        // Get configuration extensions
        val appExtension = project.extensions.findByType(AppExtension::class.java)
        val libraryExtension = project.extensions.findByType(LibraryExtension::class.java)
        val kotlinProjectExtension = project.extensions.findByType(KotlinAndroidProjectExtension::class.java)

        // Verify that at least one of the configuration extensions exist
        val baseExtension: BaseExtension = appExtension ?: libraryExtension
        ?: throw IllegalStateException("The project has not been configured correctly")

        // Set the compile SDK version
        baseExtension.setCompileSdkVersion(BuildConfig.ANDROID_COMPILE_SDK)

        // Set the default configuration
        with(baseExtension.defaultConfig) {
            targetSdk = BuildConfig.ANDROID_TARGET_SDK
            minSdk = BuildConfig.ANDROID_MIN_SDK
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables {
                useSupportLibrary = true
            }
        }

        // Set the compile options
        with(baseExtension.compileOptions) {
            sourceCompatibility = JavaVersion.toVersion(BuildConfig.JAVA_VERSION)
            targetCompatibility = JavaVersion.toVersion(BuildConfig.JAVA_VERSION)
        }

        // Set Kotlin options
        project.tasks.withType(KotlinCompile::class.java).configureEach { task ->
            task.kotlinOptions {
                jvmTarget = BuildConfig.JAVA_VERSION.toString()
            }
        }

        // Set proguard configuration
        baseExtension.buildTypes { types ->
            types.getByName("release") { type ->
                type.isMinifyEnabled = appExtension != null
                type.proguardFiles(
                    baseExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
                    "proguard-rules.pro"
                )
            }
        }

        // Only applies to library projects
        libraryExtension?.defaultConfig {
            consumerProguardFiles("consumer-rules.pro")
        }

        // Set the buildFeatures block
        with(baseExtension.buildFeatures) {
            compose = true
            buildConfig = true
        }

        // Set compose options
        with(baseExtension.composeOptions) {
            kotlinCompilerExtensionVersion = BuildConfig.COMPOSE_COMPILER_VERSION
        }

        // Exclude license resources in the '/META-INF/' directory
        baseExtension.packagingOptions.resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/*.kotlin_module"
            excludes += "/META-INF/{LICENSE,LICENSE.md,LICENSE.txt,LICENSE-notice.md}"
            excludes += "/META-INF/proguard/androidx-annotations.pro"
            excludes += "/META-INF/{NOTICE, NOTICE.md, NOTICE.txt}"
            excludes += "/META-INF/{rxjava.properties}"
            excludes += "/META-INF/{kotlinx_coroutines_core.version}"
            excludes += "/META-INF/{kotlinx_coroutines_test.version}"
        }

        // Set the JVMToolChain
        kotlinProjectExtension?.jvmToolchain(BuildConfig.JAVA_VERSION)
    }
}