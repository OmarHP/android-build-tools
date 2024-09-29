package com.omarhp.android.build.plugins

import dagger.hilt.android.plugin.HiltGradlePlugin
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KaptExtension

import org.gradle.api.artifacts.VersionCatalogsExtension
import org.jetbrains.kotlin.gradle.plugin.ide.IdeMultiplatformImport

class HiltPlugin: Plugin<Project> {
    override fun apply(project: Project) {
        project.checkIsAndroidProject()

        // Apply the Hilt Android Plugin
        project.pluginManager.apply(HiltGradlePlugin::class.java)

        // Apply the kapt plugin
        project.pluginManager.apply(KAPT_PLUGIN_ID)
        project.extensions.findByType(KaptExtension::class.java)?.apply {
            correctErrorTypes = true
        }

        // Hilt Dependencies

        project.dependencies.add("implementation", BuildConfig.HILT_ANDROID_DEPENDENCY)
        project.dependencies.add("kapt", BuildConfig.HILT_COMPILER_DEPENDENCY)

    }
}