@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.com.android.application)
    alias(libs.plugins.org.jetbrains.kotlin.android)
}

android {
    namespace = "com.apphelper.player"
    compileSdk = libs.versions.compileSdks.get().toInt()

    defaultConfig {
        applicationId = "com.apphelper.player"
        minSdk = libs.versions.minSdk.get().toInt()
        targetSdk = libs.versions.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = true //启用Proguard
            isShrinkResources = true //是否清理无用资源,依赖于minifyEnabled
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")

        }
        debug {
            isMinifyEnabled = true //不启用Proguard
            isShrinkResources = true //是否清理无用资源,依赖于minifyEnabled
            isDebuggable = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    buildFeatures {
        buildConfig = true
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(":gsyplayer"))
    implementation(libs.core.ktx)
    implementation(libs.material)
    implementation(libs.constraintlayout)
    androidTestImplementation(libs.espresso.core)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    implementation(libs.appcompat)
}