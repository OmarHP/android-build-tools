package com.omarhp.android.build.plugins

import com.android.build.gradle.AppPlugin
import org.gradle.api.Project

class ApplicationPlugin: BaseAndroidPlugin() {

    override fun apply(project: Project) {
        project.pluginManager.apply(AppPlugin::class.java)
        super.apply(project)
    }
}



