package com.example.gradle

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

class CustomTask extends DefaultTask {
    @TaskAction
    void runTask() {
        println "✅ Executing Custom Gradle Task!"
    }
}

