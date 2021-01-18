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