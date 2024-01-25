package com.omarhp.android.build.plugins

import com.android.build.gradle.AppExtension
import com.android.build.gradle.AppPlugin
import org.gradle.api.Project
import org.jetbrains.kotlin.gradle.plugin.KotlinAndroidPluginWrapper

class ApplicationPlugin: BaseAndroidPlugin() {

    override fun apply(project: Project) {
        project.pluginManager.apply(AppPlugin::class.java)
        project.pluginManager.apply(KotlinAndroidPluginWrapper::class.java)

        val appExtension = project.extensions.getByType(AppExtension::class.java)
        setupCompileSdk(appExtension)
        setupDefaultConfig(appExtension.defaultConfig)
        setupCompileOptions(appExtension)
    }
}



