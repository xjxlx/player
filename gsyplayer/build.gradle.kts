@Suppress("DSL_SCOPE_VIOLATION") plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.io.github.xjxlx.publishing)
}

android {
    namespace = "com.android.helper.player"
    compileSdk = libs.versions.compileSdks.get().toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get().toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")

        ndk {
            // 设置支持的SO库架构
            abiFilters += listOf("arm64-v8a", "x86_64", "armeabi", "x86")
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    sourceSets {
        getByName("main") {
            res.srcDirs(listOf("libs"))
            /**在libs文件夹下找so文件*/
            jniLibs.srcDirs(listOf("libs", "src/main/jniLibs"))
        }
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.appcompat)
    // api("com.github.CarGuo.GSYVideoPlayer:gsyVideoPlayer:v8.1.4-jitpack")
    // 当前编译版本31
    // api("com.github.CarGuo.GSYVideoPlayer:GSYVideoPlayer:v8.3.4-release-jitpack")

    // last version
    api("com.github.CarGuo.GSYVideoPlayer:GSYVideoPlayer:v8.5.0-release-jitpack")
}