// Top-level build file where you can add configuration options common to all sub-projects/modules.
import java.util.regex.Matcher
import net.researchgate.release.GitAdapter
import net.researchgate.release.GitAdapter.GitConfig
import net.researchgate.release.ReleaseExtension
import org.gradle.kotlin.dsl.KotlinClosure2 as Klosure2

buildscript { }

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.researchgate.release)
}

tasks.register<Delete>("clean") {
    delete(rootProject.layout.buildDirectory)
}

// DSL declaration for missing Git Config functionality when used with kotlin
// https://github.com/researchgate/gradle-release/issues/281#issuecomment-524673725
fun ReleaseExtension.git(configure: GitConfig.() -> Unit) = (getProperty("git") as GitConfig).configure()

release {
    failOnCommitNeeded = true
    failOnPublishNeeded = true
    failOnSnapshotDependencies = true
    failOnUnversionedFiles = true
    failOnUpdateNeeded = true
    revertOnFail = true
    preCommitText = ""
    preTagCommitMessage = "[gradle-release-plugin] [skip ci] - new version commit: "
    tagCommitMessage = "[gradle-release-plugin] [skip ci] - creating tag: "
    tagTemplate = "\${version}"
    versionPropertyFile = "gradle.properties"
    versionProperties = listOf<Any>()
    buildTasks = listOf<Any>()
    scmAdapters = listOf(GitAdapter::class.java)
    versionPatterns = mapOf(
        """(\d+)([^\d]*$)""" to Klosure2<Matcher, Project, String>({ m, _ ->
            m.replaceAll("${m.group(1)}${m.group(2)}")
        })
    )

    git {
        requireBranch = "release-flow"
        pushToRemote = "origin"
        pushToBranchPrefix = ""
        commitVersionFileOnly = false
    }
}

tasks.getByName("afterReleaseBuild").dependsOn("publishAll")

tasks.register("publishAll") {
    dependsOn(
        ":plugins:publishAllPublicationsToPrivateMavenRepository",
        ":version-catalog:publishAllPublicationsToPrivateMavenRepository",
    )
}