package com.omarhp.android.build.plugins

import org.gradle.api.Action
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Nested


abstract class HiltConfiguration {
    abstract val enable: Property<Boolean>
    abstract val addDependencies: Property<Boolean>
}

abstract class AndroidBuildFeaturesExtension {
    abstract val hiltConfiguration: HiltConfiguration
        @Nested get

    fun hiltConfiguration(action: Action<in HiltConfiguration>) {
        action.execute(hiltConfiguration)
    }
}