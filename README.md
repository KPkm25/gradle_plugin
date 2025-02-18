
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
