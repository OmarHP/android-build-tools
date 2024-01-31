plugins {
    `version-catalog`
    `maven-publish`
}

catalog {
    versionCatalog {
        from(files("../gradle/libs.versions.toml"))
    }
}

publishing {
    repositories {
        maven {
            name = "PrivateMaven"
            url = uri("https://repo.repsy.io/mvn/omarhp90/default")
        }
    }
    publications{
        create<MavenPublication>("artifact") {
            from(components["versionCatalog"])
        }
    }
}