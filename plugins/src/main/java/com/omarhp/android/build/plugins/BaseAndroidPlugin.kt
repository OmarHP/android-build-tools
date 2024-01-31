package com.omarhp.android.build.plugins

import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import com.android.build.gradle.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinAndroidPluginWrapper

abstract class BaseAndroidPlugin : Plugin<Project> {

    override fun apply(project: Project) {

        // Apply kotlin-android plugin
        project.pluginManager.apply(KotlinAndroidPluginWrapper::class.java)

        // Get configuration extensions
        val appExtension = project.extensions.findByType(AppExtension::class.java)
        val libraryExtension = project.extensions.findByType(LibraryExtension::class.java)
        val kotlinProjectExtension = project.extensions.findByType(KotlinAndroidProjectExtension::class.java)

        // Check at least one configuration extension exists
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

        // Set the buildFeatures block
        with(baseExtension.buildFeatures) {
            compose = true
        }

        // Set the compose options
        with(baseExtension.composeOptions) {
            kotlinCompilerExtensionVersion = BuildConfig.COMPOSE_COMPILER_VERSION
        }

        // Exclude license resources in the '/META-INF/' directory
        baseExtension.packagingOptions.resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }

        // Set the JVMToolChain
        kotlinProjectExtension?.jvmToolchain(BuildConfig.JAVA_VERSION)
    }
}