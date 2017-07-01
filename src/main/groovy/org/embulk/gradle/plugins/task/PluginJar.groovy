package org.embulk.gradle.plugins.task

import org.gradle.api.tasks.bundling.Jar

class PluginJar extends Jar {
    Jar baseJar
    String buildDirName = "pkg"
    String mainClass

    PluginJar() {
        project.afterEvaluate {
            // Set a directory where a plugin jar will be generated
            destinationDir = new File(project.projectDir, buildDirName)

            if (!baseJar) {
                baseJar = project.tasks.jar
            }
            dependsOn = [baseJar]

            // Set "plugin" as a classifier if not specified
            if (!classifier || classifier.isEmpty()) {
                classifier = "plugin"
            }

            baseJar.manifest {
                manifest.attributes 'Embulk-Plugin-Spi-Version': "0",
                        'Embulk-Plugin-Main-Class': mainClass,
                        'Implementation-Title': project.name,
                        'Implementation-Version': project.version
            }

            baseJar.from {
                // "provided" dependencies are excluded as they are provided at runtime by the Embulk core.
                def targetConfiguration = project.configurations.runtime - project.configurations.provided
                // Dependencies are picked up with extracting ".jar" files.
                targetConfiguration.collect { (it.isFile() && it.name.endsWith(".jar")) ? project.zipTree(it) : it }
            }

            // Signature files of dependencies are excluded as they cause SecurityException.
            baseJar.exclude("META-INF/*.DSA")
            baseJar.exclude("META-INF/*.RSA")
            baseJar.exclude("META-INF/*.SF")

            with baseJar
        }
    }
}
