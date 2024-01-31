package com.omarhp.android.build.plugins

import com.android.build.gradle.LibraryPlugin
import org.gradle.api.Project

class LibraryPlugin : BaseAndroidPlugin() {

    override fun apply(project: Project) {
        project.pluginManager.apply(LibraryPlugin::class.java)
        super.apply(project)
    }
}

