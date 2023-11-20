// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    repositories {
        maven { setUrl("https://plugins.gradle.org/m2/") }
    }

    repositories {
        maven { setUrl("https://storage.googleapis.com/r8-releases/raw") }
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://jitpack.io") }

        google()
        mavenLocal() // 加载本地插件
        mavenCentral()
        gradlePluginPortal()
    }

    dependencies {
        classpath(libs.gradle.api)
    }
}

@Suppress("DSL_SCOPE_VIOLATION") plugins {
    // id("com.android.application") version "7.4.2" apply false
    // id("com.android.library") version "7.4.2" apply false
    // id("org.jetbrains.kotlin.android") version "1.7.20" apply false
    // id("org.jetbrains.kotlin.jvm") version "1.7.20" apply false

    alias(libs.plugins.com.android.application) apply false
    alias(libs.plugins.com.android.library) apply false
    alias(libs.plugins.org.jetbrains.kotlin.android) apply false
    alias(libs.plugins.org.jetbrains.kotlin.jvm) apply false
}
true