package com.omarhp.android.build.plugins

import org.gradle.testfixtures.ProjectBuilder
import kotlin.test.Test

class HiltPluginTest {
    @Test
    fun `Project should contain Compose Compiler Plugin when Compose Setup Plugin is applied`() {
        val project = ProjectBuilder.builder().build()
        project.pluginManager.apply(LibraryPlugin::class.java)
        project.pluginManager.apply(HiltPlugin::class.java)
        assert(project.plugins.hasPlugin("com.google.dagger.hilt.android"))
    }
}

