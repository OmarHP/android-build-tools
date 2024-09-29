package com.omarhp.android.build.plugins

import com.android.build.api.dsl.CommonExtension
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.jetbrains.kotlin.compose.compiler.gradle.ComposeCompilerGradleSubplugin

class ComposePlugin : Plugin<Project> {

    override fun apply(project: Project) {

        project.checkIsAndroidProject()

        // Apply the Compose Compiler plugin
        project.pluginManager.apply(ComposeCompilerGradleSubplugin::class.java)

        // Set up the Compose configuration
        project.extensions.findByType(CommonExtension::class.java)?.setComposeConfiguration()
            ?: throw IllegalStateException("The project has not been configured correctly")
    }

    private fun CommonExtension<*, *, *, *, *, *>.setComposeConfiguration() {
        buildFeatures {
            compose = true
        }
        composeOptions {
            kotlinCompilerExtensionVersion = BuildConfig.COMPOSE_COMPILER_VERSION
        }
    }
}
