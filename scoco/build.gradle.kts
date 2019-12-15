import org.jetbrains.kotlin.config.KotlinCompilerVersion
import java.util.Date
import java.util.Properties

plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka") version "0.9.17"
    id("com.jfrog.bintray") version "1.8.4"
    `maven-publish`
}

dependencies {
    implementation(kotlin("stdlib-jdk7", KotlinCompilerVersion.VERSION))

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.3")
    testImplementation("junit:junit:4.12")
    testImplementation("io.mockk:mockk:1.9.3")
    testImplementation("org.amshove.kluent:kluent:1.58")
}

tasks {
    dokka {
        outputFormat = "html"
        outputDirectory = "$buildDir/javadoc"
        moduleName = rootProject.name
    }
}

val dokkaJar by tasks.creating(Jar::class) {
    group = JavaBasePlugin.DOCUMENTATION_GROUP
    description = "Assembles Kotlin docs with Dokka"
    archiveClassifier.set("javadoc")
    from(tasks.dokka)
    dependsOn(tasks.dokka)
}

val sourcesJar by tasks.creating(Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.getByName("main").allSource)
}

val publishedGroupId = "com.quadible"
val libraryName = "scoco"
val artifact = "scoco"

val libraryDescription = "Scope of a Coroutine useful for unit-testing"

val siteUrl = "https://github.com/St4B/Scoco"
val gitUrl = "https://github.com/St4B/Scoco.git"
val issueUrl = "https://github.com/St4B/Scoco/issues"
val readmeUrl = "https://github.com/St4B/Scoco/blob/master/README.md"

val libraryVersion = "1.0.0-alpha"

val developerId = "St4B"
val developerName = "Vaios Tsitsonis"

val licenseName = "The Apache Software License, Version 2.0"
val licenseUrl = "http://www.apache.org/licenses/LICENSE-2.0.txt"
val allLicenses = "Apache-2.0"

publishing {
    publications {
        create<MavenPublication>("Scoco") {
            groupId = publishedGroupId
            artifactId = artifact
            version = libraryVersion
            from(components["java"])
            artifact(dokkaJar)
            artifact(sourcesJar)

            pom.withXml {
                asNode().apply {
                    appendNode("description", libraryDescription)
                    appendNode("name", rootProject.name)
                    appendNode("url", siteUrl)
                    appendNode("licenses").appendNode("license").apply {
                        appendNode("name", licenseName)
                        appendNode("url", licenseUrl)
                    }
                    appendNode("developers").appendNode("developer").apply {
                        appendNode("id", developerId)
                        appendNode("name", developerName)
                    }
                }
            }
        }
    }
}

bintray {

    val propertiesFile = file(project.rootDir.path + "/local.properties")

    if (!propertiesFile.exists()) return@bintray

    val properties = Properties()
    properties.load(propertiesFile.inputStream())

    user = if (properties.containsKey("bintray.user")) properties["bintray.user"] as String else ""
    key = if (properties.containsKey("bintray.apikey")) properties["bintray.apikey"] as String else ""
    publish = true

    setPublications("Scoco")

    pkg.apply {
        repo = "quadible"
        name = rootProject.name
        setLicenses(allLicenses)
        setLabels("Kotlin", "Coroutines", "Coroutine Scope", "Test", "Testing", "JUnit")
        vcsUrl = siteUrl
        websiteUrl = siteUrl
        issueTrackerUrl = issueUrl
        githubRepo = githubRepo
        githubReleaseNotesFile = readmeUrl

        version.apply {
            name = libraryVersion
            desc = libraryDescription
            released = Date().toString()
        }
    }
}