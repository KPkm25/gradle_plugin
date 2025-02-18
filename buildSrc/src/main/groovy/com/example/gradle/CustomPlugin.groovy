package com.example.gradle

import org.gradle.api.Plugin
import org.gradle.api.Project
import java.nio.file.Files
import java.nio.file.Paths

class CustomPlugin implements Plugin<Project> {
    void apply(Project project) {
        // Register the customTask
        project.tasks.register("customTask", CustomTask)

        // Register the versioning task
        project.tasks.register("incrementVersion") {
            doLast {
                def version = project.version.toString()
                def versionParts = version.split("\\.") as List
                versionParts[2] = (versionParts[2].toInteger() + 1).toString()  // Increment patch version
                project.version = versionParts.join(".")
                println "Project version updated to: ${project.version}"
            }
        }

        // Register the dependency check task
        project.tasks.register("checkDependencies") {
            doLast {
                def requiredDependencies = ['org.apache.commons:commons-lang3:3.12.0', 'junit:junit:4.13.1']
                requiredDependencies.each { dep ->
                    def found = false
                    project.configurations.each { config ->
                        config.allDependencies.each { dependency ->
                            if (dependency.name == dep.split(":")[1] && dependency.group == dep.split(":")[0]) {
                                found = true
                            }
                        }
                    }
                    if (found) {
                        println "✅ Dependency '$dep' found."
                    } else {
                        println "❌ Dependency '$dep' is missing!"
                    }
                }
            }
        }

        // Register the project setup task
        project.tasks.register("setupProject") {
            doLast {
                // Create basic directories
                def dirs = [
                    'src/main/java',
                    'src/main/resources',
                    'src/test/java',
                    'src/test/resources',
                    'build'
                ]

                dirs.each { dir ->
                    def path = Paths.get(project.projectDir.toString(), dir)
                    if (!Files.exists(path)) {
                        Files.createDirectories(path)
                        println "Created directory: $dir"
                    }
                }

                // Create a simple build.gradle if it doesn't exist
                def buildFile = project.file('build.gradle')
                if (!buildFile.exists()) {
                    buildFile.text = """
                    plugins {
                        id 'java'
                    }

                    repositories {
                        mavenCentral()
                    }

                    dependencies {
                        testImplementation 'junit:junit:4.13.1'
                    }
                    """
                    println "Created build.gradle file."
                }

                // Create a simple settings.gradle if it doesn't exist
                def settingsFile = project.file('settings.gradle')
                if (!settingsFile.exists()) {
                    settingsFile.text = "rootProject.name = 'my-project'"
                    println "Created settings.gradle file."
                }

		def readmeFile = project.file('README.md')
		if(!readmeFile.exists()){
			readmeFile.text = "# My Project\n\n This is an autogenerated README file"
			println "Created README.md file"
		}
            }
        }
    }
}

