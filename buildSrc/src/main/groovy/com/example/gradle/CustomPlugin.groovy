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
			readmeFile.text = """
# Project Setup Automation with Gradle Plugin

This project provides a custom **Gradle plugin** that automates the following tasks for your Gradle-based projects:

- **Versioning**: Automatically increments the patch version of the project.
- **Dependency Validation**: Checks if required dependencies are included in the project.
- **Project Setup**: Automates the creation of basic project structure (directories, `build.gradle`, `settings.gradle`, etc.).

## Getting Started

Follow these steps to use the Gradle plugin and automate project setup, versioning, and dependency checks in your own 
Gradle project.

### Prerequisites

- **Java 8 or higher** installed. - **Gradle 8.5** or higher installed. - A **Gradle-based project**.

If you don't have Gradle installed, you can follow the instructions here: [Gradle 
Installation](https://gradle.org/install/).

### 1. Apply the Plugin to Your Project

To use this plugin in your Gradle project, follow these steps:

1. **Include the Plugin in Your Project:**

   Add the following code to your project’s `build.gradle` file:

```
groovy plugins { id 'com.example.gradle.customPlugin'}

``` 
2. **Run setupProject Task:** 
This task automates the creation of basic project structure. It will create the directories if they don't exist. 
```
 gradle setupProject
 ``` 
3. **Run incrementVersion Task:**
 This task automatically increments the patch version of the project. It will update the version property in your build.gradle file 
```
 gradle incrementVersion
 ``` 
4.**Run checkDependencies Task:** 
This task checks whether the required dependencies are present in the project. 
``` 
gradle checkDependencies

```
"""
 println "Created README.md file"
		}
            }
        }
    }
}

