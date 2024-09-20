import java.net.URI

plugins {
    `version-catalog`
    `maven-publish`
}

catalog {
    versionCatalog {
        from(files("../gradle/libs.versions.toml"))
    }
}

val REPSY_USER: String? by project
val REPSY_PASS: String? by project
val REPSY_RELEASE_URL: String? by project

publishing {
    repositories {
        if (REPSY_RELEASE_URL != null) {
            maven {
                name = "PrivateMaven"
                url = URI.create(REPSY_RELEASE_URL)
                credentials {
                    username = REPSY_USER
                    password = REPSY_PASS
                }
            }
        }
    }
    publications{
        create<MavenPublication>("artifact") {
            from(components["versionCatalog"])
        }
    }
}