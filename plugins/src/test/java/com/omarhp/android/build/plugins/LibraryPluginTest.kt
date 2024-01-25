package com.omarhp.android.build.plugins

import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test

class LibraryPluginTest {
    @Test
    fun `Project should contain Android Library Plugin when Library Plugin is applied`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(LibraryPlugin::class.java)
        assert(project.plugins.hasPlugin("com.android.library"))
    }
}

