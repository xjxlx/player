# player
gsy视频的封装

allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}


dependencies {
	        implementation 'com.github.xjxlx:player:v1.0.0'
	}

# 这个是一个封装了gsyPlayer的视频管理器，里面主要做了以下几点
## 1：更改了视频缓存到本地时候的文件名字
## 2：增加了自己需要的统计视频时长的逻辑，这一步其实可以单纯的继承原有的库文件来实现


# 三：使用步骤
    1：继承 StandardGSYVideoPlayer 或者它的实现类去使用
    2：使用的时候，要在清单文件中加入以下的配置，否则会导致横竖屏切换的时候发生异常  
            android:configChanges="keyboard|keyboardHidden|orientation|screenSize|screenLayout|smallestScreenSize|uiMode"
