pluginManagement {
    repositories {
        // maven { setUrl("https://maven.aliyun.com/repository/central") }
        // maven { setUrl("https://maven.aliyun.com/repository/google") }
        // maven { setUrl("https://oss.sonatype.org/content/repositories/snapshots") }
        // maven { setUrl("https://dl.google.com/dl/android/maven2/") }

        maven { setUrl("https://storage.googleapis.com/r8-releases/raw") }
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://jitpack.io") }

        google()
        mavenCentral()
        mavenLocal()//1、引用插件所在仓库同repositories上传的仓库
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        // maven { setUrl("https://maven.aliyun.com/repository/central") }
        // maven { setUrl("https://maven.aliyun.com/repository/google") }
        // maven { setUrl("https://oss.sonatype.org/content/repositories/snapshots") }
        // maven { setUrl("https://dl.google.com/dl/android/maven2/") }

        maven { setUrl("https://storage.googleapis.com/r8-releases/raw") }
        maven { setUrl("https://maven.aliyun.com/repository/public") }
        maven { setUrl("https://jitpack.io") }

        google()
        mavenCentral()
        mavenLocal()//1、引用插件所在仓库同repositories上传的仓库
        gradlePluginPortal()
        maven {
            credentials {
                username = "6123a7974e5db15d52e7a9d8"
                password = "HsDc[dqcDfda"
            }
            setUrl("https://packages.aliyun.com/maven/repository/2131155-release-wH01IT/")
        }
    }
    versionCatalogs {
        create("libs") {
            from("com.android:catalogs:1.0.0")
        }
    }
}

rootProject.name = "player"
include(":app")
include(":gsyplayer")