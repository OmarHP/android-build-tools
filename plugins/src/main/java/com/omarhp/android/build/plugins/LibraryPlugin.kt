package com.omarhp.android.build.plugins

import com.android.build.gradle.LibraryExtension
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinAndroidPluginWrapper

class LibraryPlugin : BaseAndroidPlugin() {

    override fun apply(project: Project) {
        project.pluginManager.apply(LibraryPlugin::class.java)
        project.pluginManager.apply(KotlinAndroidPluginWrapper::class.java)

        val libraryExtension = project.extensions.getByType(LibraryExtension::class.java)
        setupCompileSdk(libraryExtension)
        setupDefaultConfig(libraryExtension.defaultConfig)
        setupCompileOptions(libraryExtension)
    }
}

