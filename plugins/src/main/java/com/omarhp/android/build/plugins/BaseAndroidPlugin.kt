package com.omarhp.android.build.plugins

import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.internal.dsl.BaseAppModuleExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinAndroidProjectExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinAndroidPluginWrapper
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

abstract class BaseAndroidPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        // Apply the Kotlin Android plugin
        project.pluginManager.apply(KotlinAndroidPluginWrapper::class.java)

        // Get configuration extensions
        val appExtension = project.extensions.findByType(BaseAppModuleExtension::class.java)
        val libraryExtension = project.extensions.findByType(LibraryExtension::class.java)
        val kotlinProjectExtension = project.extensions.findByType(KotlinAndroidProjectExtension::class.java)

        appExtension?.setStandardConfig() ?: libraryExtension?.setStandardConfig()
            ?: throw IllegalStateException(PROJECT_CONFIGURATION_ERROR)

        // Set Kotlin options
        project.tasks.withType(KotlinCompilationTask::class.java).configureEach { task ->
            task.compilerOptions {
                this.apiVersion
            }
        }

        project.tasks.withType(KotlinJvmCompile::class.java).configureEach { task ->
            task.compilerOptions {
                jvmTarget.set(JvmTarget.fromTarget(BuildConfig.JAVA_VERSION.toString()))
            }
        }

        /*// This is the old way to set the JVM target
        project.tasks.withType(KotlinCompile::class.java).configureEach { task ->
            task.kotlinOptions {
                jvmTarget = BuildConfig.JAVA_VERSION.toString()
            }
        }*/

        // Set the JVMToolChain
        kotlinProjectExtension?.jvmToolchain(BuildConfig.JAVA_VERSION)
    }


    private fun LibraryExtension.setStandardConfig() {
        setCommonConfig()
        defaultConfig {
            consumerProguardFiles("consumer-rules.pro")
        }
    }

    private fun BaseAppModuleExtension.setStandardConfig() {
        setCommonConfig()
        defaultConfig {
            targetSdk = BuildConfig.ANDROID_TARGET_SDK
        }
    }

    private fun <T: BuildType> CommonExtension<*, T, *, *, *, *>.setCommonConfig() {
        compileSdk = BuildConfig.ANDROID_COMPILE_SDK

        defaultConfig {
            minSdk = BuildConfig.ANDROID_MIN_SDK
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables { useSupportLibrary = true }
        }

        buildTypes {
            release {
                isMinifyEnabled = this is BaseAppModuleExtension
                proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            }
        }

        compileOptions {
            sourceCompatibility = JavaVersion.toVersion(BuildConfig.JAVA_VERSION)
            targetCompatibility = JavaVersion.toVersion(BuildConfig.JAVA_VERSION)
        }

        buildFeatures {
            buildConfig = true
        }

        packaging {
            resources {
                excludes += "/META-INF/{AL2.0,LGPL2.1}"
                excludes += "/META-INF/*.kotlin_module"
                excludes += "/META-INF/{LICENSE,LICENSE.md,LICENSE.txt,LICENSE-notice.md}"
                excludes += "/META-INF/proguard/androidx-annotations.pro"
                excludes += "/META-INF/{NOTICE, NOTICE.md, NOTICE.txt}"
                excludes += "/META-INF/{rxjava.properties}"
                excludes += "/META-INF/{kotlinx_coroutines_core.version}"
                excludes += "/META-INF/{kotlinx_coroutines_test.version}"
            }
        }
    }
}