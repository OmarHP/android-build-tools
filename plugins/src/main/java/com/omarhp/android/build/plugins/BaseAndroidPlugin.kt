package com.omarhp.android.build.plugins

import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.DefaultConfig
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class BaseAndroidPlugin: Plugin<Project> {

    protected companion object {
        const val COMPILE_SDK = 34
        const val TARGET_SDK = 34
        const val MIN_SDK = 26
        val JAVA_VERSION = JavaVersion.VERSION_17
    }

    fun setupCompileSdk(baseExtension: BaseExtension) {
        baseExtension.setCompileSdkVersion(COMPILE_SDK)
    }

    fun setupDefaultConfig(defaultConfig: DefaultConfig) {
        with(defaultConfig) {
            targetSdk = TARGET_SDK
            minSdk = MIN_SDK
            testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
            vectorDrawables {
                useSupportLibrary = true
            }
        }
    }

    fun setupCompileOptions(baseExtension: BaseExtension) {
        baseExtension.compileOptions {
            it.sourceCompatibility = JAVA_VERSION
            it.targetCompatibility = JAVA_VERSION
        }
    }
}