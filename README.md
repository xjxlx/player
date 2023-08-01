# player

gsy视频的封装

## 1：更改了视频缓存到本地时候的文件名字

## 2：增加了自己需要的统计视频时长的逻辑，这一步其实可以单纯的继承原有的库文件来实现

## 3：官方网站的地址：

    https://github.com/CarGuo/GSYVideoPlayer

## 4：使用步骤

    1：继承 StandardGSYVideoPlayer 或者它的实现类去使用
    2：使用的时候，要在清单文件中加入以下的配置，否则会导致横竖屏切换的时候发生异常  
            android:configChanges="keyboard|keyboardHidden|orientation|screenSize|screenLayout|smallestScreenSize|uiMode"

## 5: 依赖使用
allprojects {
    repositories {
        maven { url 'https://maven.aliyun.com/repository/public' }
        maven { url 'https://maven.aliyun.com/repository/central' }
        maven { url 'https://maven.aliyun.com/repository/google' }
        maven { url 'https://maven.aliyun.com/repository/gradle-plugin' }
        maven { url "https://oss.sonatype.org/content/repositories/snapshots" }
        maven { url 'https://dl.google.com/dl/android/maven2/' }
        maven { url 'https://jitpack.io' }

        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
        
implementation 'com.github.xjxlx:player:v2.2.0'
