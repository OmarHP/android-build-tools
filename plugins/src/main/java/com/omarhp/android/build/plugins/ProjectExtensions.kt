package com.omarhp.android.build.plugins

import com.android.build.gradle.AppPlugin
import org.gradle.api.Project

internal fun Project.checkIsAndroidProject() {
    if (!plugins.hasPlugin(LibraryPlugin::class.java) &&
        !plugins.hasPlugin(AppPlugin::class.java)) {
        throw IllegalStateException(NO_ANDROID_PROJECT_ERROR)
    }
}