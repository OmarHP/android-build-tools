package com.omarhp.android.build.plugins

import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test

class ComposePluginTest {
    @Test
    fun `Project should contain Compose Compiler Plugin when Compose Setup Plugin is applied`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(LibraryPlugin::class.java)
        project.pluginManager.apply(ComposePlugin::class.java)
        assert(project.plugins.hasPlugin("org.jetbrains.kotlin.plugin.compose"))
    }
}

