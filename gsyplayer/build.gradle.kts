@Suppress("DSL_SCOPE_VIOLATION") plugins {
    alias(libs.plugins.com.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.io.github.xjxlx.publishing)
}

android {
    namespace = "com.android.helper.player"
    compileSdk = libs.versions.compileSdks.get()
        .toInt()

    defaultConfig {
        minSdk = libs.versions.minSdk.get()
            .toInt()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation(libs.appcompat)
    api("com.github.CarGuo.GSYVideoPlayer:gsyVideoPlayer:v8.1.4-jitpack")
}

//
//afterEvaluate {
//    publishing {
//        publications {
//            release(MavenPublication) {
//                from components.release
//                groupId = "com.github.apphelper.player"//插件id，格式：com.gitee/github.用户名
//                artifactId = "player"//插件名称
//                version = "2.2.0"//版本号
//                //引用使用格式：implementation "com.gitee.xiaweifeng:JitPackTest11:1.0"
//            }
//        }
//    }
//}
