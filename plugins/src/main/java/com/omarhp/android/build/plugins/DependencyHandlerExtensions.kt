package com.omarhp.android.build.plugins

import org.gradle.api.artifacts.dsl.DependencyHandler

/**
 * Adds the standard Compose dependencies to the project.
 */
fun DependencyHandler.compose() {
    add("implementation", platform(BuildConfig.COMPOSE_BOM_DEPENDENCY))
    add("androidTestImplementation", platform(BuildConfig.COMPOSE_BOM_DEPENDENCY))
    BuildConfig.COMPOSE_IMPLEMENTATION_BUNDLE.forEach { add("implementation", it) }
    BuildConfig.COMPOSE_DEBUG_IMPLEMENTATION_BUNDLE.forEach { add("debugImplementation", it) }
    BuildConfig.COMPOSE_ANDROID_TEST_IMPLEMENTATION_BUNDLE.forEach { add("androidTestImplementation", it) }
}

/**
 * Adds the standard Hilt dependencies to the project.
 */
fun DependencyHandler.hilt() {
    add("implementation", BuildConfig.HILT_ANDROID_DEPENDENCY)
    add("kapt", BuildConfig.HILT_COMPILER_DEPENDENCY)
}