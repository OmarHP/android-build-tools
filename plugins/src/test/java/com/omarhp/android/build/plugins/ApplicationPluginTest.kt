package com.omarhp.android.build.plugins

import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test

class ApplicationPluginTest {

    @Test
    fun `Project should contain Android Application Plugin when Application Plugin is applied`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(ApplicationPlugin::class.java)
        assert(project.plugins.hasPlugin("com.android.application"))
    }
}

